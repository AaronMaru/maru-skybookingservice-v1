package com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.payment;

import com.skybooking.library.NativeQuery;
import com.skybooking.library.NativeQueryFolder;
import org.springframework.stereotype.Component;

import java.util.List;

@NativeQueryFolder("payment")
@Component
public interface PaymentNQ extends NativeQuery {

    List<PaymentMethodTO> getListPaymentMethods();

}
