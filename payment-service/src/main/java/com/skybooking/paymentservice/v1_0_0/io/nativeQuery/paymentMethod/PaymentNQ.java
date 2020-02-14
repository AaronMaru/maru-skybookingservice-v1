package com.skybooking.paymentservice.v1_0_0.io.nativeQuery.paymentMethod;

import com.skybooking.library.NativeQuery;
import com.skybooking.library.NativeQueryFolder;
import com.skybooking.library.NativeQueryParam;
import org.springframework.stereotype.Component;

import java.util.List;

@NativeQueryFolder("paymentMethod")
@Component
public interface PaymentNQ extends NativeQuery {

    PaymentMethodTO getPaymentMethod(@NativeQueryParam("code") String code);

    List<PaymentMethodTO> getListPaymentMethods();

}
