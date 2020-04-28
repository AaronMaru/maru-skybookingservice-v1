package com.skybooking.skyflightservice.v1_0_0.util.activitylog;


import com.skybooking.skyflightservice.v1_0_0.io.entity.user.StakeHolderUserEntity;
import com.skybooking.skyflightservice.v1_0_0.io.entity.user.UserEntity;
import com.skybooking.skyflightservice.v1_0_0.io.repository.users.StakeHolderUserRP;
import com.skybooking.skyflightservice.v1_0_0.io.repository.users.UserRepository;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.activity.ActivitySV;
import com.skybooking.skyflightservice.v1_0_0.util.JwtUtils;
import com.skybooking.skyflightservice.v1_0_0.util.skyuser.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;

@Component
public class ActivityLoggingBean {

    @Autowired
    private ActivitySV service;

    @Autowired
    private UserBean userBean;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRP;

    @Autowired
    private StakeHolderUserRP stakeHolderUserRP;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * this method will log the action of the logged-in user
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param action
     */
    public void activities(String action) {
        activities(action, userBean.getUserPrincipal());
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * this method will log the action of the user
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param action
     * @param causer
     */
    public void activities(String action, UserEntity causer) {
        activities(action, "user", causer.getId(), null, null);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * this method will log the action of the logged-in user and subject
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param action
     * @param subject
     */
    public void activities(String action, Object subject) {
        activities(action, userBean.getUserPrincipal(), subject);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * this method will log the action of the user and subject
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param action
     * @param causer
     * @param subject
     */
    public void activities(String action, UserEntity causer, Object subject) {

        Integer id = this.getSubjectId(subject);
        String entity = this.getSubjectName(subject);

        activities(action, "user", causer.getId(), entity, id);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * this method will log the action of the logged-in user
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param action
     * @param causerType
     * @param causerId
     * @param subjectType
     * @param subjectId
     */
    private void activities(String action, String causerType, Long causerId, String subjectType, Integer subjectId) {

        final String LOG_FLIGHT = "booking";


        switch (action) {

            case Action.INDEX_BOOKINNG: {
                this.service.logName(LOG_FLIGHT);
                this.service.causedBy(causerId, causerType);
                this.service.performedOn(subjectId, subjectType);
                this.service.log(Action.Messages.get(Action.INDEX_BOOKINNG));
                break;
            }
            case Action.INDEX_PAYMNET_METHOD_SELECT: {
                this.service.logName(LOG_FLIGHT);
                this.service.causedBy(causerId, causerType);
                this.service.performedOn(subjectId, subjectType);
                this.service.log(Action.Messages.get(Action.INDEX_PAYMNET_METHOD_SELECT));
                break;
            }
            case Action.INDEX_CREATE_PAYMENT: {
                this.service.logName(LOG_FLIGHT);
                this.service.causedBy(causerId, causerType);
                this.service.performedOn(subjectId, subjectType);
                this.service.log(Action.Messages.get(Action.INDEX_CREATE_PAYMENT));
                break;
            }
            case Action.INDEX_TICKETING_PROCESSING_PAYMENT: {
                this.service.logName(LOG_FLIGHT);
                this.service.causedBy(causerId, causerType);
                this.service.performedOn(subjectId, subjectType);
                this.service.log(Action.Messages.get(Action.INDEX_TICKETING_PROCESSING_PAYMENT));
                break;
            }
            case Action.INDEX_TICKETING_PAYMENT: {
                this.service.logName(LOG_FLIGHT);
                this.service.causedBy(causerId, causerType);
                this.service.performedOn(subjectId, subjectType);
                this.service.log(Action.Messages.get(Action.INDEX_TICKETING_PAYMENT));
                break;
            }
            case Action.INDEX_TICKETING: {
                this.service.logName(LOG_FLIGHT);
                this.service.causedBy(causerId, causerType);
                this.service.performedOn(subjectId, subjectType);
                this.service.log(Action.Messages.get(Action.INDEX_TICKETING));
                break;
            }
            case Action.INDEX_TICKETING_FAIL: {
                this.service.logName(LOG_FLIGHT);
                this.service.causedBy(causerId, causerType);
                this.service.performedOn(subjectId, subjectType);
                this.service.log(Action.Messages.get(Action.INDEX_TICKETING_FAIL));
                break;
            }
            case Action.CANCELLATION_PIPAY: {
                this.service.logName(LOG_FLIGHT);
                this.service.causedBy(causerId, causerType);
                this.service.performedOn(subjectId, subjectType);
                this.service.log(Action.Messages.get(Action.CANCELLATION_PIPAY));
                break;
            }
            case Action.FAIL_PIPAY: {
                this.service.logName(LOG_FLIGHT);
                this.service.causedBy(causerId, causerType);
                this.service.performedOn(subjectId, subjectType);
                this.service.log(Action.Messages.get(Action.FAIL_PIPAY));
                break;
            }
            case Action.CANCELLATION_IPAY88: {
                this.service.logName(LOG_FLIGHT);
                this.service.causedBy(causerId, causerType);
                this.service.performedOn(subjectId, subjectType);
                this.service.log(Action.Messages.get(Action.CANCELLATION_IPAY88));
                break;
            }
            case Action.FAIL_IPAY88: {
                this.service.logName(LOG_FLIGHT);
                this.service.causedBy(causerId, causerType);
                this.service.performedOn(subjectId, subjectType);
                this.service.log(Action.Messages.get(Action.FAIL_IPAY88));
                break;
            }

        }
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * this method will get the id of entity
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param subject
     * @return id
     */
    private Integer getSubjectId(Object subject) {

        Field field = null;
        try {

            field = subject.getClass().getDeclaredField("id");
            field.setAccessible(true);

            return (Integer) field.get(subject);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * this method will get the name of entity by class file name.
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param subject
     * @return entity
     */
    private String getSubjectName(Object subject) {

        String name = subject.getClass().getSimpleName();
        if (name.endsWith("Entity")) {
            name = name.replace("Entity", "");
        }

        return name.toLowerCase();

    }

    public UserEntity getUser() {
        return userRP.findByUsername(jwtUtils.getClaim("user_name", String.class));
    }

    public UserEntity getUser(Integer stakeholderUserId) {

        StakeHolderUserEntity stakeholderUser = stakeHolderUserRP.findById(stakeholderUserId.longValue()).orElse(null);
        return userRP.first(stakeholderUser.getUserEntity().getId());

    }

    public static class Action {

        public static final String INDEX_BOOKINNG = "indexBooking";
        public static final String INDEX_PAYMNET_METHOD_SELECT = "indexPaymentMethodSelected";
        public static final String INDEX_CREATE_PAYMENT = "indexCreatePayment";
        public static final String INDEX_TICKETING_PROCESSING_PAYMENT = "indexTicketingProcessingPayment";
        public static final String INDEX_TICKETING_PAYMENT = "indexTicketingPayment";
        public static final String INDEX_TICKETING = "indexTicketing";
        public static final String INDEX_TICKETING_FAIL = "indexTicketingFail";
        public static final String CANCELLATION_PIPAY = "cancellationPipay";
        public static final String FAIL_PIPAY = "failPipay";
        public static final String CANCELLATION_IPAY88 = "cancellationIpay88";
        public static final String FAIL_IPAY88 = "failIpay88";

        static HashMap<String, String> Messages = new HashMap<>() {{

            put(INDEX_BOOKINNG, "Booking successfully");
            put(INDEX_PAYMNET_METHOD_SELECT, "Payment method selected");
            put(INDEX_CREATE_PAYMENT, "User Create payment");
            put(INDEX_TICKETING_PROCESSING_PAYMENT, "Payment processing");
            put(INDEX_TICKETING_PAYMENT, "User make payment successfully");
            put(INDEX_TICKETING, "Automation issued air ticket successfully");
            put(INDEX_TICKETING_FAIL, "Automation issued air ticket fail");
            put(CANCELLATION_PIPAY, "User cancel payment (PiPay)");
            put(FAIL_PIPAY, "User make payment fail (PiPay)");
            put(CANCELLATION_IPAY88, "User cancel payment (Ipay88)");
            put(FAIL_IPAY88, "User make payment fail (Ipay88)");

        }};

    }


}

