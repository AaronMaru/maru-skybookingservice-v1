package com.skybooking.skyflightservice.v1_0_0.service.implement.baggages;

import com.skybooking.skyflightservice.v1_0_0.io.entity.baggages.BookingBaggageAllowanceEntity;
import com.skybooking.skyflightservice.v1_0_0.io.entity.baggages.BookingBaggageEntity;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.*;
import com.skybooking.skyflightservice.v1_0_0.io.repository.baggages.BookingBaggageAllowanceRP;
import com.skybooking.skyflightservice.v1_0_0.io.repository.baggages.BookingBaggageRP;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.baggages.BaggageSV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.DetailSV;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.BookingRequestTA;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BookingPassengerRQ;
import com.skybooking.skyflightservice.v1_0_0.util.booking.BookingUtility;
import com.skybooking.skyflightservice.v1_0_0.util.passenger.PassengerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BaggageIP implements BaggageSV {

  @Autowired private DetailSV detailSV;

  @Autowired private BookingBaggageAllowanceRP bookingBaggageAllowanceRP;

  @Autowired private BookingBaggageRP bookingBaggageRP;

  @Autowired private BookingUtility bookingUtility;

  /**
   * -----------------------------------------------------------------------------------------------------------------
   * insert baggage information into database
   * -----------------------------------------------------------------------------------------------------------------
   *
   * @param bookingRequestTA
   * @param bookingId
   * @return Boolean
   */
  @Override
  public Boolean insertBaggage(BookingRequestTA bookingRequestTA, Integer bookingId) {

    try {

      var shopping = detailSV.getShoppingDetail(bookingRequestTA.getRequest().getRequestId());
      if (shopping == null) return false;

      var passengerTypes =
          bookingRequestTA.getRequest().getPassengers().stream()
              .map(BookingPassengerRQ::getBirthDate)
              .map(PassengerUtil::type)
              .collect(Collectors.toList());

      for (String legId : bookingRequestTA.getRequest().getLegIds()) {

        shopping.getLegs().stream()
            .filter(it -> it.getId().equalsIgnoreCase(legId))
            .findFirst()
            .ifPresent(getLegConsumer(shopping, bookingId, passengerTypes));
      }

      return true;

    } catch (Exception ex) {
      ex.printStackTrace();
      return false;
    }
  }

  /**
   * -------------------------------------------------------------------------------------------------------------------
   * get leg detail and process inserting data to database
   * -------------------------------------------------------------------------------------------------------------------
   *
   * @param shopping
   * @param bookingId
   * @param passengerTypes
   * @return Consumer<Leg>
   */
  private Consumer<Leg> getLegConsumer(
      ShoppingTransformEntity shopping, Integer bookingId, List<String> passengerTypes) {
    return leg -> {
      var baggage =
          shopping.getBaggages().stream()
              .filter(it -> it.getId().equalsIgnoreCase(leg.getBaggage()))
              .findFirst()
              .get();

      leg.getSegments().stream()
          .map(getLegSegmentDetailSegmentFunction(shopping))
          .forEachOrdered(insertBookingBaggage(bookingId, baggage, passengerTypes));
    };
  }

  /**
   * -------------------------------------------------------------------------------------------------------------------
   * get segment detail
   * -------------------------------------------------------------------------------------------------------------------
   *
   * @param shopping
   * @return
   */
  private Function<LegSegmentDetail, Segment> getLegSegmentDetailSegmentFunction(
      ShoppingTransformEntity shopping) {
    return legSegmentDetail ->
        shopping.getSegments().stream()
            .filter(it -> it.getId().equalsIgnoreCase(legSegmentDetail.getSegment()))
            .findFirst()
            .get();
  }

  /**
   * -------------------------------------------------------------------------------------------------------------------
   * function transform baggage to booking baggage allowance entity
   * -------------------------------------------------------------------------------------------------------------------
   *
   * @param bookingBaggageId
   * @return Function
   */
  private Function<Baggage, BookingBaggageAllowanceEntity>
      getBaggageBookingBaggageAllowanceEntityFunction(Integer bookingBaggageId) {
    return baggage -> {
      var bookingBaggageAllowance = new BookingBaggageAllowanceEntity();

      var isPiece = baggage.isPiece();

      if (isPiece) {
        bookingBaggageAllowance.setIsPiece((byte) 1);
        bookingBaggageAllowance.setPieces(baggage.getPieces());
        bookingBaggageAllowance.setUnit("");
        bookingBaggageAllowance.setWeight(0);
      } else {
        bookingBaggageAllowance.setIsPiece((byte) 0);
        bookingBaggageAllowance.setPieces(0);
        bookingBaggageAllowance.setUnit(baggage.getUnit());
        bookingBaggageAllowance.setWeight(baggage.getWeights());
      }

      bookingBaggageAllowance.setPassType(bookingUtility.getPassengerType(baggage.getType()));
      bookingBaggageAllowance.setBaggage_id(bookingBaggageId);

      return bookingBaggageAllowance;
    };
  }

  /**
   * -------------------------------------------------------------------------------------------------------------------
   * insert baggage and baggage allowance list into database
   * -------------------------------------------------------------------------------------------------------------------
   *
   * @param bookingId
   * @param baggage
   * @param passengerTypes
   * @return
   */
  private Consumer<Segment> insertBookingBaggage(
      Integer bookingId, BaggageDetail baggage, List<String> passengerTypes) {
    return segment -> {
      var bookingBaggage = new BookingBaggageEntity();
      bookingBaggage.setBookingId(bookingId);
      bookingBaggage.setLocationCode(
          segment.getDeparture().concat("-").concat(segment.getArrival()));

      // insert data to booking_baggage
      var savedBookingBaggage = bookingBaggageRP.save(bookingBaggage);
      var bookingBaggageId = savedBookingBaggage.getId();

      // insert data to booking_baggage_allowance
      var bookingBaggageAllowances =
          baggage.getDetails().stream()
              .filter(it -> passengerTypes.contains(it.getType()))
              .map(getBaggageBookingBaggageAllowanceEntityFunction(bookingBaggageId))
              .collect(Collectors.toList());

      bookingBaggageAllowanceRP.saveAll(bookingBaggageAllowances);
    };
  }
}
