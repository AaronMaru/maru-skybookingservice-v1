package com.skybooking.paymentservice.v1_0_0.util.generator;

import com.skybooking.paymentservice.v1_0_0.util.classse.CardInfo;

public class CardUtility {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get card information
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param number
     * @return
     */
    public static CardInfo getCardInfo(String number) {

        CardInfo card = new CardInfo();

        if (number.length() < 4) {
            return card;
        }

        var numberLength = number.length();
        var firstDigit = number.substring(0, 1);
        var secondDigit = number.substring(1, 2);
        var last4Digit = number.substring(numberLength - 4);

        switch (firstDigit) {
            case "3":
                if (secondDigit.equals("7")) {
                    card.setType("AmericanExpress");
                    card.setNumber("XXXX XXXXXX X" + last4Digit);
                } else if (secondDigit.equals("0") || secondDigit.equals("8")) {
                    card.setType("DinnerClub");
                    card.setNumber("XXXX XXXX XX" + last4Digit.substring(0, 2) + " " + last4Digit.substring(2));
                } else {
                    card.setType("JCB");
                    card.setNumber("XXXX XXXX XXXX " + last4Digit);
                }
                break;

            case "4":
                card.setType("VISA");
                card.setNumber("XXXX XXXX XXXX " + last4Digit);
                break;

            case "5":
                card.setType("MasterCard");
                card.setNumber("XXXX XXXX XXXX " + last4Digit);
                break;

            case "6":
                if (secondDigit.equals("2")) {
                    card.setType("UnionPay");
                } else {
                    card.setType("Discover");
                }
                card.setNumber("XXXX XXXX XXXX " + last4Digit);
                break;
        }

        return card;

    }
}
