package com.skybooking.skyflightservice.v1_0_0.service.implement.shopping.transform;

import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.*;
import com.skybooking.skyflightservice.v1_0_0.util.DateUtility;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.multimap.Multimap;
import org.eclipse.collections.api.multimap.bag.MutableBagMultimap;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TransformSabreMerger {

    private MutableMap<String, Airline> airlines = UnifiedMap.newMap();
    private MutableMap<String, Aircraft> aircrafts = UnifiedMap.newMap();
    private MutableMap<String, Location> locations = UnifiedMap.newMap();
    private MutableMap<String, LayoverAirport> layoverAirports = UnifiedMap.newMap();
    private MutableMap<String, PriceDetail> prices = UnifiedMap.newMap();
    private MutableMap<String, BaggageDetail> baggages = UnifiedMap.newMap();
    private MutableMap<String, Segment> segments = UnifiedMap.newMap();
    private MutableMap<String, com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.Leg> legs = UnifiedMap.newMap();
    private MutableMap<Optional<String>, MutableBagMultimap<Integer, Leg>> directs = UnifiedMap.newMap();
    private MutableMap<Optional<Integer>, MutableBagMultimap<Optional<String>, Leg>> cheaps = UnifiedMap.newMap();

    private FastList<TransformSabre> transformSabres;
    private String directions;

    public TransformSabreMerger(FastList<TransformSabre> transformSabres, String directions) {
        this.transformSabres = transformSabres;
        this.directions = directions;
        this.start();
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * init and processing data
     * -----------------------------------------------------------------------------------------------------------------
     */
    private void start() {

        processingMerger();
        filteringDirectFlight();
        filteringCheapFlight();

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * processing merge response list into one data set
     * -----------------------------------------------------------------------------------------------------------------
     */
    private void processingMerger() {

        transformSabres
            .forEach(transformSabre -> {
                airlines.putAll(transformSabre.getAirlines());
                aircrafts.putAll(transformSabre.getAircrafts());
                locations.putAll(transformSabre.getLocations());
                layoverAirports.putAll(transformSabre.getLayoverAirports());
                prices.putAll(transformSabre.getPrices());
                baggages.putAll(transformSabre.getBaggages());
                segments.putAll(transformSabre.getSegments());
                legs.putAll(transformSabre.getLegs());
            });

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * filtering and group by direct flight
     * -----------------------------------------------------------------------------------------------------------------
     */
    private void filteringDirectFlight() {

        var totalDirection = transformSabres.size();

        var directLegs = legs.partition(Leg::isDirectFlight).getSelected();
        var directLegAirline = directLegs.groupBy(leg -> leg.getAirlines().stream().findFirst().map(LegAirline::getAirline));

        directLegAirline
            .keySet()
            .forEach(airline -> {

                var directLegDirection = directLegAirline.get(airline).groupBy(Leg::getDirectionIndex);
                var directDirectionSize = directLegDirection.keySet().size();

                if (directDirectionSize == totalDirection) {
                    directs.put(airline, directLegDirection);
                }
            });

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * pairing direct flight to itineraries list
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return List
     */
    private List<Itinerary> getDirectFlights() {

        var directFlight = new ArrayList<Itinerary>();

        directs.forEachKeyValue((airline, legDirection) -> {

            var itinerary = new Itinerary();
            itinerary.setAirline(airline.get());

            legDirection.forEachKeyMultiValues((directionIndex, legDirects) -> {

                var legGroup = new LegGroup();
                legGroup.setAirline(airline.get());

                legDirects.forEach(leg -> {

                    var legDescription = new LegDescription();
                    legDescription.setLeg(leg.getId());

                    legGroup.getLegsDesc().add(legDescription);

                });

                // sort price
                var selectedLegs = legGroup.getLegsDesc()
                    .stream()
                    .sorted((previous, next) -> {

                        var prePrice = prices.get(legs.get(previous.getLeg()).getPrice());
                        var nexPrice = prices.get(legs.get(next.getLeg()).getPrice());

                        return Double.compare(prePrice.getBasePrice().getTotal().doubleValue(), nexPrice.getBasePrice().getTotal().doubleValue());
                    })
                    .collect(Collectors.toList());

                var countLeg = selectedLegs.stream().count();

                itinerary.getLowest().add(selectedLegs.stream().findFirst().map(LegDescription::getLeg).get());
                itinerary.getHighest().add(selectedLegs.stream().skip(countLeg - 1).findFirst().map(LegDescription::getLeg).get());

                // sort date
                selectedLegs = legGroup.getLegsDesc()
                    .stream()
                    .sorted((previous, next) -> {
                        var preLeg = legs.get(previous.getLeg());
                        var nextLeg = legs.get(next.getLeg());

                        var preDeparture = ZonedDateTime.parse(preLeg.getDepartureTime());
                        var nextDeparture = ZonedDateTime.parse(nextLeg.getDepartureTime());

                        return preDeparture.compareTo(nextDeparture);
                    }).collect(Collectors.toList());

                legGroup.setLegsDesc(selectedLegs);

                itinerary.getLegGroups().add(legGroup);
            });

            directFlight.add(itinerary);
        });


        setItineraryIdAndDefaultLeg(directFlight, "1");

        return getItinerariesSortPrices(directFlight);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * filtering and group by all flights
     * -----------------------------------------------------------------------------------------------------------------
     */
    private void filteringCheapFlight() {

        var directionLegs = legs.groupBy(Leg::getDirectionIndex);


        directionLegs.forEachKey(direction -> {
            var airlineLegs = directionLegs.get(direction).groupBy(leg -> leg.getAirlines().stream().findFirst().map(LegAirline::getAirline));
            cheaps.put(Optional.of(direction), airlineLegs);
        });

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * pairing all possible flights to itineraries list
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return List
     */
    private List<Itinerary> getCheapFlights() {

        var cheapFlight = new ArrayList<Itinerary>();
        var multiDirection = transformSabres.size() > 1;

        var intersectAirlines = getIntersectAirlines();

        if (multiDirection) {
            // loop intersect airline
            intersectAirlines
                .forEach(airline -> {

                    var itinerary = new Itinerary();

                    // loop each direction
                    cheaps
                        .forEachKeyValue((directionIndex, airlineLegs) -> {

                            var selectedLegs = airlineLegs
                                .get(airline)
                                .toSortedList(Comparators.byDoubleFunction(leg -> prices.get(leg.getPrice()).getBasePrice().getTotal().doubleValue()))
                                .stream()
                                .map(leg -> {

                                    var legDescription = new LegDescription();
                                    legDescription.setLeg(leg.getId());

                                    return legDescription;
                                })
                                .collect(Collectors.toList());

                            var countLeg = selectedLegs.stream().count();

                            itinerary.getLowest().add(selectedLegs.stream().findFirst().map(LegDescription::getLeg).get());
                            itinerary.getHighest().add(selectedLegs.stream().skip(countLeg - 1).findFirst().map(LegDescription::getLeg).get());

                            selectedLegs = selectedLegs
                                .stream()
                                .sorted((previous, next) -> {
                                    var preLeg = legs.get(previous.getLeg());
                                    var nextLeg = legs.get(next.getLeg());

                                    var preDeparture = ZonedDateTime.parse(preLeg.getDepartureTime());
                                    var nextDeparture = ZonedDateTime.parse(nextLeg.getDepartureTime());

                                    return preDeparture.compareTo(nextDeparture);
                                }).collect(Collectors.toList());


                            var legGroup = new LegGroup();
                            legGroup.setAirline(airline.get());
                            legGroup.setLegsDesc(selectedLegs);

                            itinerary.getLegGroups().add(legGroup);

                        });


                    cheapFlight.add(itinerary);
                });

        }
        // loop difference airline
        var differenceAirlines = getDifferenceAirlines();

        var sameAirlineSet = differenceAirlines
            .flatCollect(Multimap::keySet).toSet()
            .containsAll(intersectAirlines);

        if (cheapFlight.isEmpty()) {
            setDifferenceAirlines(cheapFlight, differenceAirlines);
        } else {
            if (!sameAirlineSet) {
                setDifferenceAirlines(cheapFlight, differenceAirlines);
            } else {
                setItineraryIdAndDefaultLeg(cheapFlight, "0");
            }
        }

        return getItinerariesSortPrices(cheapFlight);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * set difference airlines into list
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param cheapFlight
     * @param differenceAirlines
     */
    private void setDifferenceAirlines(ArrayList<Itinerary> cheapFlight, MutableMap<Optional<Integer>, MutableBagMultimap<Optional<String>, Leg>> differenceAirlines) {

        var maxAirlineSize = differenceAirlines.values()
            .stream()
            .max(Comparator.comparingInt(previous -> previous.keySet().size()))
            .map(airlineLeg -> airlineLeg.keySet().size())
            .get();

        // loop maximum possible airline
        IntStream
            .range(0, maxAirlineSize)
            .forEachOrdered(airlineIndex -> {

                var itinerary = new Itinerary();

                // loop each direction
                differenceAirlines
                    .forEachKeyValue((directionIndex, airlineLegs) -> {

                        var airlineSize = airlineLegs.keySet().size();
                        var selectedAirline = airlineLegs.keySet().toList().get(this.selectedIndex(airlineSize, airlineIndex));

                        var selectedLegs = airlineLegs
                            .get(selectedAirline)
                            .toSortedList(Comparators.byDoubleFunction(leg -> prices.get(leg.getPrice()).getBasePrice().getTotal().doubleValue()))
                            .stream()
                            .map(leg -> {

                                var legDescription = new LegDescription();
                                legDescription.setLeg(leg.getId());

                                return legDescription;
                            })
                            .collect(Collectors.toList());

                        var countLeg = selectedLegs.stream().count();

                        itinerary.getLowest().add(selectedLegs.stream().findFirst().map(LegDescription::getLeg).get());
                        itinerary.getHighest().add(selectedLegs.stream().skip(countLeg - 1).findFirst().map(LegDescription::getLeg).get());

                        selectedLegs = selectedLegs
                            .stream()
                            .sorted((previous, next) -> {
                                var preLeg = legs.get(previous.getLeg());
                                var nextLeg = legs.get(next.getLeg());

                                var preDeparture = ZonedDateTime.parse(preLeg.getDepartureTime());
                                var nextDeparture = ZonedDateTime.parse(nextLeg.getDepartureTime());

                                return preDeparture.compareTo(nextDeparture);
                            }).collect(Collectors.toList());

                        var legGroup = new LegGroup();
                        legGroup.setAirline(selectedAirline.get());
                        legGroup.setLegsDesc(selectedLegs);
                        itinerary.getLegGroups().add(legGroup);

                    });


                cheapFlight.add(itinerary);
            });

        setItineraryIdAndDefaultLeg(cheapFlight, "0");
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get itineraries sort prices
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param itineraries
     * @return List
     */
    private List<Itinerary> getItinerariesSortPrices(ArrayList<Itinerary> itineraries) {
        return itineraries
            .stream()
            .sorted((previous, next) -> {
                var preTotal = (Double) previous
                    .getLowest()
                    .stream()
                    .map(leg -> prices.get(legs.get(leg).getPrice()).getBasePrice().getTotal().doubleValue())
                    .mapToDouble(Double::doubleValue).sum();
                var nextTotal = next
                    .getLowest()
                    .stream()
                    .map(leg -> prices.get(legs.get(leg).getPrice()).getBasePrice().getTotal().doubleValue())
                    .mapToDouble(Double::doubleValue).sum();

                return Double.compare(preTotal, nextTotal);
            }).collect(Collectors.toList());
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * set itinerary id and set default leg
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param itineraries
     * @param itineraryType
     */
    private void setItineraryIdAndDefaultLeg(ArrayList<Itinerary> itineraries, String itineraryType) {
        itineraries.forEach(itinerary -> {

            var directionIndex = new AtomicInteger();

            itinerary.setId(generateItineraryId(itinerary, itineraryType));

            itinerary
                .getLegGroups()
                .forEach(legGroup -> {

                    var lowest = itinerary.getLowest().get(directionIndex.getAndIncrement());

                    legGroup.getLegsDesc()
                        .stream()
                        .filter(legDescription -> legDescription.getLeg().equalsIgnoreCase(lowest))
                        .findFirst()
                        .get()
                        .setStatus(true);

                });
        });
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * generate itinerary id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param itinerary
     * @param itineraryType
     * @return String
     */
    private String generateItineraryId(Itinerary itinerary, String itineraryType) {

        var count = itinerary.getLegGroups().size();

        var airline = itinerary.getLegGroups()
            .stream()
            .map(LegGroup::getAirline)
            .collect(Collectors.joining("-"));

        var departureLegGroup = itinerary.getLegGroups().stream().findFirst().get();
        var arrivalLegGroup = itinerary.getLegGroups().stream().skip(count - 1).findFirst().get();

        var legDeparture = legs.get(departureLegGroup.getLegsDesc().stream().findFirst().get().getLeg());
        var legArrival = legs.get(arrivalLegGroup.getLegsDesc().stream().findFirst().get().getLeg());

        var classType = legDeparture.getSegments().stream().findFirst().get().getCabin();

        var delimiter = ":";

        return itineraryType
            .concat(delimiter)
            .concat(classType)
            .concat(delimiter)
            .concat(legDeparture.getDeparture())
            .concat(delimiter)
            .concat(Objects.requireNonNull(DateUtility.convertZonedDateTimeToDateString(legDeparture.getDepartureTime())))
            .concat(delimiter)
            .concat(Objects.requireNonNull(DateUtility.convertZonedDateTimeToDateString(legArrival.getArrivalTime())))
            .concat(delimiter)
            .concat(legArrival.getArrival())
            .concat("@")
            .concat(this.directions)
            .concat("@")
            .concat(airline);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get union airlines
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return Set
     */
    private MutableSet<Optional<String>> getUnionAirlines() {
        return cheaps.flatCollect(Multimap::keySet).toSet();
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get intersect airlines
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return Set
     */
    private MutableSet<Optional<String>> getIntersectAirlines() {
        MutableSet<Optional<String>> intersectAirlines = UnifiedSet.newSet();

        cheaps.forEachKeyValue((direction, airlineLegs) -> {

            var intersect = getUnionAirlines().intersect(airlineLegs.keySet());

            if (intersectAirlines.isEmpty()) {
                intersectAirlines.addAll(intersect);
            } else {
                intersectAirlines.removeAll(intersectAirlines.difference(intersect));
            }

        });
        return intersectAirlines;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get difference airline
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return Map
     */
    private MutableMap<Optional<Integer>, MutableBagMultimap<Optional<String>, Leg>> getDifferenceAirlines() {

        MutableMap<Optional<Integer>, MutableBagMultimap<Optional<String>, Leg>> differenceAirlines = UnifiedMap.newMap();

        var intersect = getIntersectAirlines();

        cheaps.forEachKeyValue((direction, airlineLegs) -> {

            var difference = airlineLegs.keySet().difference(intersect);
            if (difference.isEmpty()) {
                differenceAirlines.put(direction, airlineLegs);
            } else {
                differenceAirlines.put(direction, airlineLegs.selectKeysMultiValues((airline, leg) -> difference.contains(airline)));
            }
        });

        return differenceAirlines;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * processing data and transform to shopping data
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return ShoppingTransformEntity
     */
    public ShoppingTransformEntity processingTransformer() {

        var shopping = new ShoppingTransformEntity();

        shopping.setAirlines(airlines.toList());
        shopping.setAircrafts(aircrafts.toList());
        shopping.setLocations(locations.toList());
        shopping.setLayoverAirports(layoverAirports.toList());
        shopping.setPrices(prices.toList());
        shopping.setBaggages(baggages.toList());
        shopping.setSegments(segments.toList());
        shopping.setLegs(legs.toList());
        shopping.setDirect(getDirectFlights());
        shopping.setCheapest(getCheapFlights());

        return shopping;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * select index function for support difference airline selection
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param max
     * @param current
     * @return int
     */
    private int selectedIndex(int max, int current) {

        int selected = current;
        while (selected >= max) {
            selected = selected - max;
        }
        return selected;
    }

}
