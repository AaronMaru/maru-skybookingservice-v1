package com.skybooking.stakeholderservice.v1_0_0.service.implement.passenger;

import com.skybooking.stakeholderservice.exception.httpstatus.BadRequestException;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.passenger.PassengerEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeHolderUserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.UserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.passenger.PassengerRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.UserRepository;
import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.passenger.PassengerSV;
import com.skybooking.stakeholderservice.v1_0_0.transformer.passenger.PassengerTF;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.passenger.PassengerRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.passenger.PassengerPagingRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.passenger.PassengerRS;
import com.skybooking.stakeholderservice.v1_0_0.util.activitylog.ActivityLoggingBean;
import com.skybooking.stakeholderservice.v1_0_0.util.activitylog.ActivityLoggingBean.Action;
import com.skybooking.stakeholderservice.v1_0_0.util.general.DateValidatorUsingDateFormat;
import com.skybooking.stakeholderservice.v1_0_0.util.general.GeneralBean;
import com.skybooking.stakeholderservice.v1_0_0.util.skyuser.UserBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class PassengerIP implements PassengerSV {

    @Autowired
    GeneralBean generalBean;

    @Autowired
    private PassengerRP repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserBean userBean;

    @Autowired
    private ActivityLoggingBean logger;

    @Autowired
    private HttpServletRequest request;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get list of passengers
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return List<PassengerRS>
     */
    @Override
    public PassengerPagingRS getItems() {

        UserEntity userEntity = userBean.getUserPrincipal();
        StakeHolderUserEntity stakeHolder = userEntity.getStakeHolderUser();

        Integer size = (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) ? Integer.valueOf(request.getParameter("size")) : 10;
        Integer page = (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) ? Integer.valueOf(request.getParameter("page")) - 1 : 0;

        Page<PassengerEntity> passengers = repository.findByStakeHolderUserEntity(stakeHolder, PageRequest.of(page, size));

        List<PassengerRS> PassengerRSS = new ArrayList<>();

        for (PassengerEntity passenger : passengers) {
            PassengerRS passengerRS = new PassengerRS();
            BeanUtils.copyProperties(passenger, passengerRS);
            PassengerRSS.add(passengerRS);
        }

        PassengerPagingRS passengerPagingRS = new PassengerPagingRS();
        passengerPagingRS.setSize(size);
        passengerPagingRS.setPage(page + 1);
        passengerPagingRS.setData(PassengerRSS);
        passengerPagingRS.setTotals(passengers.getTotalElements());

        return passengerPagingRS;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get passenger detail
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id
     * @return PassengerRS
     */
    @Override
    public PassengerRS getItem(Long id) {
        return PassengerTF.getResponse(this.getPassengerById(id));
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * create a new passenger
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param request
     * @return PassengerRS
     */
    @Override
    public PassengerRS createItem(PassengerRQ request) {

        DateValidatorUsingDateFormat cl = new DateValidatorUsingDateFormat();

        if (!cl.valid(request.getBirthDate(), "yyyy-MM-dd") || !cl.valid(request.getExpireDate(), "yyyy-MM-dd")) {
            throw new BadRequestException("Wrong date format", "");
        }

        UserEntity userEntity = userBean.getUserPrincipal();
        StakeHolderUserEntity stakeHolder = userEntity.getStakeHolderUser();
        PassengerEntity passenger = PassengerTF.getEntity(request);

        passenger.setStakeHolderUserEntity(stakeHolder);

        this.logger.activities(Action.CREATE_PASSENGER);

        return PassengerTF.getResponse(repository.save(passenger));

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * update passenger's information
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id
     * @param request
     * @return PassengerRS
     */
    @Override
    public PassengerRS updateItem(Long id, PassengerRQ request) {

        PassengerEntity passenger = this.getPassengerById(id);
        PassengerEntity updatePassenger = PassengerTF.getEntity(request);
        String[] ignoredProperties = this.generalBean.getNullPropertyNames(updatePassenger);
        BeanUtils.copyProperties(updatePassenger, passenger, ignoredProperties);

        this.logger.activities(Action.UPDATE_PASSENGER);

        return PassengerTF.getResponse(repository.save(passenger));

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * delete passenger
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id
     */
    @Override
    public void deleteItem(Long id) {

        PassengerEntity passenger = this.getPassengerById(id);
        UserEntity userEntity = userBean.getUserPrincipal();
        StakeHolderUserEntity stakeHolder = userEntity.getStakeHolderUser();
        stakeHolder.getPassengerEntities().remove(passenger);

        userRepository.save(userEntity);

        this.logger.activities(Action.DELETE_PASSENGER);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * This method will find passenger of stakeholder by passenger's id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id The passenger's id.
     * @return passenger
     */
    private PassengerEntity getPassengerById(Long id) {

        StakeHolderUserEntity stakeHolder = userBean.getUserPrincipal().getStakeHolderUser();
        List<PassengerEntity> passengers = stakeHolder.getPassengerEntities();

        for (PassengerEntity item : passengers) {
            if (item.getId().equals(id)) {
                return item;
            }
        }

        throw new BadRequestException("not_found", null);

    }

}
