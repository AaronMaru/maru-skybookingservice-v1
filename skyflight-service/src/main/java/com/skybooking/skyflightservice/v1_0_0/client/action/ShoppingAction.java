package com.skybooking.skyflightservice.v1_0_0.client.action;


import com.skybooking.skyflightservice.v1_0_0.client.ui.response.bargainfinder.SabreBargainFinderRS;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.shopping.BasicAuthRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.shopping.FlightShoppingRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.BasicAuthRS;
import org.springframework.stereotype.Service;

@Service
public class ShoppingAction {


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get access token
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param basicAuthRQ
     * @return BasicAuthRS
     */
    public BasicAuthRS getAuth(BasicAuthRQ basicAuthRQ) {

        return new BasicAuthRS();
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get bargain finder Shopping
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param request
     * @return SabreBargainFinderRS
     */
    public SabreBargainFinderRS getShopping(FlightShoppingRQ request) {

        try {

            return new SabreBargainFinderRS();

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
