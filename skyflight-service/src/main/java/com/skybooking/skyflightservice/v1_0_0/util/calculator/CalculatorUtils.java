package com.skybooking.skyflightservice.v1_0_0.util.calculator;

import com.fasterxml.jackson.databind.JsonNode;
import com.skybooking.skyflightservice.v1_0_0.util.classes.markup.ItemMarkup;

import java.math.BigDecimal;

public class CalculatorUtils {

    public static BigDecimal getAmountPercentage(BigDecimal amount, BigDecimal percentage) {
        return NumberFormatter.trimAmount(amount.multiply(percentage));
    }

    public static ItemMarkup getBookingMarkup(JsonNode airItineraryPricingInfo, BigDecimal markup, BigDecimal generalMarkupPaymentPercentage) {

        var markupAmount = BigDecimal.ZERO;
        var paymentMethodMarkupAmount = BigDecimal.ZERO;

        for (JsonNode item : airItineraryPricingInfo) {

            var passengerQuantity = new BigDecimal(item.get("PassengerTypeQuantity").get("Quantity").textValue());
            var totalTax = NumberFormatter.trimAmount(new BigDecimal(item.get("ItinTotalFare").get("Taxes").get("TotalAmount").textValue()));
            var baseFare = NumberFormatter.trimAmount(new BigDecimal(item.get("ItinTotalFare").get("BaseFare").get("Amount").textValue()));

            if (item.get("ItinTotalFare").has("EquivFare")) {
                baseFare = NumberFormatter.trimAmount(new BigDecimal(item.get("ItinTotalFare").get("EquivFare").get("Amount").textValue()));
            }

            var totalTaxMarkup = NumberFormatter.trimAmount(getAmountPercentage(totalTax, markup));
            var baseFareMarkup = NumberFormatter.trimAmount(getAmountPercentage(baseFare, markup));

            markupAmount = markupAmount.add(totalTaxMarkup.add(baseFareMarkup).multiply(passengerQuantity));

            var totalTaxMarkupPaymentPercentage = baseFare.add(baseFareMarkup).multiply(generalMarkupPaymentPercentage).multiply(passengerQuantity);
            var baseFareMarkupPaymentPercentage = totalTax.add(totalTaxMarkup).multiply(generalMarkupPaymentPercentage).multiply(passengerQuantity);

            paymentMethodMarkupAmount = paymentMethodMarkupAmount.add(totalTaxMarkupPaymentPercentage.add(baseFareMarkupPaymentPercentage));
        }

        return new ItemMarkup(markupAmount, paymentMethodMarkupAmount);
    }
}
