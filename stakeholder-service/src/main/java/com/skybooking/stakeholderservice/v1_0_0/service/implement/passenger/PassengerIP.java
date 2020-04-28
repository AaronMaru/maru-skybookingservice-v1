package com.skybooking.stakeholderservice.v1_0_0.service.implement.passenger;

import com.skybooking.stakeholderservice.exception.httpstatus.BadRequestException;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.passenger.PassengerEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeHolderUserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.UserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.passenger.PassengerNQ;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.passenger.PassengerTO;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.country.CountryRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.passenger.PassengerRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.UserRepository;
import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.passenger.PassengerSV;
import com.skybooking.stakeholderservice.v1_0_0.transformer.passenger.PassengerTF;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.FilterRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.passenger.PassengerRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.passenger.PassengerPagingRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.passenger.PassengerRS;
import com.skybooking.stakeholderservice.v1_0_0.util.JwtUtils;
import com.skybooking.stakeholderservice.v1_0_0.util.activitylog.ActivityLoggingBean;
import com.skybooking.stakeholderservice.v1_0_0.util.activitylog.ActivityLoggingBean.Action;
import com.skybooking.stakeholderservice.v1_0_0.util.general.GeneralBean;
import com.skybooking.stakeholderservice.v1_0_0.util.header.HeaderBean;
import com.skybooking.stakeholderservice.v1_0_0.util.skyuser.UserBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
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

    @Autowired
    private HeaderBean headerBean;

    @Autowired
    private PassengerNQ passengerNQ;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CountryRP countryRP;

    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get list of passengers
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return List<PassengerRS>
     */
    @Override
    public PassengerPagingRS getItems() {

        Long localeId = headerBean.getLocalizationId();
        FilterRQ filterRQ = new FilterRQ(httpServletRequest, jwtUtils.getUserToken());

        String stake = headerBean.getCompanyId(filterRQ.getCompanyHeaderId());

        Integer size = filterRQ.getSize();
        Integer page = filterRQ.getPage();

        if (request.getParameter("passType") != null) {
            List<String> validatePassType = Arrays.asList("ADT", "CNN", "INF");
            if (!validatePassType.contains(request.getParameter("passType").toUpperCase())) {
                throw new BadRequestException("The param value must be one in (ADT,  INF, CNN).", null);
            }
        }

        String passType = request.getParameter("passType") == null ? ""
                : request.getParameter("passType").toUpperCase();

        Page<PassengerTO> passengers = passengerNQ.getPassenger(filterRQ.getSkyuserId(), filterRQ.getCompanyHeaderId(),
                filterRQ.getRole(), passType, stake, localeId, PageRequest.of(page, size));

        List<PassengerRS> passengerRSList = PassengerTF.getResponseList(passengers);

        PassengerPagingRS passengerPagingRS = new PassengerPagingRS();
        passengerPagingRS.setSize(size);
        passengerPagingRS.setPage(page + 1);
        passengerPagingRS.setData(passengerRSList);
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

        Long localeId = headerBean.getLocalizationId();
        FilterRQ filterRQ = new FilterRQ(httpServletRequest, jwtUtils.getUserToken());

        String stake = headerBean.getCompanyId(filterRQ.getCompanyHeaderId());

        var passengerRS = PassengerTF.getResponse(this.getPassengerById(id));
        userBean.setPasengerType(passengerRS.getBirthDate(), passengerRS);

        PassengerTO passengerTO = passengerNQ.getPassengerById(passengerRS.getId(), filterRQ.getSkyuserId(),
                filterRQ.getCompanyHeaderId(), filterRQ.getRole(), stake, localeId);

        if (passengerTO == null) {
            throw new BadRequestException("not_found", null);
        }

        BeanUtils.copyProperties(passengerTO, passengerRS);
        return passengerRS;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Create a new passenger
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param passengerRQ
     * @return PassengerRS
     */
    @Override
    public PassengerRS createItem(PassengerRQ passengerRQ) {

        Long localeId = headerBean.getLocalizationId();
        FilterRQ filterRQ = new FilterRQ(httpServletRequest, jwtUtils.getUserToken());

        if (repository.existsByIdNumberAndIdType(passengerRQ.getIdNumber(), passengerRQ.getIdType())) {
            throw new BadRequestException("Passenger already exist", null);
        }

        if (countryRP.existsIso(passengerRQ.getNationality()) == null) {
            throw new BadRequestException("Nationality not found", null);
        }

        UserEntity userEntity = userBean.getUserPrincipal();

        PassengerEntity passenger = PassengerTF.getEntity(passengerRQ);

        String stake = headerBean.getCompanyId(filterRQ.getCompanyHeaderId());

        if (stake.equals("company")) {
            passenger.setCompanyId(filterRQ.getCompanyHeaderId());
        }

        passenger.setStakeHolderUserEntity(userEntity.getStakeHolderUser());

        this.logger.activities(Action.CREATE_PASSENGER);
        var passengerRS = getPassengerRS(passenger, filterRQ, stake, localeId);

        return passengerRS;

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * update passenger's information
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id
     * @param passengerRQ
     * @return PassengerRS
     */
    @Override
    public PassengerRS updateItem(Long id, PassengerRQ passengerRQ) {

        Long localeId = headerBean.getLocalizationId();
        FilterRQ filterRQ = new FilterRQ(httpServletRequest, jwtUtils.getUserToken());

        String stake = headerBean.getCompanyId(filterRQ.getCompanyHeaderId());

        PassengerEntity passenger = this.getPassengerById(id);
        if (passengerRQ.getNationality() != null) {
            if (countryRP.existsIso(passengerRQ.getNationality()) == null) {
                throw new BadRequestException("Nationality not found", null);
            }
        }

        PassengerEntity updatePassenger = PassengerTF.getEntity(passengerRQ);
        String[] ignoredProperties = this.generalBean.getNullPropertyNames(updatePassenger);
        BeanUtils.copyProperties(updatePassenger, passenger, ignoredProperties);

        this.logger.activities(Action.UPDATE_PASSENGER);

        var passengerRS = getPassengerRS(passenger, filterRQ, stake, localeId);

        return passengerRS;
    }

    private PassengerRS getPassengerRS(PassengerEntity passenger, FilterRQ filterRQ, String stake, long localeId) {
        var passengerRS = PassengerTF.getResponse(repository.save(passenger));

        PassengerTO passengerTO = passengerNQ.getPassengerById(passengerRS.getId(), filterRQ.getSkyuserId(),
                filterRQ.getCompanyHeaderId(), filterRQ.getRole(), stake, localeId);

        BeanUtils.copyProperties(passengerTO, passengerRS);
        userBean.setPasengerType(passengerRS.getBirthDate(), passengerRS);

        return passengerRS;
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

        FilterRQ filterRQ = new FilterRQ(httpServletRequest, jwtUtils.getUserToken());

        String stake = headerBean.getCompanyId(filterRQ.getCompanyHeaderId());

        var data = repository.findPassenger(id, filterRQ.getSkyuserId(), null);

        if (stake.equals("company"))
            data = repository.findPassenger(id, filterRQ.getSkyuserId(), filterRQ.getCompanyHeaderId());

        if (data != null)
            return data;

        throw new BadRequestException("not_found", null);

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Create passenger for when create PNR
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param passengerRQ
     * @return passenger
     */
    @Override
    public void bookingCreatePassenger(PassengerRQ passengerRQ) {

        FilterRQ filterRQ = new FilterRQ(httpServletRequest, jwtUtils.getUserToken());

        if (!repository.existsByIdNumberAndIdType(passengerRQ.getIdNumber(), passengerRQ.getIdType())) {
            if (countryRP.existsIso(passengerRQ.getNationality()) == null) {
                throw new BadRequestException("Nationality not found", null);
            }

            PassengerEntity passenger = PassengerTF.getEntity(passengerRQ);

            String stake = headerBean.getCompanyId(filterRQ.getCompanyHeaderId());

            if (stake.equals("company")) {
                passenger.setCompanyId(filterRQ.getCompanyHeaderId());
            }

            UserEntity userEntity = userBean.getUserPrincipal();

            passenger.setStakeHolderUserEntity(userEntity.getStakeHolderUser());

            this.logger.activities(Action.CREATE_PASSENGER);
            repository.save(passenger);
        }
    }
}
