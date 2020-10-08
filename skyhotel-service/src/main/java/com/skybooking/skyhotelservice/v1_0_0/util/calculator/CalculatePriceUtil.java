package com.skybooking.skyhotelservice.v1_0_0.util.calculator;

import com.skybooking.skyhotelservice.constant.CurrencyConstant;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.room.FeeRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.room.RateRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.booking.RateTaxesHBRS;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.markup.HotelMarkupEntity;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.booking.checkrate.CancellationPolicy;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.booking.checkrate.Discount;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.booking.checkrate.Tax;
import com.skybooking.skyhotelservice.v1_0_0.util.calculator.CalculatePriceRS.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CalculatePriceUtil {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get markup price detail
     * -----------------------------------------------------------------------------------------------------------------
     *
     *
     * @param markupList
     * @param amount
     * @param rRoom
     * @param nights
     * @return priceDetail
     */
    public static MarkupPriceDetail markupPricesDetail(List<HotelMarkupEntity> markupList, BigDecimal amount, BigDecimal rRoom, BigDecimal nights) {

        MarkupPriceDetail priceDetail = new MarkupPriceDetail();
        var markup = markupList.stream()
            .filter(markupData -> (
                amount.compareTo(BigDecimal.valueOf(500)) > 0) ? markupData.getFrom() >= 500 : BigDecimal.valueOf(markupData.getFrom()).compareTo(amount) < 0 && (amount.compareTo(BigDecimal.valueOf(markupData.getTo())) < 0 || amount.compareTo(BigDecimal.valueOf(markupData.getTo())) == 0))
            .findFirst();

        BigDecimal percentage = BigDecimal.ZERO;

        if (markup.isPresent()) {
            percentage = markup.get().getPercentage();
        }
        priceDetail.setAmount(NumberFormatter.trimAmount(amount.multiply(percentage).add(amount)));
        priceDetail.setMarkupAmount(amount.multiply(percentage));
        priceDetail.setTotalMarkupAmount(priceDetail.getMarkupAmount().multiply(rRoom).multiply(nights));
        priceDetail.setMarkupPercentage(percentage);

        return priceDetail;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get only exclude tax before markup
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param taxesHBRS
     * @return BigDecimal
     */
    public static BigDecimal excludeTaxBeforeMarkup(RateTaxesHBRS taxesHBRS)
    {
        var ref = new Object() {
            BigDecimal excludeTax = BigDecimal.ZERO;
        };
        if(taxesHBRS != null && taxesHBRS.getTaxes() != null){
            if (!taxesHBRS.getTaxes().isEmpty()){
                taxesHBRS.getTaxes().forEach(dataTax ->{
                    if(!dataTax.isIncluded()){
                        ref.excludeTax = ref.excludeTax.add(dataTax.getAmount());
                    }
                });
            }
        }

        return ref.excludeTax;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get detail net price (unitNet, totalNet)
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param net
     * @param excludeTax
     * @param rRoom => room that request
     * @param nights
     * @return netDetail
     */
    public static NetPriceDetail detailNet(BigDecimal net, BigDecimal excludeTax, BigDecimal rRoom, BigDecimal nights)
    {
        NetPriceDetail netDetail = new NetPriceDetail();
        BigDecimal unitNet = net.add(excludeTax).divide(nights, 2, RoundingMode.HALF_UP);
        BigDecimal totalNet = unitNet.multiply(rRoom).multiply(nights);
        netDetail.setTotalNet(totalNet);
        netDetail.setUnitNet(unitNet);

        return netDetail;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get markup payment (markupPaymentPercentage, markupPaymentAmount)
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param totalAmount
     * @return markupPayment
     */
    public static MarkupPaymentDetail markupPayment(BigDecimal totalAmount)
    {
        MarkupPaymentDetail markupPayment = new MarkupPaymentDetail();
        BigDecimal markupPercentage = BigDecimal.valueOf(5).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        BigDecimal markupAmount = totalAmount.multiply(markupPercentage);
        markupPayment.setMarkupPercentage(markupPercentage);
        markupPayment.setMarkupAmount(markupAmount);

        return markupPayment;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get unit amount
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param unitNet
     * @param markupAmount
     * @param unitTax
     * @param unitDiscountAmount
     * @return BigDecimal
     */
    public static BigDecimal unitAmount(BigDecimal unitNet, BigDecimal markupAmount, BigDecimal unitTax, BigDecimal unitDiscountAmount)
    {
        BigDecimal unitAmount = unitNet.add(markupAmount);
        unitAmount = unitAmount
            .add(CalculatePriceUtil.markupPayment(unitAmount).getMarkupAmount()).subtract(unitTax).add(unitDiscountAmount);

        return  unitAmount;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get detail total price (subTotalAmount, totalAmount)
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param unitAmount
     * @param discount
     * @param tax => total tax
     * @param rRoom => total room that request
     * @param nights
     * @return totalPrice
     */
    public static TotalPriceDetail detailTotalPrice(BigDecimal unitAmount, BigDecimal discount, BigDecimal tax, BigDecimal rRoom, BigDecimal nights)
    {
        // note unitAmount include discount already
        TotalPriceDetail totalPrice = new TotalPriceDetail();

        BigDecimal amountIncludeDiscount = unitAmount.multiply(rRoom).multiply(nights);
        BigDecimal amountAfterDiscount = amountIncludeDiscount.subtract(discount).add(tax);
        totalPrice.setAmountIncludeDiscount(amountIncludeDiscount);
        totalPrice.setAmountAfterDiscount(amountAfterDiscount);

        return totalPrice;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get tax detail (tax, unitTax, List<Tax> taxes
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param taxesRS
     * @param rRoom => total room that request
     * @param nights
     * @return TaxDetail
     */
    public static TaxDetail taxDetail(List<Tax> taxesRS, Integer rRoom, Long nights)
    {
        // declare variable
        TaxDetail taxDetail = new TaxDetail();

        if (taxesRS != null && !taxesRS.isEmpty()){
            List<Tax> taxes = taxesRS
                .stream()
                .map(taxRS -> {
                    taxDetail.setTax(taxDetail.getTax()
                        .add(taxRS.getAmount().multiply(BigDecimal.valueOf(rRoom)).multiply(BigDecimal.valueOf(nights))));

                    taxDetail.setUnitTax(taxDetail.getUnitTax().add(taxRS.getAmount()));

                    return new Tax(
                        taxRS.getType(),
                        taxRS.getCurrency(),
                        taxRS.getAmount().multiply(BigDecimal.valueOf(rRoom)).multiply(BigDecimal.valueOf(nights))
                    );
                })
                .collect(Collectors.toList());
                taxDetail.setTaxes(taxes);
        }

        return taxDetail;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get unit tax breakdown
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param taxesHBRS
     * @param markup
     * @param sRoom => total room that response
     * @param nights
     * @return list taxes
     */
    public static List<Tax> breakdownTaxUnit(RateTaxesHBRS taxesHBRS, MarkupPriceDetail markup, Integer sRoom, Long nights)
    {
        List<Tax> taxes = new ArrayList();
        // loop tax
        if (taxesHBRS != null && !taxesHBRS.getTaxes().isEmpty()) {
            taxesHBRS.getTaxes().forEach(dataTax -> {
                Tax tax = new Tax();
                // get breakdown unit tax
                BigDecimal bUnitTax = dataTax.getAmount()
                    .divide(BigDecimal.valueOf(sRoom).multiply(BigDecimal.valueOf(nights)), 2, RoundingMode.HALF_UP);
                // get markup tax
                BigDecimal taxMarkup = bUnitTax
                    .multiply(markup.getMarkupPercentage()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                // get total tax in breakdown
                BigDecimal bTax = bUnitTax.add(taxMarkup);

                bTax = bTax.add(CalculatePriceUtil.markupPayment(bTax).getMarkupAmount());
                // set value to taxRS
                tax.setCurrency(CurrencyConstant.USD.CODE);
                tax.setType(dataTax.getType());
                tax.setAmount(bTax);
                taxes.add(tax);
            });
        }

        return taxes;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get unit discount breakdown
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param rate
     * @param nights
     * @return list Discount
     */
    public static List<Discount> breakdownDiscountUnit(RateRSDS rate, BigDecimal nights)
    {
        List<Discount> discounts = new ArrayList<>();

        if (rate.getRateBreakDown() != null && rate.getRateBreakDown().getRateDiscounts() != null)
            discounts = rate.getRateBreakDown().getRateDiscounts()
                .stream()
                .map(rateDiscountRSDS -> {
                    return new Discount(
                        rateDiscountRSDS.getName(),
                        rateDiscountRSDS.getCode(),
                        rateDiscountRSDS.getAmount().abs().divide(nights, 2, RoundingMode.HALF_UP),
                        rateDiscountRSDS.getCurrency()
                    );

                })
                .collect(Collectors.toList());

        if (rate.getOffers() != null)
            discounts.addAll(
                rate.getOffers().stream()
                    .map(rateOfferRSDS -> {
                        return new Discount(
                            rateOfferRSDS.getName(),
                            rateOfferRSDS.getCode(),
                            rateOfferRSDS.getAmount().abs().divide(nights, 2, RoundingMode.HALF_UP),
                            rateOfferRSDS.getCurrencyCode()
                        );
                    })
                    .collect(Collectors.toList())
            );

        return discounts;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get discount detail (discount, unitDiscount, List<Discount> discounts
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param discountRSDS
     * @param rRoom => total room that request
     * @param nights
     * @return DiscountDetail
     */
    public static DiscountDetail discountDetail(List<Discount> discountRSDS, BigDecimal rRoom, BigDecimal nights)
    {
        DiscountDetail discountDetail = new DiscountDetail();

        if (discountRSDS != null && !discountRSDS.isEmpty()){
            List<Discount> discounts = discountRSDS
                .stream()
                .map(disRSDS -> {
                    discountDetail.setDiscount(discountDetail.getDiscount().add(disRSDS.getAmount().multiply(rRoom).multiply(nights)));

                    discountDetail.setUnitDiscount(discountDetail.getUnitDiscount()
                        .add(disRSDS.getAmount()));

                    return new Discount(
                        disRSDS.getName(),
                        disRSDS.getCode(),
                        disRSDS.getAmount().multiply(rRoom).multiply(nights),
                        disRSDS.getCurrencyCode()
                    );
                })
                .collect(Collectors.toList());
            discountDetail.setDiscounts(discounts);
        }
        return discountDetail;
    }

    public static List<CancellationPolicy> breakdownCancellationPolicy(List<FeeRSDS> cancelPolicyRSDS, BigDecimal markupPercentage, BigDecimal nights)
    {
        List<CancellationPolicy> cancellationPolicyRS =  new ArrayList<>();

        if(!cancelPolicyRSDS.isEmpty()){
            cancellationPolicyRS = cancelPolicyRSDS
                .stream()
                .map(cancelPolicyRS -> {
                    CancellationPolicy cancelPolicy = new CancellationPolicy();
                    BigDecimal amountIncludeMarkup = cancelPolicyRS.getAmount()
                        .add(cancelPolicyRS.getAmount().multiply(markupPercentage));

                    BigDecimal finalAmount = amountIncludeMarkup.add(CalculatePriceUtil.markupPayment(amountIncludeMarkup).getMarkupAmount())
                        .divide(nights, 2, RoundingMode.HALF_UP);

                    cancelPolicy.setNet(cancelPolicyRS.getAmount().divide(nights, 2, RoundingMode.HALF_UP));
                    cancelPolicy.setAmount(finalAmount);
                    cancelPolicy.setFrom(cancelPolicyRS.getFrom());
                    cancelPolicy.setCurrency(cancelPolicyRS.getCurrency());

                    return cancelPolicy;
                })
                .collect(Collectors.toList());
        }

        return  cancellationPolicyRS;
    }

    public static CancellationPolicyDetail cancellationPolicyDetail(List<CancellationPolicy> cancellationPolicies, BigDecimal rRoom, BigDecimal nights)
    {
        CancellationPolicyDetail cancellationPolicyDetail = new CancellationPolicyDetail();

        if (!cancellationPolicies.isEmpty()){
           List<CancellationPolicy> cancellationData = cancellationPolicies.stream()
                .map(cancellationPolicy -> {
                    cancellationPolicyDetail.setTotalAmount(cancellationPolicyDetail
                        .getTotalAmount()
                        .add(cancellationPolicy.getAmount()).multiply(rRoom).multiply(nights));

                    CancellationPolicy policy = new CancellationPolicy();
                    policy.setNet(cancellationPolicy.getNet().multiply(rRoom).multiply(nights));
                    policy.setAmount(cancellationPolicy.getAmount().multiply(rRoom).multiply(nights));
                    policy.setFrom(cancellationPolicy.getFrom());
                    policy.setCurrency(cancellationPolicy.getCurrency());

                    return policy;
                })
                .collect(Collectors.toList());
            cancellationPolicyDetail.setCancellationPolicies(cancellationData);
        }

        return cancellationPolicyDetail;
    }

}
