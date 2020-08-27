package com.skybooking.skyflightservice.v1_0_0.service.implement.backoffice;

import com.fasterxml.jackson.databind.JsonNode;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.ApplicationResultTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.OfflineItineraryTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.TravelItineraryTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.*;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.accountinfo.*;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.accountinfo.document.AccountDocumentTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.accountinfo.payment.AccountPaymentCommissionTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.accountinfo.payment.AccountPaymentPaymentTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.accountinfo.tax.AccountTaxTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.accountinfo.ticketing.AccountTicketingETicketTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.accountinfo.ticketing.AccountTicketingExchangeTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.accountinfo.ticketing.AccountTicketingTicketingTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.customerinfo.*;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryRef.ItineraryRefSource;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.ItineraryPricingTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.ItineraryReservationItemsTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.ItineraryTicketingTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.itinerarypricing.ItineraryPriceQuoteTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.itinerarypricing.ItineraryPriceQuoteTotalTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.itinerarypricing.price.ItineraryPriceModelTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.itinerarypricing.pricequote.*;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.itinerarypricing.pricequote.priceditinerary.*;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.itinerarypricing.tax.ItineraryTaxesTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.itineraryreservation.*;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.itineraryreservation.flightsegment.*;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.remarkinfo.RemarkTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.reservation.*;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.specialserviceinfo.SpecialServiceInfoAirlineTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.specialserviceinfo.SpecialServiceInfoPersonNameTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.specialserviceinfo.SpecialServiceInfoServiceTA;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItineraryConverter {

    OfflineItineraryTA getItinerary(JsonNode json) {
        return OfflineItineraryTA.builder()
            .applicationResult(applicationResult(json.get("ApplicationResults")))
            .travelItinerary(travelItinerary(json.get("TravelItinerary")))
            .build();
    }

    ApplicationResultTA applicationResult(JsonNode json) {
        return ApplicationResultTA.builder()
            .status(json.get("status").asText())
            .build();
    }

    TravelItineraryTA travelItinerary(JsonNode json) {
        return TravelItineraryTA.builder()
            .customerInfo(json.has("CustomerInfo")
                ? customerInfo(json.get("CustomerInfo"))
                : null)
            .accountingInfo(json.has("AccountingInfo")
                ? accountingInfos(json.get("AccountingInfo"))
                : null)
            .remarkInfo(json.has("RemarkInfo")
                ? remarkInfo(json.get("RemarkInfo"))
                : null)
            .itineraryInfo(json.has("ItineraryInfo")
                ? itineraryInfo(json.get("ItineraryInfo"))
                : null)
            .openReservationElements(json.has("OpenReservationElements")
                ? openReservationElements(json.get("OpenReservationElements"))
                : null)
            .itineraryRef(json.has("ItineraryRef")
                ? itineraryRef(json.get("ItineraryRef"))
                : null)
            .specialServiceInfo(json.has("SpecialServiceInfo")
                ? specialServiceInfo(json.get("SpecialServiceInfo"))
                : null)
            .build();
    }

    CustomerInfoTA customerInfo(JsonNode json) {
        List<PersonNameTA> personNames = new ArrayList<>();
        if (json.get("PersonName").isArray()) {
            json.get("PersonName").forEach(item -> {
                personNames.add(personNameTA(item));
            });
        } else {
            personNames.add(personNameTA(json.get("PersonName")));
        }

        JsonNode form = json.get("PaymentInfo").get("Payment").get("Form");
        PaymentFormTA paymentForm = PaymentFormTA.builder()
            .text(form.get("Text").asText(""))
            .rph(form.get("RPH").asText(""))
            .id(form.get("Id").asInt(0))
            .build();
        PaymentTA payment = PaymentTA.builder().form(paymentForm).build();
        PaymentInfoTA paymentInfo = PaymentInfoTA.builder().payment(payment).build();

        JsonNode contactNumbersJson = json.get("ContactNumbers").get("ContactNumber");
        ContactNumberTA contactNumber = ContactNumberTA.builder()
            .id(contactNumbersJson.get("Id").asInt(0))
            .rph(contactNumbersJson.get("RPH").asText(""))
            .locationCode(contactNumbersJson.get("LocationCode").asText(""))
            .phone(contactNumbersJson.get("Phone").asText(""))
            .build();
        ContactNumbersTA contactNumbers = ContactNumbersTA.builder().contactNumber(contactNumber).build();

        List<AddressLineTA> addressLines = new ArrayList<>();
        json.get("Address").get("AddressLine").forEach(item -> {
            AddressLineTA addressLine = AddressLineTA.builder()
                .id(item.get("Id").asInt(0))
                .type(item.get("type").asText(""))
                .content(item.get("content").asText(""))
                .build();
            addressLines.add(addressLine);
        });
        AddressTA address = AddressTA.builder().addressLine(addressLines).build();

        return CustomerInfoTA.builder()
            .personNames(personNames)
            .paymentInfo(paymentInfo)
            .contactNumbers(contactNumbers)
            .address(address)
            .build();
    }

    List<AccountingInfoTA> accountingInfos(JsonNode json) {
        List<AccountingInfoTA> accountingInfos = new ArrayList<>();
        if (json.isArray()) {
            json.forEach(item -> {
                accountingInfos.add(accountingInfo(item));
            });
        } else {
            accountingInfos.add(accountingInfo(json));
        }

        return accountingInfos;
    }

    RemarkInfoTA remarkInfo(JsonNode json) {
        RemarkTA remark = null;
        if (json.has("Remark")) {
            JsonNode remarkJson = json.get("Remark");
            remark = RemarkTA.builder()
                .type(remarkJson.get("Type").asText(""))
                .text(remarkJson.get("Text").asText(""))
                .rph(remarkJson.get("RPH").asText(""))
                .id(remarkJson.get("Id").asInt(0))
                .build();
        }

        return RemarkInfoTA.builder()
            .remark(remark)
            .build();
    }

    ItineraryInfoTA itineraryInfo(JsonNode json) {
        return ItineraryInfoTA.builder()
            .reservationItem(json.has("ReservationItems") ? itineraryReservationItemsTA(json.get("ReservationItems")) : null)
            .itineraryPricing(json.has("ItineraryPricing") ? itineraryPricingTA(json.get("ItineraryPricing")) : null)
            .ticketing(json.has("Ticketing") ? itineraryTicketingTA(json.get("Ticketing")) : null)
            .build();
    }

    OpenReservationElementsTA openReservationElements(JsonNode json) {
        return OpenReservationElementsTA.builder()
            .openReservationElement(json.has("OpenReservationElement")
                ? openReservationElement(json.get("OpenReservationElement"))
                : null)
            .build();
    }

    ItineraryRefTA itineraryRef(JsonNode json) {
        JsonNode sourceJson = json.get("Source");
        ItineraryRefSource source = ItineraryRefSource.builder()
            .homePseudoCityCode(sourceJson.get("HomePseudoCityCode").asText(""))
            .lastUpdateDateTime(sourceJson.get("LastUpdateDateTime").asText(""))
            .receivedFrom(sourceJson.get("ReceivedFrom").asText(""))
            .createDateTime(sourceJson.get("CreateDateTime").asText(""))
            .sequenceNumber(sourceJson.get("SequenceNumber").asInt(0))
            .aaaPseudoCityCode(sourceJson.get("AAA_PseudoCityCode").asText(""))
            .creationAgent(sourceJson.get("CreationAgent").asText(""))
            .pseudoCityCode(sourceJson.get("PseudoCityCode").asText(""))
            .build();

        return ItineraryRefTA.builder()
            .id(json.get("ID").asText(""))
            .header(json.get("Header").asText(""))
            .airExtras(json.get("AirExtras").asBoolean())
            .partitionId(json.get("PartitionID").asText(""))
            .primeHostId(json.get("PrimeHostID").asText(""))
            .inhibitCode(json.get("InhibitCode").asText(""))
            .source(source)
            .build();
    }

    List<SpecialServiceInfoTA> specialServiceInfo(JsonNode json) {
        List<SpecialServiceInfoTA> specialServiceInfos = new ArrayList<>();
        if (json.isArray()) {
            json.forEach(item -> {
                specialServiceInfos.add(specialServiceInfoTA(item));
            });
        } else {
            specialServiceInfos.add(specialServiceInfoTA(json));
        }

        return specialServiceInfos;
    }

    PersonNameTA personNameTA(JsonNode json) {
        return PersonNameTA.builder()
            .elementId(json.get("elementId").asText(""))
            .surname(json.get("Surname").asText(""))
            .givenName(json.get("GivenName").asText(""))
            .passengerType(json.get("PassengerType").asText(""))
            .nameNumber(json.get("NameNumber").asDouble(0))
            .withInfant(json.get("WithInfant").asBoolean())
            .rph(json.get("RPH").asInt(0))
            .build();
    }

    SpecialServiceInfoTA specialServiceInfoTA(JsonNode json) {
        JsonNode serviceJson = json.get("Service");
        SpecialServiceInfoPersonNameTA personNameTA = null;
        SpecialServiceInfoAirlineTA airlineTA = null;

        if (serviceJson.has("PersonName")) {
            personNameTA = SpecialServiceInfoPersonNameTA.builder()
                .content(serviceJson.get("PersonName").get("content").asText(""))
                .nameNumber(serviceJson.get("PersonName").get("NameNumber").asText(""))
                .build();
        }
        if (serviceJson.has("Airline")) {
            airlineTA = SpecialServiceInfoAirlineTA.builder()
                .code(serviceJson.get("Airline").get("Code").asText(""))
                .build();
        }

        SpecialServiceInfoServiceTA service = SpecialServiceInfoServiceTA.builder()
            .ssrCode(serviceJson.get("SSR_Code").asText(""))
            .ssrType(serviceJson.get("SSR_Type").asText(""))
            .text(serviceJson.get("Text").asText(""))
            .personName(personNameTA)
            .airline(airlineTA)
            .build();

        return SpecialServiceInfoTA.builder()
            .id(json.get("Id").asInt(0))
            .rph(json.get("RPH").asText(""))
            .type(json.get("Type").asText(""))
            .service(service)
            .build();
    }

    List<OpenReservationElementTA> openReservationElement(JsonNode json) {
        List<OpenReservationElementTA> openReservationElements = new ArrayList<>();
        if (json.isArray()) {
            json.forEach(item -> {
                OpenReservationElementTA openReservationElement = OpenReservationElementTA.builder()
                    .id(item.get("id").asInt(0))
                    .elementId(item.get("elementId").asText(""))
                    .type(item.has("type") ? item.get("type").asText() : null)
                    .nameAssociation(item.has("NameAssociation")
                        ? nameAssociationTA(item.get("NameAssociation"))
                        : null)
                    .serviceRequest(item.has("ServiceRequest")
                        ? serviceRequestTA(item.get("ServiceRequest"))
                        : null)
                    .segmentAssociation(item.has("SegmentAssociation")
                        ? segmentAssociationTA(item.get("SegmentAssociation"))
                        : null)
                    .build();
                openReservationElements.add(openReservationElement);
            });
        }
        return openReservationElements;
    }

    ReservationNameAssociationTA nameAssociationTA(JsonNode json) {
        return ReservationNameAssociationTA.builder()
            .firstName(json.get("FirstName").asText())
            .lastName(json.get("LastName").asText())
            .referenceId(json.get("ReferenceId").asInt())
            .nameRefNumber(json.get("NameRefNumber").asDouble())
            .build();
    }

    ReservationServiceRequestTA serviceRequestTA(JsonNode json) {
        return ReservationServiceRequestTA.builder()
            .serviceType(json.has("serviceType") ? json.get("serviceType").asText() : null)
            .code(json.has("code") ? json.get("code").asText() : null)
            .serviceCount(json.has("serviceCount") ? json.get("serviceCount").asInt() : null)
            .ssrType(json.has("ssrType") ? json.get("ssrType").asText() : null)
            .airlineCode(json.has("airlineCode") ? json.get("airlineCode").asText() : null)
            .fullText(json.has("FullText") ? json.get("FullText").asText() : null)
            .actionCode(json.has("actionCode") ? json.get("actionCode").asText() : null)
            .freeText(json.has("FreeText") ? json.get("FreeText").asText() : null)
            .build();
    }

    ReservationSegmentAssociationTA segmentAssociationTA(JsonNode json) {
        ReservationAirSegment airSegment = null;
        if (json.has("AirSegment")) {
            JsonNode airSegJson = json.get("AirSegment");
            airSegment = ReservationAirSegment.builder()
                .flightNumber(json.has("FlightNumber") ? airSegJson.get("FlightNumber").asText() : null)
                .carrierCode(json.has("CarrierCode") ? airSegJson.get("CarrierCode").asText() : null)
                .departureDate(json.has("DepartureDate") ? airSegJson.get("DepartureDate").asText() : null)
                .boardPoint(json.has("BoardPoint") ? airSegJson.get("BoardPoint").asText() : null)
                .classOfService(json.has("ClassOfService") ? airSegJson.get("ClassOfService").asText() : null)
                .offPoint(json.has("OffPoint") ? airSegJson.get("OffPoint").asText() : null)
                .build();
        }

        return ReservationSegmentAssociationTA.builder()
            .id(json.has("Id") ? json.get("Id").asInt() : null)
            .segmentAssociationId(json.has("SegmentAssociationId") ? json.get("SegmentAssociationId").asInt() : null)
            .airSegment(airSegment)
            .build();
    }

    AccountingInfoTA accountingInfo(JsonNode json) {
        AccountBaseFareTA baseFare = json.has("BaseFare") ? accountBaseFareTA(json.get("BaseFare")) : null;
        AccountPersonNameTA personName = json.has("PersonName") ? accountPersonNameTA(json.get("PersonName")) : null;
        AccountAirlineTA airline = json.has("Airline") ? accountAirlineTA(json.get("Airline")) : null;
        AccountTicketingInfoTA ticketingInfo = json.has("TicketingInfo") ? accountTicketingInfoTA(json.get("TicketingInfo")) : null;
        AccountPaymentInfoTA paymentInfo = json.has("PaymentInfo") ? accountPaymentInfoTA(json.get("PaymentInfo")) : null;
        AccountDocumentInfoTA documentInfo = json.has("DocumentInfo") ? accountDocumentInfoTA(json.get("DocumentInfo")) : null;
        AccountTaxesTA taxes = json.has("Taxes") ? accountTaxesTA(json.get("Taxes")) : null;

        return AccountingInfoTA.builder()
            .id(json.has("Id") ? json.get("Id").asInt() : null)
            .fareApplication(json.has("FareApplication") ? json.get("FareApplication").asText() : null)
            .baseFare(baseFare)
            .personName(personName)
            .airline(airline)
            .ticketingInfo(ticketingInfo)
            .paymentInfo(paymentInfo)
            .documentInfo(documentInfo)
            .taxes(taxes)
            .build();
    }

    AccountBaseFareTA accountBaseFareTA(JsonNode json) {
        return AccountBaseFareTA.builder()
            .currencyCode(json.has("CurrencyCode") ? json.get("CurrencyCode").asText() : null)
            .amount(json.has("Amount") ? BigDecimal.valueOf(json.get("Amount").asDouble()) : null)
            .build();
    }

    AccountPersonNameTA accountPersonNameTA(JsonNode json) {
        return AccountPersonNameTA.builder()
            .content(json.has("content") ? json.get("content").asText() : null)
            .nameNumber(json.has("NameNumber") ? json.get("NameNumber").asDouble() : null)
            .build();
    }

    AccountAirlineTA accountAirlineTA(JsonNode json) {
        return AccountAirlineTA.builder()
            .code(json.has("Code") ? json.get("Code").asText() : null)
            .build();
    }

    AccountTicketingInfoTA accountTicketingInfoTA(JsonNode json) {
        AccountTicketingExchangeTA exchange = null;
        AccountTicketingETicketTA eTicket = null;
        AccountTicketingTicketingTA ticketing = null;
        if (json.has("Exchange")) {
            JsonNode exchangeJson = json.get("Exchange");
            exchange = AccountTicketingExchangeTA.builder()
                .ind(exchangeJson.has("Ind") ? exchangeJson.get("Ind").asBoolean() : null)
                .build();
        }
        if (json.has("eTicket")) {
            JsonNode eTicketJson = json.get("eTicket");
            eTicket = AccountTicketingETicketTA.builder()
                .ind(eTicketJson.has("Ind") ? eTicketJson.get("Ind").asBoolean() : null)
                .build();
        }
        if (json.has("Ticketing")) {
            JsonNode ticketingJson = json.get("Ticketing");
            ticketing = AccountTicketingTicketingTA.builder()
                .conjunctedCount(ticketingJson.has("ConjunctedCount") ? ticketingJson.get("ConjunctedCount").asInt() : null)
                .build();
        }
        return AccountTicketingInfoTA.builder()
            .tariffBasis(json.has("TariffBasis") ? json.get("TariffBasis").asText() : null)
            .exchange(exchange)
            .eTicket(eTicket)
            .ticketing(ticketing)
            .build();
    }

    AccountPaymentInfoTA accountPaymentInfoTA(JsonNode json) {
        AccountPaymentPaymentTA payment = null;
        AccountPaymentCommissionTA commission = null;
        if (json.has("Payment")) {
            JsonNode paymentJson = json.get("Payment");
            payment = AccountPaymentPaymentTA.builder()
                .form(paymentJson.has("Form") ? paymentJson.get("Form").asText() : null)
                .build();
        }
        if (json.has("Commission")) {
            JsonNode formJson = json.get("Commission");
            commission = AccountPaymentCommissionTA.builder()
                .amount(formJson.has("Amount") ? BigDecimal.valueOf(formJson.get("Amount").asDouble()) : null)
                .build();
        }
        return AccountPaymentInfoTA.builder().payment(payment).commission(commission).build();
    }

    AccountDocumentInfoTA accountDocumentInfoTA(JsonNode json) {
        AccountDocumentTA document = null;
        if (json.has("Document")) {
            JsonNode documentJson = json.get("Document");
            document = AccountDocumentTA.builder()
                .number(documentJson.has("Number") ? documentJson.get("Number").asInt() : null)
                .build();
        }
        return AccountDocumentInfoTA.builder().document(document).build();
    }

    AccountTaxesTA accountTaxesTA(JsonNode json) {
        AccountTaxTA tax = null;
        if (json.has("Tax")) {
            JsonNode taxJson = json.get("Tax");
            tax = AccountTaxTA.builder()
                .amount(taxJson.has("Amount") ? BigDecimal.valueOf(taxJson.get("Amount").asDouble()) : null)
                .build();
        }
        return AccountTaxesTA.builder().tax(tax).build();
    }

    ItineraryReservationItemsTA itineraryReservationItemsTA(JsonNode json) {
        List<ItineraryReservationItemTA> items = new ArrayList<>();
        if (json.has("Item")) {
            JsonNode itemJsons = json.get("Item");
            if (itemJsons.isArray()) {
                itemJsons.forEach(itemJson -> {
                    items.add(itineraryReservationItemTA(itemJson));
                });
            } else {
                items.add(itineraryReservationItemTA(itemJsons));
            }
        }

        return ItineraryReservationItemsTA.builder().item(items).build();
    }

    ItineraryReservationItemTA itineraryReservationItemTA(JsonNode json) {
        return ItineraryReservationItemTA.builder()
            .rph(json.has("RPH") ? json.get("RPH").asInt() : null)
            .flightSegment(json.has("FlightSegment") ? reservationFlightSegmentTA(json.get("FlightSegment")) : null)
            .product(json.has("Product") ? reservationProductTA(json.get("Product")) : null)
            .build();
    }

    ItineraryPricingTA itineraryPricingTA(JsonNode json) {
        return ItineraryPricingTA.builder()
            .priceQuoteTotal(json.has("PriceQuoteTotals") ? itineraryPriceQuoteTotalTA(json.get("PriceQuoteTotals")) : null)
            .priceQuote(json.has("PriceQuote") ? itineraryPriceQuoteTAList(json.get("PriceQuote")) : null)
            .build();
    }

    List<ItineraryPriceQuoteTA> itineraryPriceQuoteTAList(JsonNode json) {
        List<ItineraryPriceQuoteTA> priceQuoteTAList = new ArrayList<>();
        if (json.isArray()) {
            json.forEach(item -> {
                priceQuoteTAList.add(itineraryPriceQuoteTA(item));
            });
        } else {
            priceQuoteTAList.add(itineraryPriceQuoteTA(json));
        }

        return priceQuoteTAList;
    }

    ItineraryPriceQuoteTA itineraryPriceQuoteTA(JsonNode json) {
        ItineraryPriceResponseHeaderTA responseHeader = null;
        ItineraryPriceQuotePlusTA priceQuotePlusTA = null;
        if (json.has("ResponseHeader")) {
             JsonNode responseHeaderJson = json.get("ResponseHeader");
             if (responseHeaderJson.has("Text"))
                 responseHeader = ItineraryPriceResponseHeaderTA.builder().text(responseHeaderJson.get("Text").asText()).build();
        }
        if (json.has("PriceQuotePlus")) {
            JsonNode priceQuoteJson = json.get("PriceQuotePlus");
            ItineraryPricePassengerInfoTA passengerInfoTA = null;
            if (priceQuoteJson.has("PassengerInfo")) {
                JsonNode passengerInfoJson = priceQuoteJson.get("PassengerInfo");
                List<ItineraryPricePassengerDataTA> passengerDataTAS = new ArrayList<>();
                if (passengerInfoJson.has("PassengerData")) {
                    JsonNode passengerDataJson = passengerInfoJson.get("PassengerData");
                    if (passengerDataJson.isArray()) {
                        passengerDataJson.forEach(item -> {
                            ItineraryPricePassengerDataTA pricePassengerData = ItineraryPricePassengerDataTA.builder()
                                .content(item.has("content") ? item.get("content").asText() : null)
                                .nameNumber(item.has("NameNumber") ? item.get("NameNumber").asDouble() : null)
                                .build();
                            passengerDataTAS.add(pricePassengerData);
                        });
                    } else {
                        ItineraryPricePassengerDataTA pricePassengerData = ItineraryPricePassengerDataTA.builder()
                            .content(passengerDataJson.has("content") ? passengerDataJson.get("content").asText() : null)
                            .nameNumber(passengerDataJson.has("NameNumber") ? passengerDataJson.get("NameNumber").asDouble() : null)
                            .build();
                        passengerDataTAS.add(pricePassengerData);
                    }
                }
                passengerInfoTA = ItineraryPricePassengerInfoTA.builder()
                    .passengerType(passengerInfoJson.has("PassengerType") ? passengerInfoJson.get("PassengerType").asText() : null)
                    .passengerData(passengerDataTAS)
                    .build();

            }
            priceQuotePlusTA = ItineraryPriceQuotePlusTA.builder()
                .tourCode(priceQuoteJson.has("TourCode") ? priceQuoteJson.get("TourCode").asText() : null)
                .verifyFareCalc(priceQuoteJson.has("VerifyFareCalc") ? priceQuoteJson.get("VerifyFareCalc").asBoolean() : null)
                .nucSuppresion(priceQuoteJson.has("NUCSuppresion") ? priceQuoteJson.get("NUCSuppresion").asBoolean() : null)
                .ticketingInstructionsInfo(priceQuoteJson.has("TicketingInstructionsInfo") ? priceQuoteJson.get("TicketingInstructionsInfo").asText() : null)
                .itBtFare(priceQuoteJson.has("IT_BT_Fare") ? priceQuoteJson.get("IT_BT_Fare").asText() : null)
                .domesticIntlInd(priceQuoteJson.has("DomesticIntlInd") ? priceQuoteJson.get("DomesticIntlInd").asText() : null)
                .manualFare(priceQuoteJson.has("ManualFare") ? priceQuoteJson.get("ManualFare").asBoolean() : null)
                .subjToGovtApproval(priceQuoteJson.has("SubjToGovtApproval") ? priceQuoteJson.get("SubjToGovtApproval").asBoolean() : null)
                .discountAmount(priceQuoteJson.has("DiscountAmount") ? BigDecimal.valueOf(priceQuoteJson.get("DiscountAmount").asDouble()) : null)
                .pricingStatus(priceQuoteJson.has("PricingStatus") ? priceQuoteJson.get("PricingStatus").asText() : null)
                .systemIndicator(priceQuoteJson.has("SystemIndicator") ? priceQuoteJson.get("SystemIndicator").asText() : null)
                .negotiatedFare(priceQuoteJson.has("NegotiatedFare") ? priceQuoteJson.get("NegotiatedFare").asBoolean() : null)
                .displayOnly(priceQuoteJson.has("DisplayOnly") ? priceQuoteJson.get("DisplayOnly").asBoolean() : null)
                .itineraryChanged(priceQuoteJson.has("ItineraryChanged") ? priceQuoteJson.get("ItineraryChanged").asBoolean() : null)
                .passengerInfo(passengerInfoTA)
                .build();
        }


        return ItineraryPriceQuoteTA.builder()
            .rph(json.has("RPH") ? json.get("RPH").asText() : null)
            .responseHeader(responseHeader)
            .pricedItinerary(json.has("PricedItinerary") ? itineraryPricedItineraryTA(json.get("PricedItinerary")) : null)
            .priceQuotePlus(priceQuotePlusTA)
            .build();
    }

    ItineraryPricedItineraryTA itineraryPricedItineraryTA(JsonNode json) {
        return ItineraryPricedItineraryTA.builder()
            .taxExempt(json.has("TaxExempt") ? json.get("TaxExempt").asBoolean() : null)
            .validatingCarrier(json.has("ValidatingCarrier") ? json.get("ValidatingCarrier").asText() : null)
            .displayOnly(json.has("DisplayOnly") ? json.get("DisplayOnly").asBoolean() : null)
            .rph(json.has("RPH") ? json.get("RPH").asInt() : null)
            .inputMessage(json.has("InputMessage") ? json.get("InputMessage").asText() : null)
            .statusCode(json.has("StatusCode") ? json.get("StatusCode").asText() : null)
            .storedDateTime(json.has("StoredDateTime") ? json.get("StoredDateTime").asText() : null)
            .airItineraryPricingInfo(json.has("AirItineraryPricingInfo") ? airItineraryPricingInfoTA(json.get("AirItineraryPricingInfo")) : null)
            .build();
    }

    AirItineraryPricingInfoTA airItineraryPricingInfoTA(JsonNode json) {
        PtcFareBreakdownTA ptc = null;
        if (json.has("PTC_FareBreakdown")) {
            JsonNode ptcJson = json.get("PTC_FareBreakdown");
            List<PtcFlightSegmentTA> flightSegments = new ArrayList<>();
            if (ptcJson.has("FlightSegment")) {
                JsonNode flightSegmentJson = ptcJson.get("FlightSegment");
                if (flightSegmentJson.isArray()) {
                    flightSegmentJson.forEach(item -> {
                        flightSegments.add(ptcFlightSegmentTA(item));
                    });
                } else {
                    flightSegments.add(ptcFlightSegmentTA(flightSegmentJson));
                }
                ptc = PtcFareBreakdownTA.builder().flightSegment(flightSegments).build();
            }
        }

        return AirItineraryPricingInfoTA.builder()
            .ptcFareBreakdown(ptc)
            .build();
    }

    PtcFlightSegmentTA ptcFlightSegmentTA(JsonNode json) {
        MarketingAirlineTA marketingAirlineTA = !json.has("MarketingAirline")
            ? null :
            MarketingAirlineTA.builder()
                .code(json.get("MarketingAirline").has("Code") ? json.get("MarketingAirline").get("Code").asText() : null)
                .flightNumber(json.get("MarketingAirline").has("FlightNumber") ? json.get("MarketingAirline").get("FlightNumber").asInt() : null)
                .build();

        BaggageAllowanceTA baggageAllowanceTA = !json.has("BaggageAllowance")
            ? null :
            BaggageAllowanceTA.builder()
                .number(json.get("BaggageAllowance").has("Number") ? json.get("BaggageAllowance").get("Number").asText() : null)
                .build();

        FareBasisTA fareBasisTA = !json.has("FareBasis")
            ? null :
            FareBasisTA.builder()
                .code(json.get("FareBasis").has("Code") ? json.get("FareBasis").get("Code").asText() : null)
                .build();

        ValidityDatesTA validityDatesTA = !json.has("ValidityDates")
            ? null :
            ValidityDatesTA.builder()
                .notValidBefore(json.get("ValidityDates").has("Code") ? json.get("ValidityDates").get("Code").asText() : null)
                .notValidAfter(json.get("ValidityDates").has("Code") ? json.get("ValidityDates").get("Code").asText() : null)
                .build();

        SegmentLocationTA originLocation = !json.has("OriginLocation")
            ? null :
            SegmentLocationTA.builder()
                .locationCode(json.get("OriginLocation").has("LocationCode") ? json.get("OriginLocation").get("LocationCode").asText() : null)
                .build();

        return PtcFlightSegmentTA.builder()
            .segmentNumber(json.has("SegmentNumber") ? json.get("SegmentNumber").asInt() : null)
            .status(json.has("Status") ? json.get("Status").asText() : null)
            .departureDateTime(json.has("DepartureDateTime") ? json.get("DepartureDateTime").asText() : null)
            .connectionInd(json.has("ConnectionInd") ? json.get("ConnectionInd").asText() : null)
            .flightNumber(json.has("FlightNumber") ? json.get("FlightNumber").asInt() : null)
            .resBookDesigCode(json.has("ResBookDesigCode") ? json.get("ResBookDesigCode").asText() : null)
            .marketingAirline(marketingAirlineTA)
            .baggageAllowance(baggageAllowanceTA)
            .fareBasis(fareBasisTA)
            .validityDates(validityDatesTA)
            .originLocation(originLocation)
            .build();
    }

    ItineraryPriceQuoteTotalTA itineraryPriceQuoteTotalTA(JsonNode json) {
        ItineraryPriceModelTA baseFare = null;
        ItineraryPriceModelTA totalFare = null;
        ItineraryPriceModelTA equivFare = null;
        ItineraryTaxesTA taxes= null;

        if (json.has("BaseFare")) {
            JsonNode baseFareJson = json.get("BaseFare");
            baseFare = ItineraryPriceModelTA.builder()
                .amount(baseFareJson.has("Amount") ? BigDecimal.valueOf(baseFareJson.get("Amount").asDouble()) : null)
                .currency(baseFareJson.has("CurrencyCode") ? baseFareJson.get("CurrencyCode").asText() : null)
                .build();
        }
        if (json.has("TotalFare")) {
            JsonNode totalFareJson = json.get("TotalFare");
            totalFare = ItineraryPriceModelTA.builder()
                .amount(totalFareJson.has("Amount") ? BigDecimal.valueOf(totalFareJson.get("Amount").asDouble()) : null)
                .currency(totalFareJson.has("CurrencyCode") ? totalFareJson.get("CurrencyCode").asText() : null)
                .build();
        }
        if (json.has("EquivFare")) {
            JsonNode equivFareJson = json.get("EquivFare");
            equivFare = ItineraryPriceModelTA.builder()
                .amount(equivFareJson.has("Amount") ? BigDecimal.valueOf(equivFareJson.get("Amount").asDouble()) : null)
                .currency(equivFareJson.has("CurrencyCode") ? equivFareJson.get("CurrencyCode").asText() : null)
                .build();
        }
        if (json.has("Taxes")) {
            JsonNode taxesJson = json.get("Taxes");
            if (taxesJson.has("Tax")) {
                JsonNode taxJson = taxesJson.get("Tax");
                ItineraryPriceModelTA tax = ItineraryPriceModelTA.builder()
                    .amount(taxJson.has("Amount") ? BigDecimal.valueOf(taxJson.get("Amount").asDouble()) : null)
                    .currency(taxJson.has("CurrencyCode") ? taxJson.get("CurrencyCode").asText() : null)
                    .build();
                taxes = ItineraryTaxesTA.builder().tax(tax).build();
            }
        }

        return ItineraryPriceQuoteTotalTA.builder()
            .baseFare(baseFare)
            .totalFare(totalFare)
            .equivFare(equivFare)
            .taxes(taxes)
            .build();
    }

    List<ItineraryTicketingTA> itineraryTicketingTA(JsonNode json) {
        List<ItineraryTicketingTA> itineraryTicketing = new ArrayList<>();
        json.forEach(item -> {
            ItineraryTicketingTA ticketing = ItineraryTicketingTA.builder()
                .rph(item.has("RPH") ? item.get("RPH").asText() : null)
                .eTicketNumber(item.has("eTicketNumber") ? item.get("eTicketNumber").asText() : null)
                .ticketTimeLimit(item.has("TicketTimeLimit") ? item.get("TicketTimeLimit").asText() : null)
                .build();
            itineraryTicketing.add(ticketing);
        });

        return itineraryTicketing;
    }

    ReservationFlightSegmentTA reservationFlightSegmentTA(JsonNode json) {
        return ReservationFlightSegmentTA.builder()
            .codeShare(json.has("CodeShare") ? json.get("CodeShare").asBoolean() : null)
            .numberInParty(json.has("NumberInParty") ? json.get("NumberInParty").asText() : null)
            .stopQuantity(json.has("StopQuantity") ? json.get("StopQuantity").asText() : null)
            .elapsedTime(json.has("ElapsedTime") ? json.get("ElapsedTime").asDouble() : null)
            .resBookDesigCode(json.has("ResBookDesigCode") ? json.get("ResBookDesigCode").asText() : null)
            .segmentNumber(json.has("SegmentNumber") ? json.get("SegmentNumber").asText() : null)
            .arrivalDateTime(json.has("ArrivalDateTime") ? json.get("ArrivalDateTime").asText() : null)
            .updatedArrivalTime(json.has("UpdatedArrivalTime") ? json.get("UpdatedArrivalTime").asText() : null)
            .specialMeal(json.has("SpecialMeal") ? json.get("SpecialMeal").asBoolean() : null)
            .updatedDepartureTime(json.has("UpdatedDepartureTime") ? json.get("UpdatedDepartureTime").asText() : null)
            .status(json.has("Status") ? json.get("Status").asText() : null)
            .dayOfWeekInd(json.has("DayOfWeekInd") ? json.get("DayOfWeekInd").asInt() : null)
            .smokingAllowed(json.has("SmokingAllowed") ? json.get("SmokingAllowed").asBoolean() : null)
            .airMilesFlown(json.has("AirMilesFlown") ? json.get("AirMilesFlown").asText() : null)
            .isPast(json.has("IsPast") ? json.get("IsPast").asBoolean() : null)
            .eTicket(json.has("eTicket") ? json.get("eTicket").asBoolean() : null)
            .departureDateTime(json.has("DepartureDateTime") ? json.get("DepartureDateTime").asText() : null)
            .segmentBookedDate(json.has("SegmentBookedDate") ? json.get("SegmentBookedDate").asText() : null)
            .flightNumber(json.has("FlightNumber") ? json.get("FlightNumber").asText() : null)
            .id(json.has("Id") ? json.get("Id").asInt() : null)
            .marketingAirline(json.has("MarketingAirline") ? segmentAirlineTA(json.get("MarketingAirline")) : null)
            .operatingAirline(json.has("OperatingAirline") ? operatingAirline(json.get("OperatingAirline")) : null)
            .operatingAirlinePricing(json.has("OperatingAirlinePricing") ? operatingAirlinePricing(json.get("OperatingAirlinePricing")) : null)
            .originLocation(json.has("OriginLocation") ? originLocation(json.get("OriginLocation")) : null)
            .destinationLocation(json.has("DestinationLocation") ? destinationLocation(json.get("DestinationLocation")) : null)
            .disclosureCarrier(json.has("DisclosureCarrier") ? disclosureCarrier(json.get("DisclosureCarrier")) : null)
            .meal(json.has("Meal") ? meal(json.get("Meal")) : null)
            .supplierRef(json.has("SupplierRef") ? supplierRef(json.get("SupplierRef")) : null)
            .equipment(json.has("FlightNumber") ? equipment(json.get("FlightNumber")) : null)
            .cabin(json.has("Cabin") ? cabin(json.get("Cabin")) : null)
            .build();
    }

    SegmentAirlineTA segmentAirlineTA(JsonNode json) {
        return SegmentAirlineTA.builder()
            .flightNumber(json.has("FlightNumber") ? json.get("FlightNumber").asText() : null)
            .resBookDesigCode(json.has("ResBookDesigCode") ? json.get("ResBookDesigCode").asText() : null)
            .banner(json.has("Banner") ? json.get("Banner").asText() : null)
            .code(json.has("Code") ? json.get("Code").asText() : null)
            .build();
    }

    SegmentAirlineTA operatingAirline(JsonNode json) {
        return SegmentAirlineTA.builder()
            .resBookDesigCode(json.has("ResBookDesigCode") ? json.get("ResBookDesigCode").asText() : null)
            .flightNumber(json.has("FlightNumber") ? json.get("FlightNumber").asText() : null)
            .banner(json.has("Banner") ? json.get("Banner").asText() : null)
            .code(json.has("Code") ? json.get("Code").asText() : null)
            .build();
    }

    SegmentAirlineTA operatingAirlinePricing(JsonNode json) {
        return SegmentAirlineTA.builder()
            .code(json.has("Code") ? json.get("Code").asText() : null)
            .build();
    }

    SegmentLocationTA originLocation(JsonNode json) {
        return SegmentLocationTA.builder()
            .locationCode(json.has("LocationCode") ? json.get("LocationCode").asText() : null)
            .terminal(json.has("Terminal") ? json.get("Terminal").asText() : null)
            .terminalCode(json.has("TerminalCode") ? json.get("TerminalCode").asInt() : null)
            .build();
    }

    SegmentLocationTA destinationLocation(JsonNode json) {
        return SegmentLocationTA.builder()
            .locationCode(json.has("LocationCode") ? json.get("LocationCode").asText() : null)
            .terminal(json.has("Terminal") ? json.get("Terminal").asText() : null)
            .terminalCode(json.has("TerminalCode") ? json.get("TerminalCode").asInt() : null)
            .build();
    }

    ReservationDisclosureCarrierTA disclosureCarrier(JsonNode json) {
        return ReservationDisclosureCarrierTA.builder()
            .dot(json.has("DOT") ? json.get("DOT").asBoolean() : null)
            .banner(json.has("Banner") ? json.get("Banner").asText() : null)
            .code(json.has("Code") ? json.get("Code").asText() : null)
            .build();
    }

    SegmentMealTA meal(JsonNode json) {
        return SegmentMealTA.builder()
            .code(json.has("Code") ? json.get("Code").asText() : null)
            .build();
    }

    SegmentSupplierRefTA supplierRef(JsonNode json) {
        return SegmentSupplierRefTA.builder()
            .id(json.has("ID") ? json.get("ID").asText() : null)
            .build();
    }

    SegmentEquipmentTA equipment(JsonNode json) {
        return SegmentEquipmentTA.builder()
            .airEquipType(json.has("AirEquipType") ? json.get("AirEquipType").asText() : null)
            .build();
    }

    ReservationProductAirCabinTA cabin(JsonNode json) {
        return ReservationProductAirCabinTA.builder()
            .sabreCode(json.has("SabreCode") ? json.get("SabreCode").asText() : null)
            .shortName(json.has("ShortName") ? json.get("ShortName").asText() : null)
            .lang(json.has("Lang") ? json.get("Lang").asText() : null)
            .code(json.has("Code") ? json.get("Code").asText() : null)
            .name(json.has("Name") ? json.get("Name").asText() : null)
            .build();
    }

    ReservationProductTA reservationProductTA(JsonNode json) {
        return ReservationProductTA.builder()
            .productDetail(json.has("ProductDetails") ? reservationProductDetailTA(json.get("ProductDetails")) : null)
            .build();
    }

    ReservationProductDetailTA reservationProductDetailTA(JsonNode json) {
        return ReservationProductDetailTA.builder()
            .productCategory(json.has("productCategory") ? json.get("productCategory").asText() : null)
            .productName(json.has("ProductName") ? productNameTA(json.get("ProductName")) : null)
            .air(json.has("Air") ? productDetailAirTA(json.get("Air")) : null)
            .build();
    }

    ReservationProductNameTA productNameTA(JsonNode json) {
        return ReservationProductNameTA.builder()
            .type(json.has("type") ? json.get("type").asText() : null)
            .build();
    }

    ReservationProductDetailAirTA productDetailAirTA(JsonNode json) {
        return ReservationProductDetailAirTA.builder()
            .funnelFlight(json.has("FunnelFlight") ? json.get("FunnelFlight").asBoolean() : null)
            .marketingClassOfService(json.has("MarketingClassOfService") ? json.get("MarketingClassOfService").asText() : null)
            .mealCode(json.has("MealCode") ? json.get("MealCode").asText() : null)
            .arrivalTerminalName(json.has("ArrivalTerminalName") ? json.get("ArrivalTerminalName").asText() : null)
            .classOfService(json.has("ClassOfService") ? json.get("ClassOfService").asText() : null)
            .marketingFlightNumber(json.has("MarketingFlightNumber") ? json.get("MarketingFlightNumber").asInt() : null)
            .flightNumber(json.has("FlightNumber") ? json.get("FlightNumber").asInt() : null)
            .numberInParty(json.has("NumberInParty") ? json.get("NumberInParty").asInt() : null)
            .airMilesFlown(json.has("AirMilesFlown") ? json.get("AirMilesFlown").asInt() : null)
            .departureAirport(json.has("DepartureAirport") ? json.get("DepartureAirport").asText() : null)
            .marketingAirlineCode(json.has("MarketingAirlineCode") ? json.get("MarketingAirlineCode").asText() : null)
            .segmentBookedDate(json.has("SegmentBookedDate") ? json.get("SegmentBookedDate").asText() : null)
            .arrivalTerminalCode(json.has("ArrivalTerminalCode") ? json.get("ArrivalTerminalCode").asInt() : null)
            .scheduleChangeIndicator(json.has("ScheduleChangeIndicator") ? json.get("ScheduleChangeIndicator").asBoolean() : null)
            .changeOfGauge(json.has("ChangeOfGauge") ? json.get("ChangeOfGauge").asBoolean() : null)
            .outboundConnection(json.has("outboundConnection") ? json.get("outboundConnection").asBoolean() : null)
            .eTicket(json.has("Eticket") ? json.get("Eticket").asBoolean() : null)
            .departureDateTime(json.has("DepartureDateTime") ? json.get("DepartureDateTime").asText() : null)
            .segmentAssociationId(json.has("segmentAssociationId") ? json.get("segmentAssociationId").asInt() : null)
            .sequence(json.has("sequence") ? json.get("sequence").asInt() : null)
            .arrivalDateTime(json.has("ArrivalDateTime") ? json.get("ArrivalDateTime").asText() : null)
            .equipmentType(json.has("EquipmentType") ? json.get("EquipmentType").asText() : null)
            .actionCode(json.has("ActionCode") ? json.get("ActionCode").asText() : null)
            .airlineRefId(json.has("AirlineRefId") ? json.get("AirlineRefId").asText() : null)
            .elapsedTime(json.has("ElapsedTime") ? json.get("ElapsedTime").asInt() : null)
            .arrivalAirport(json.has("ArrivalAirport") ? json.get("ArrivalAirport").asText() : null)
            .inboundConnection(json.has("inboundConnection") ? json.get("inboundConnection").asBoolean() : null)
            .cabin(json.has("Cabin") ? productAirCabinTA(json.get("Cabin")) : null)
            .disclosureCarrier(json.has("DisclosureCarrier") ? disclosureCarrierTA(json.get("DisclosureCarrier")) : null)
            .build();
    }

    ReservationProductAirCabinTA productAirCabinTA(JsonNode json) {
        return ReservationProductAirCabinTA.builder()
            .code(json.has("code") ? json.get("code").asText() : null)
            .name(json.has("name") ? json.get("name").asText() : null)
            .shortName(json.has("shortName") ? json.get("shortName").asText() : null)
            .lang(json.has("lang") ? json.get("lang").asText() : null)
            .sabreCode(json.has("sabreCode") ? json.get("sabreCode").asText() : null)
            .build();
    }

    ReservationDisclosureCarrierTA disclosureCarrierTA(JsonNode json) {
        return ReservationDisclosureCarrierTA.builder()
            .dot(json.has("DOT") ? json.get("DOT").asBoolean() : null)
            .banner(json.has("Banner") ? json.get("Banner").asText() : null)
            .code(json.has("Code") ? json.get("Code").asText() : null)
            .build();
    }

}
