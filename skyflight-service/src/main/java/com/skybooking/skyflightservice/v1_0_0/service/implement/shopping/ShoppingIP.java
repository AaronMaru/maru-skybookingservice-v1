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
import com.skybooking.skyflightservice.v1_0_0.transformer.shopping.FlighShoppingTF;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BCreateRQ;
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
     * @return ShoppingTransformEntity
     */
    @Override
    public FlightShoppingRS shoppingTransformMarkup(FlightShoppingRQ shoppingRQ, String userType, Integer userId) {

        var markupTO = new MarkupTO();

        if (userType.equalsIgnoreCase("anonymous")) {
            markupTO = markupNQ.getMarkupPriceAnonymousUser(shoppingRQ.getClassType().toUpperCase());
        } else if (userType.equalsIgnoreCase("skyuser")) {
            markupTO = markupNQ.getMarkupPriceSkyOwnerUser(userId, shoppingRQ.getClassType().toUpperCase());
        } else if (userType.equalsIgnoreCase("skyowner")) {
            markupTO = markupNQ.getMarkupPriceSkyOwnerUser(userId, shoppingRQ.getClassType().toUpperCase());
        }

        return FlighShoppingTF.getResponse(transformSV.getShoppingTransformDetailWithFilter(transformSV.getShoppingTransformDetailMarkup(this.shoppingTransform(shoppingRQ), markupTO.getMarkup().doubleValue())));

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get shopping transform data
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param shoppingRQ
     * @return ShoppingTransformEntity
     */
    @Override
    public ShoppingTransformEntity shoppingTransform(FlightShoppingRQ shoppingRQ) {

        var query = querySV.flightShoppingExist(shoppingRQ);

        if (query != null) {
            return transformSV.getShoppingTransformById(query.getId());
        }

        var transform = transformSV.getShoppingTransformDetail(transformSV.getShoppingTransform(this.shoppingAsync(shoppingRQ)));

        if (transform == null) {
            querySV.flightShoppingRemove(shoppingRQ);
        }

        return transform;
    }

    @Override
    public Boolean revalidate(BCreateRQ request) {
        return revalidateFlight.revalidate(request);
    }

}
