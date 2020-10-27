package com.skybooking.paymentservice.v1_0_0.util.integration;

import com.skybooking.paymentservice.v1_0_0.client.flight.ui.response.FlightMandatoryDataRS;
import com.skybooking.paymentservice.v1_0_0.io.enitity.PaymentEnitity;
import com.skybooking.paymentservice.v1_0_0.io.repository.PaymentInfoRP;
import com.skybooking.paymentservice.v1_0_0.io.repository.PaymentRP;
import com.skybooking.paymentservice.v1_0_0.ui.model.request.PaymentHotelRQ;
import com.skybooking.paymentservice.v1_0_0.ui.model.request.PaymentPointRQ;
import com.skybooking.paymentservice.v1_0_0.ui.model.request.PaymentRQ;
import com.skybooking.paymentservice.v1_0_0.ui.model.response.PaymentInfoRS;
import com.skybooking.paymentservice.v1_0_0.ui.model.response.PaymentRS;
import com.skybooking.paymentservice.v1_0_0.ui.model.response.UrlPaymentRS;
import com.skybooking.paymentservice.v1_0_0.util.generator.GeneralUtility;
import com.skybooking.paymentservice.v1_0_0.util.header.HeaderBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Map;

@Component
public class Payments {

    @Autowired
    private Environment environment;

    @Autowired
    private PaymentRP paymentRP;

    @Autowired
    private PaymentInfoRP paymentInfoRP;

    @Autowired
    private GeneralUtility generalUtility;

    @Autowired
    private HeaderBean headerBean;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get payment url
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param paymentRQ
     * @param path
     * @return
     */
    public UrlPaymentRS getPaymentUrl(PaymentRQ paymentRQ, String path) {

        String language = headerBean.getLocalization();

        paymentRQ.setToken(generalUtility.paymentEncode(paymentRQ));
        var url_token = generalUtility.getBaseUrl() + path + "?lang=" + language + "&token="
                + generalUtility.tokenEncodeBase64(addUrlToken(paymentRQ).getToken());
        UrlPaymentRS urlPaymentRS = new UrlPaymentRS();
        urlPaymentRS.setUrlPayment(url_token);

        return urlPaymentRS;

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get payment url
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param paymentRQ
     * @param path
     * @return
     */
    public UrlPaymentRS getPaymentUrl(PaymentHotelRQ paymentRQ, String path) {

        paymentRQ.setToken(generalUtility.paymentEncodeHotel(paymentRQ));
        var url_token = generalUtility.getBaseUrl() + path + "?token="
                + generalUtility.tokenEncodeBase64(addUrlTokenHotel(paymentRQ).getToken());
        UrlPaymentRS urlPaymentRS = new UrlPaymentRS();
        urlPaymentRS.setUrlPayment(url_token);

        return urlPaymentRS;

    }

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
        model.addAttribute("Lang",
                paymentRS.getLang().equals("zh") ? environment.getProperty("spring.IPAY88.LANG") : "");
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

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Generate ipay88 request signature
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param paymentRS
     * @return
     */
    public String ipay88Signature(PaymentRS paymentRS) {
        var amount = paymentRS.getAmount().toString();
        String replaceDot = amount.replace(".", "");
        String replaceComma = replaceDot.replace(",", "");

        var signature = environment.getProperty("spring.IPAY88.MERCHANT_KEY")
                + environment.getProperty("spring.IPAY88.MERCHANT_CODE") + paymentRS.getBookingCode() + replaceComma
                + environment.getProperty("spring.IPAY88.CURRENCY");

        return generalUtility.encrypt(signature);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Generate ipay88 response signature
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param paymentId
     * @param refNo
     * @param amount
     * @param status
     * @return
     */
    public String ipay88Signature(String paymentId, String refNo, String amount, String status) {

        String replaceDot = amount.replace(".", "");
        String replaceComma = replaceDot.replace(",", "");

        var signature = environment.getProperty("spring.IPAY88.MERCHANT_KEY")
                + environment.getProperty("spring.IPAY88.MERCHANT_CODE") + paymentId + refNo + replaceComma
                + environment.getProperty("spring.IPAY88.CURRENCY") + status;

        return generalUtility.encrypt(signature);
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

        String token = generalUtility.tokenDecodeBase64(request.get("token"));
        String bookingCode = generalUtility.paymentDecode(token);

        PaymentRS paymentRS = getUrlToken(bookingCode);
        paymentRS.setLang(request.get("lang"));

        return paymentRS;

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get payment information for input hidden
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param paymentRS
     * @return
     */
    public PaymentRS getPaymentInfo(PaymentRS paymentRS, FlightMandatoryDataRS mandatoryDataRS) {

        var paymentInfo = getPaymentInfo(paymentRS.getPaymentCode());

        paymentRS.setAmount(mandatoryDataRS.getAmount());
        paymentRS.setDescription(mandatoryDataRS.getDescription());
        paymentRS.setPaymentId(paymentInfo.getPaymentId());
        paymentRS.setUserName(mandatoryDataRS.getName());
        paymentRS.setUserEmail(mandatoryDataRS.getEmail());
        paymentRS.setUserContact(mandatoryDataRS.getPhoneNumber());
        paymentRS.setPlayerPhone(mandatoryDataRS.getPhoneNumber());

        return paymentRS;

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get payment information for input hidden
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param paymentRS
     * @return
     */
    public PaymentRS getPaymentInfoHotel(PaymentRS paymentRS, FlightMandatoryDataRS mandatoryDataRS) {

        var paymentInfo = getPaymentInfo(paymentRS.getPaymentCode());
        paymentRS.setAmount(mandatoryDataRS.getAmount());
        paymentRS.setDescription(mandatoryDataRS.getDescription());
        paymentRS.setPaymentId(paymentInfo.getPaymentId());
        paymentRS.setUserName(mandatoryDataRS.getName());
        paymentRS.setUserEmail(mandatoryDataRS.getEmail());
        paymentRS.setUserContact(mandatoryDataRS.getPhoneNumber());
        paymentRS.setPlayerPhone(mandatoryDataRS.getPhoneNumber());

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

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get payment url
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param paymentRQ
     * @param path
     * @return
     */
    public UrlPaymentRS getPaymentUrlPoint(PaymentPointRQ paymentRQ, String path) {

        String language = headerBean.getLocalization();

        paymentRQ.setToken(generalUtility.paymentEncodePoint(paymentRQ));
        var url_token = generalUtility.getBaseUrl() + path + "?lang=" + language + "&token="
                + generalUtility.tokenEncodeBase64(addUrlTokenPoint(paymentRQ).getToken());
        UrlPaymentRS urlPaymentRS = new UrlPaymentRS();
        urlPaymentRS.setUrlPayment(url_token);

        return urlPaymentRS;

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Add payment information
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param paymentRQ
     * @return
     */
    public PaymentRS addUrlTokenPoint(PaymentPointRQ paymentRQ) {

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
     * Add payment information
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param paymentRQ
     * @return
     */
    public PaymentRS addUrlTokenHotel(PaymentHotelRQ paymentRQ) {

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

}
