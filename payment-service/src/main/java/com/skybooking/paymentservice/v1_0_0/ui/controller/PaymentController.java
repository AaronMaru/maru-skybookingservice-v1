package com.skybooking.paymentservice.v1_0_0.ui.controller;

import com.skybooking.paymentservice.v1_0_0.client.pipay.model.request.PipayVerifyDataRQ;
import com.skybooking.paymentservice.v1_0_0.client.pipay.model.request.PipayVerifyRQ;
import com.skybooking.paymentservice.v1_0_0.client.pipay.model.response.PipayVerifyRS;
import com.skybooking.paymentservice.v1_0_0.service.interfaces.ProviderSV;
import com.skybooking.paymentservice.v1_0_0.ui.model.request.IPay88RQ;
import com.skybooking.paymentservice.v1_0_0.ui.model.request.PaymentHotelRQ;
import com.skybooking.paymentservice.v1_0_0.ui.model.request.PaymentRQ;
import com.skybooking.paymentservice.v1_0_0.ui.model.response.BaseRS;
import com.skybooking.paymentservice.v1_0_0.ui.model.response.PaymentMethodAvailableRS;
import com.skybooking.paymentservice.v1_0_0.ui.model.response.PaymentMethodRS;
import com.skybooking.paymentservice.v1_0_0.ui.model.response.UrlPaymentRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1.0.0")
public class PaymentController {

    @Autowired
    private ProviderSV providerSV;

    @Autowired
    private WebClient client;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Request payment URL
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param paymentRQ
     * @return
     */
    @PostMapping("/request")
    public ResponseEntity<BaseRS<UrlPaymentRS>> request(@Valid @RequestBody PaymentRQ paymentRQ) {
        return new ResponseEntity<>(
                new BaseRS<>(HttpStatus.OK.value(), "", providerSV.getRequestUrl(paymentRQ)), HttpStatus.OK
        );
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get available payment method
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    @GetMapping("/methods/{bookingCode}")
    public ResponseEntity<BaseRS<List<PaymentMethodRS>>> getPaymentMethods(@PathVariable String bookingCode) {
        return new ResponseEntity<>(
                new BaseRS<>(HttpStatus.OK.value(), "", providerSV.getPaymentMethods(bookingCode)), HttpStatus.OK
        );
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Verify background process with ipay88 between payment service with ipay88 server
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param iPay88RQ
     * @return
     */
    @PostMapping(value = "/ipay88/backend", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String ipay88Backend(@RequestBody IPay88RQ iPay88RQ) {
        System.out.println(iPay88RQ);

        return "RECEIVEOK";
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get available payment method available
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    @GetMapping("methods")
    public ResponseEntity<BaseRS<List<PaymentMethodAvailableRS>>> getPaymentMethodsAvailable() {
        return new ResponseEntity<>(
                new BaseRS<>(HttpStatus.OK.value(), "", providerSV.getPaymentMethodsAvailable()), HttpStatus.OK
        );
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get available payment method available
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    @GetMapping("methods/available/{code}")
    public ResponseEntity<BaseRS<PaymentMethodAvailableRS>> getPaymentMethodsAvailableByCode(@PathVariable String code) {
        return new ResponseEntity<>(
                new BaseRS<>(HttpStatus.OK.value(), "", providerSV.getPaymentMethodsAvailableByCode(code)), HttpStatus.OK
        );
    }

    @GetMapping("maru")
    public void maru() {
        PipayVerifyRQ pipayVerifyRQ = new PipayVerifyRQ(
                new PipayVerifyDataRQ("SBFT01200120", "685415")
        );

        PipayVerifyRS pipayVerifyRS = client.mutate()
                .build()
                .post()
                .uri("https://onlinepayment-uat.pipay.com/rest-api/verifyTransaction")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(pipayVerifyRQ)
                .retrieve()
                .bodyToMono(PipayVerifyRS.class)
                .block();

        System.out.println(pipayVerifyRS);
    }

    @GetMapping(value = "/redirect")
    public ModelAndView method() {
        return new ModelAndView("redirect:" + "http://96.9.69.92:5555/flights/payment-info?bookingCode=SBFT01049420&status=1");
    }
}
