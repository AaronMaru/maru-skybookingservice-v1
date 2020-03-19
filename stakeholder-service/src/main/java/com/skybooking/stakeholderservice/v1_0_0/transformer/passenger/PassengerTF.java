package com.skybooking.stakeholderservice.v1_0_0.transformer.passenger;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.passenger.PassengerEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.passenger.PassengerTO;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.passenger.PassengerRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.passenger.PassengerRS;
import com.skybooking.stakeholderservice.v1_0_0.util.skyuser.UserBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class PassengerTF {


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get passenger entity
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param request
     * @return passengerEntity
     */

    public static PassengerEntity getEntity(PassengerRQ request) {

        PassengerEntity passenger = new PassengerEntity();
        BeanUtils.copyProperties(request, passenger);

        return passenger;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get passenger response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param entity
     * @return PassengerRS
     */
    public static PassengerRS getResponse(PassengerEntity entity) {

        PassengerRS response = new PassengerRS();
        BeanUtils.copyProperties(entity, response);

        return response;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get passenger response list
     *
     * @param passengers
     * @return List<PassengerRS>
     */

    public static List<PassengerRS> getResponseList(Page<PassengerTO> passengers) {

        UserBean userBean = new UserBean();
        List<PassengerRS> passengerList = new ArrayList<>();

        for (PassengerTO passenger : passengers) {
            PassengerRS passengerRS = new PassengerRS();
            BeanUtils.copyProperties(passenger, passengerRS);
            userBean.setPasengerType(passenger.getBirthDate().toString(), passengerRS);
            passengerRS.setId(passenger.getId().longValue());
            passengerRS.setBirthDate(passenger.getBirthDate().toString());
            passengerRS.setExpireDate(passenger.getExpireDate().toString());
            passengerRS.setIdType(passenger.getIdType());
            passengerList.add(passengerRS);
        }

        return passengerList;
    }
}
