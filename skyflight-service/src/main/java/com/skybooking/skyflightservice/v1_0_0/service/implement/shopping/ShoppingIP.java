package com.skybooking.skyflightservice.v1_0_0.service.implement.shopping;


import com.skybooking.skyflightservice.v1_0_0.client.distributed.action.ShoppingAction;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.SabreBargainFinderRS;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.ShoppingResponseEntity;
import com.skybooking.skyflightservice.v1_0_0.io.entity.shopping.ShoppingTransformEntity;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.shopping.MarkupNQ;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.shopping.MarkupTO;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.QuerySV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.ResponseSV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.ShoppingSV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.TransformSV;
import com.skybooking.skyflightservice.v1_0_0.service.model.shopping.RevalidateM;
import com.skybooking.skyflightservice.v1_0_0.transformer.shopping.FlighShoppingTF;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BookingCreateRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.shopping.FlightShoppingRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping.FlightShoppingRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingIP implements ShoppingSV {

    @Autowired
    private ShoppingAction shoppingAction;

    @Autowired
    private RevalidateFlight revalidateFlight;

    @Autowired
    private QuerySV querySV;

    @Autowired
    private ResponseSV responseSV;

    @Autowired
    private TransformSV transformSV;

    @Autowired
    private MarkupNQ markupNQ;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get sabre shopping
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param shoppingRQ
     * @return SabreBargainFinderRS
     */
    @Override
    public SabreBargainFinderRS shopping(FlightShoppingRQ shoppingRQ) {
        return shoppingAction.getShopping(shoppingRQ).block();
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get shopping as parallel requests
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param shoppingRQ
     * @return ShoppingResponseEntity
     */
    @Override
    public ShoppingResponseEntity shoppingAsync(FlightShoppingRQ shoppingRQ) {

        var query = querySV.flightShoppingExist(shoppingRQ);

        if (query == null) {
            query = querySV.flightShoppingCreate(shoppingRQ);
        }

        var responses = shoppingAction.getShoppingList(shoppingRQ);

        return responseSV.flightShoppingCreate(query.getId(), responses, query);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get shopping transform data with markup price
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param shoppingRQ
     * @param userType
     * @param userId
     * @param currency
     * @param locale
     * @return ShoppingTransformEntity
     */
    @Override
    public FlightShoppingRS shoppingTransformMarkup(FlightShoppingRQ shoppingRQ, String userType, Integer userId, String currency, long locale) {

        var markupTO = new MarkupTO();

        if (userType.equalsIgnoreCase("anonymous")) {
            markupTO = markupNQ.getMarkupPriceAnonymousUser(shoppingRQ.getClassType().toUpperCase());
        } else if (userType.equalsIgnoreCase("skyuser")) {
            markupTO = markupNQ.getMarkupPriceSkyOwnerUser(userId, shoppingRQ.getClassType().toUpperCase());
        } else if (userType.equalsIgnoreCase("skyowner")) {
            markupTO = markupNQ.getMarkupPriceSkyOwnerUser(userId, shoppingRQ.getClassType().toUpperCase());
        }

        return FlighShoppingTF.getResponse(transformSV.getShoppingTransformDetailWithFilter(transformSV.getShoppingTransformDetailMarkup(this.shoppingTransform(shoppingRQ, locale), markupTO.getMarkup().doubleValue(), currency)));

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get shopping transform data
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param shoppingRQ
     * @param locale
     * @return ShoppingTransformEntity
     */
    @Override
    public ShoppingTransformEntity shoppingTransform(FlightShoppingRQ shoppingRQ, long locale) {

        var query = querySV.flightShoppingExist(shoppingRQ);

        var transform = new ShoppingTransformEntity();

        if (query != null) {
            transform = transformSV.getShoppingTransformDetail(transformSV.getShoppingTransformById(query.getId()), locale);

            if (transform == null) {
                querySV.flightShoppingRemove(shoppingRQ);
            } else {
                return transform;
            }
        }

        transform = transformSV.getShoppingTransformDetail(transformSV.getShoppingTransform(this.shoppingAsync(shoppingRQ)), locale);

        if (transform == null) {
            querySV.flightShoppingRemove(shoppingRQ);
        }

        return transform;
    }


    @Override
    public RevalidateM revalidate(BookingCreateRQ request) {
        return revalidateFlight.revalidate(request);
    }

}
