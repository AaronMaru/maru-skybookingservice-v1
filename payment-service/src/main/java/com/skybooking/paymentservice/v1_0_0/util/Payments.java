package com.skybooking.paymentservice.v1_0_0.util;


import com.skybooking.paymentservice.v1_0_0.io.enitity.PaymentEnitity;
import com.skybooking.paymentservice.v1_0_0.io.repository.PaymentInfoRP;
import com.skybooking.paymentservice.v1_0_0.io.repository.PaymentRP;
import com.skybooking.paymentservice.v1_0_0.ui.model.request.PaymentRQ;
import com.skybooking.paymentservice.v1_0_0.ui.model.response.PaymentInfoRS;
import com.skybooking.paymentservice.v1_0_0.ui.model.response.PaymentRS;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Map;

public class Payments {

    @Autowired
    Environment environment;

    @Autowired
    PaymentRP paymentRP;

    @Autowired
    PaymentInfoRP paymentInfoRP;

    @Autowired
    General general;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * PIPAY Payload input hidden
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param model
     * @return
     */
    public Object pipayPayload(PaymentRS paymentRS, Model model) {

        var MID = environment.getProperty("spring.PIPAY.MID");
        var digest = MID + paymentRS.getBookingCode() + paymentRS.getAmount().toString();

        model.addAttribute("mid", MID);
        model.addAttribute("sid", environment.getProperty("spring.PIPAY.SID"));
        model.addAttribute("did", environment.getProperty("spring.PIPAY.DID"));
        model.addAttribute("var1", environment.getProperty("spring.PIPAY.VAR1"));
        model.addAttribute("lang", environment.getProperty("spring.PIPAY.LANG"));
        model.addAttribute("trType", environment.getProperty("spring.PIPAY.TR_TYPE"));
        model.addAttribute("limitPP", environment.getProperty("spring.PIPAY.LIMIT_PP"));
        model.addAttribute("currency", environment.getProperty("spring.PIPAY.CURRENCY"));
        model.addAttribute("payMethod", environment.getProperty("spring.PIPAY.PAY_METHOD"));
        model.addAttribute("cancelLimer", environment.getProperty("spring.PIPAY.CANCEL_TIMER"));
        model.addAttribute("succesScrDelay", environment.getProperty("spring.PIPAY.SUCCESS_SCR_DELAY"));
        model.addAttribute("startTransaction", environment.getProperty("spring.PIPAY.START_TRANSACTION"));
        model.addAttribute("verifyTransaction", environment.getProperty("spring.PIPAY.VERIFY_TRANSACTION"));
        model.addAttribute("orderId", paymentRS.getBookingCode());
        model.addAttribute("orderDesc", paymentRS.getDescription());
        model.addAttribute("orderAmount", paymentRS.getAmount());
        model.addAttribute("payerPhone", paymentRS.getPlayerPhone());
        model.addAttribute("orderDate", new Date());
        model.addAttribute("confirmURL", environment.getProperty("spring.PIPAY.CONFIRM_URL"));
        model.addAttribute("cancelURL", environment.getProperty("spring.PIPAY.CANCEL_URL"));
        model.addAttribute("description", paymentRS.getDescription());
        model.addAttribute("digest", pipayMD5(digest));

        return model;

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Hashing md5 for pipay signature
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param signature
     * @return
     */
    public static String pipayMD5(String signature) {

        return DigestUtils.md5DigestAsHex(signature.getBytes());
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * IPAY88 Payload input hidden
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param model
     * @return
     */
    public Object ipay88Payload(PaymentRS paymentRS, Model model) {

        model.addAttribute("MerchantKey", environment.getProperty("spring.IPAY88.MERCHANT_KEY"));
        model.addAttribute("MerchantCode", environment.getProperty("spring.IPAY88.MERCHANT_CODE"));
        model.addAttribute("ResponseURL", environment.getProperty("spring.IPAY88.RESPONSE_URL"));
        model.addAttribute("BackendURL", environment.getProperty("spring.IPAY88.BACKEND_URL"));
        model.addAttribute("EntryUrl", environment.getProperty("spring.IPAY88.ENTRY_URL"));
        model.addAttribute("LimitWM", environment.getProperty("spring.IPAY88.LIMIT_WM"));
        model.addAttribute("LimitAB", environment.getProperty("spring.IPAY88.LIMIT_AB"));
        model.addAttribute("LimitAQ", environment.getProperty("spring.IPAY88.LIMIT_AQ"));
        model.addAttribute("LimitEM", environment.getProperty("spring.IPAY88.LIMIT_EM"));
        model.addAttribute("lang", environment.getProperty("spring.IPAY88.LANG"));
        model.addAttribute("PaymentId", paymentRS.getPaymentId());
        model.addAttribute("RefNo", paymentRS.getBookingCode());
        model.addAttribute("Amount", paymentRS.getAmount());
        model.addAttribute("Currency", environment.getProperty("spring.IPAY88.CURRENCY"));
        model.addAttribute("ProdDesc", paymentRS.getDescription());
        model.addAttribute("UserName", paymentRS.getUserName());
        model.addAttribute("UserEmail", paymentRS.getUserEmail());
        model.addAttribute("UserContact", paymentRS.getUserContact());
        model.addAttribute("Remark", paymentRS.getDescription());
        model.addAttribute("Signature", ipay88Signature(paymentRS));

        return model;

    }


    public String ipay88Signature(PaymentRS paymentRS) {
        var amount = paymentRS.getAmount().toString();
        String replaceDot = amount.replace(".", "");
        String replaceComma = replaceDot.replace(",", "");

        var signature = environment.getProperty("spring.IPAY88.MERCHANT_KEY")
                + environment.getProperty("spring.IPAY88.MERCHANT_CODE")
                + paymentRS.getBookingCode()
                + replaceComma
                + environment.getProperty("spring.IPAY88.CURRENCY");

        return general.encrypt(signature);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Add payment information
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param paymentRQ
     * @return
     */
    public PaymentRS addUrlToken(PaymentRQ paymentRQ) {

        PaymentEnitity pay = new PaymentEnitity();

        pay.setToken(paymentRQ.getToken());
        pay.setBookingCode(paymentRQ.getBookingCode());
        pay.setCallbackUrl(paymentRQ.getCallbackUrl());
        pay.setRender(0);
        pay.setPlatform(paymentRQ.getPlatform());
        pay.setPaymentCode(paymentRQ.getPaymentCode());
        pay.setProvider(paymentRQ.getProviderType());

        PaymentRS paymentRS = new PaymentRS();
        BeanUtils.copyProperties(paymentRP.save(pay), paymentRS);

        return paymentRS;

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get data url token
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param bookingCode
     * @return
     */
    public PaymentRS getUrlToken(String bookingCode) {

        PaymentRS paymentRS = new PaymentRS();
        BeanUtils.copyProperties(paymentRP.getUrlToken(bookingCode), paymentRS);
        return paymentRS;

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Update value of Render
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id
     */
    public void updateUrlToken(Long id) {

        PaymentEnitity pay = paymentRP.getOne(id);
        pay.setRender(1);
        paymentRP.save(pay);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Update Payment Status Render
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param request
     * @return
     */
    public PaymentRS upadatePayment(@RequestParam Map<String, String> request) {

        String token = general.tokenDecodeBase64(request.get("token"));
        String bookingCode = general.paymentDecode(token);
        return getUrlToken(bookingCode);

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get payment information for input hidden
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param paymentRS
     * @return
     */
    public PaymentRS getPaymentInfo(PaymentRS paymentRS) {

        var paymentInfo = getPaymentInfo(paymentRS.getPaymentCode());

        paymentRS.setAmount((double) 200.00);
        paymentRS.setDescription("Flight Booking");
        paymentRS.setPaymentId(paymentInfo.getPaymentId());
        paymentRS.setUserName("Mr Sareun");
        paymentRS.setUserEmail("mensareun@skybooking.info");
        paymentRS.setUserContact("098719293");
        paymentRS.setPlayerPhone("085890910");

        return paymentRS;

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get Payment method information
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param paymentCode
     * @return
     */
    public PaymentInfoRS getPaymentInfo(String paymentCode) {

        PaymentInfoRS paymentInfoRS = new PaymentInfoRS();
        BeanUtils.copyProperties(paymentInfoRP.getPaymentInfo(paymentCode), paymentInfoRS);
        return paymentInfoRS;

    }

}