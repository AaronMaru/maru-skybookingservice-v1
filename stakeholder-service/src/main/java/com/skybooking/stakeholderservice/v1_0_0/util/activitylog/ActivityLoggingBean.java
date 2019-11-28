package com.skybooking.stakeholderservice.v1_0_0.util.activitylog;


import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.UserEntity;
import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.activity.ActivitySV;
import com.skybooking.stakeholderservice.v1_0_0.util.skyuser.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;

@Component
public class ActivityLoggingBean {

    @Autowired
    ActivitySV service;

    @Autowired
    UserBean userBean;


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

        Long id = this.getSubjectId(subject);
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
    private void activities(String action, String causerType, Long causerId, String subjectType, Long subjectId) {

        final String LOG_SKY_USER = "skyuser";
        final String LOG_SKY_OWNER = "skyowner";
        final String LOG_BOOKING = "booking";


        switch (action) {
            case Action.REGISTER: {
                this.service.logName(LOG_SKY_USER);
                this.service.causedBy(causerId, causerType);
                this.service.log(Action.Messages.get(Action.REGISTER));
                break;
            }
            case Action.REGISTER_SKY_OWNER: {
                this.service.logName(LOG_SKY_OWNER);
                this.service.causedBy(causerId, causerType);
                this.service.performedOn(subjectId, subjectType);
                this.service.log(Action.Messages.get(Action.REGISTER_SKY_OWNER));
                break;
            }
            case Action.LOGIN: {
                this.service.logName(LOG_SKY_USER);
                this.service.causedBy(causerId, causerType);
                this.service.log(Action.Messages.get(Action.LOGIN));
                break;
            }
            case Action.VERIFY_USER_ACTIVE: {
                this.service.logName(LOG_SKY_USER);
                this.service.causedBy(causerId, causerType);
                this.service.log(Action.Messages.get(Action.VERIFY_USER_ACTIVE));
                break;
            }
            case Action.UPDATE_STAKE_USER: {
                this.service.logName(LOG_SKY_USER);
                this.service.causedBy(causerId, causerType);
                this.service.log(Action.Messages.get(Action.UPDATE_STAKE_USER));
                break;
            }
            case Action.CHANGE_PASSWORD: {
                this.service.logName(LOG_SKY_USER);
                this.service.causedBy(causerId, causerType);
                this.service.log(Action.Messages.get(Action.CHANGE_PASSWORD));
                break;
            }
            case Action.RESET_PASSWORD: {
                this.service.logName(LOG_SKY_USER);
                this.service.causedBy(causerId, causerType);
                this.service.log(Action.Messages.get(Action.RESET_PASSWORD));
                break;
            }
            case Action.UPDATE_CONTACT: {
                this.service.logName(LOG_SKY_USER);
                this.service.causedBy(causerId, causerType);
                this.service.log(Action.Messages.get(Action.UPDATE_CONTACT));
                break;
            }
            case Action.CREATE_STAKE_HOLDER_COMPANY: {
                this.service.logName(LOG_SKY_OWNER);
                this.service.causedBy(causerId, causerType);
                this.service.performedOn(subjectId, subjectType);
                this.service.log(Action.Messages.get((Action.CREATE_STAKE_HOLDER_COMPANY)));
                break;
            }
            case Action.LOGOUT: {
                this.service.logName(LOG_SKY_USER);
                this.service.causedBy(causerId, causerType);
                this.service.log(Action.Messages.get(Action.LOGOUT));
                break;
            }
            case Action.LOGIN_SOCIAL_CHECK: {
                this.service.logName(LOG_SKY_USER);
                this.service.causedBy(causerId, causerType);
                this.service.log(Action.Messages.get(Action.LOGIN_SOCIAL_CHECK));
                break;
            }
            case Action.STORE_USER_ACCOUNT_CARD: {
                this.service.logName(LOG_SKY_USER);
                this.service.causedBy(causerId, causerType);
                this.service.log(Action.Messages.get(Action.STORE_USER_ACCOUNT_CARD));
                break;
            }
            case Action.UPDATE_USER_ACCOUNT_CARD: {
                this.service.logName(LOG_SKY_USER);
                this.service.causedBy(causerId, causerType);
                this.service.log(Action.Messages.get(Action.UPDATE_USER_ACCOUNT_CARD));
                break;
            }
            case Action.DELETE_USER_ACCOUNT_CARD: {
                this.service.logName(LOG_SKY_USER);
                this.service.causedBy(causerId, causerType);
                this.service.log(Action.Messages.get(Action.DELETE_USER_ACCOUNT_CARD));
                break;
            }
            case Action.NOTIFICATION_DAY: {
                this.service.logName(LOG_SKY_USER);
                this.service.causedBy(causerId, causerType);
                this.service.log(Action.Messages.get(Action.NOTIFICATION_DAY));
                break;
            }
            case Action.NOTIFICATION_BOOKING: {
                this.service.logName(LOG_SKY_USER);
                this.service.causedBy(causerId, causerType);
                this.service.log(Action.Messages.get(Action.NOTIFICATION_BOOKING));
                break;
            }
            case Action.NOTIFICATION_EMAIL: {
                this.service.logName(LOG_SKY_USER);
                this.service.causedBy(causerId, causerType);
                this.service.log(Action.Messages.get(Action.NOTIFICATION_EMAIL));
                break;
            }
            case Action.CHANGE_CURRENCY: {
                this.service.logName(LOG_SKY_USER);
                this.service.causedBy(causerId, causerType);
                this.service.log(Action.Messages.get(Action.CHANGE_CURRENCY));
                break;
            }
            case Action.USER_CHANGE_REGION: {
                this.service.logName(LOG_SKY_USER);
                this.service.causedBy(causerId, causerType);
                this.service.log(Action.Messages.get(Action.USER_CHANGE_REGION));
                break;
            }
            case Action.CHANGE_LANGUAGE: {
                this.service.logName(LOG_SKY_USER);
                this.service.causedBy(causerId, causerType);
                this.service.log(Action.Messages.get(Action.CHANGE_LANGUAGE));
                break;
            }
            case Action.SEND_BOOKING_INFO: {
                this.service.logName(LOG_SKY_USER);
                this.service.causedBy(causerId, causerType);
                this.service.log(Action.Messages.get(Action.SEND_BOOKING_INFO));
                break;
            }
            case Action.SEND_BOOKING_RECEIPT: {
                this.service.logName(LOG_SKY_USER);
                this.service.causedBy(causerId, causerType);
                this.service.log(Action.Messages.get(Action.SEND_BOOKING_RECEIPT));
                break;
            }
            case Action.CREATE_PASSENGER: {
                this.service.logName(LOG_SKY_USER);
                this.service.causedBy(causerId, causerType);
                this.service.log(Action.Messages.get(Action.CREATE_PASSENGER));
                break;
            }
            case Action.UPDATE_PASSENGER: {
                this.service.logName(LOG_SKY_USER);
                this.service.causedBy(causerId, causerType);
                this.service.log(Action.Messages.get(Action.UPDATE_PASSENGER));
                break;
            }
            case Action.DELETE_PASSENGER: {
                this.service.logName(LOG_SKY_USER);
                this.service.causedBy(causerId, causerType);
                this.service.log(Action.Messages.get(Action.DELETE_PASSENGER));
                break;
            }
            case Action.DEACTIVE_USER: {
                this.service.logName(LOG_SKY_USER);
                this.service.causedBy(causerId, causerType);
                this.service.log(Action.Messages.get(Action.DEACTIVE_USER));
                break;
            }
            case Action.UPDATE_COMPANY_PROFILE: {
                this.service.logName(LOG_SKY_OWNER);
                this.service.causedBy(causerId, causerType);
                this.service.performedOn(subjectId, subjectType);
                this.service.log(Action.Messages.get(Action.UPDATE_COMPANY_PROFILE));
                break;
            }
            case Action.REQUEST_COMPANY_AGAIN: {
                this.service.logName(LOG_SKY_OWNER);
                this.service.causedBy(causerId, causerType);
                this.service.performedOn(subjectId, subjectType);
                this.service.log(Action.Messages.get(Action.REQUEST_COMPANY_AGAIN));
                break;
            }
            case Action.INVITE_SKY_OWNER: {
                this.service.logName(LOG_SKY_OWNER);
                this.service.causedBy(causerId, causerType);
                this.service.performedOn(subjectId, subjectType);
                this.service.log(Action.Messages.get(Action.INVITE_SKY_OWNER));
                break;
            }
            case Action.INVITE_SKY_USER: {
                this.service.logName(LOG_SKY_OWNER);
                this.service.causedBy(causerId, causerType);
                this.service.performedOn(subjectId, subjectType);
                this.service.log(Action.Messages.get(Action.INVITE_SKY_USER));
                break;
            }
            case Action.INVITE_SKY_USER_ACCOUNT: {
                this.service.logName(LOG_SKY_OWNER);
                this.service.causedBy(causerId, causerType);
                this.service.performedOn(subjectId, subjectType);
                this.service.log(Action.Messages.get(Action.INVITE_SKY_USER_ACCOUNT));
                break;
            }
            case Action.NOTIFICATION_DAY_COMPANY: {
                this.service.logName(LOG_SKY_OWNER);
                this.service.causedBy(causerId, causerType);
                this.service.performedOn(subjectId, subjectType);
                this.service.log(Action.Messages.get(Action.NOTIFICATION_DAY_COMPANY));
                break;
            }
            case Action.NOTIFICATION_BOOKING_COMPANY: {
                this.service.logName(LOG_SKY_OWNER);
                this.service.causedBy(causerId, causerType);
                this.service.performedOn(subjectId, subjectType);
                this.service.log(Action.Messages.get(Action.NOTIFICATION_BOOKING_COMPANY));
                break;
            }
            case Action.NOTIFICATION_EMAIL_COMPANY: {
                this.service.logName(LOG_SKY_OWNER);
                this.service.causedBy(causerId, causerType);
                this.service.performedOn(subjectId, subjectType);
                this.service.log(Action.Messages.get(Action.NOTIFICATION_EMAIL_COMPANY));
                break;
            }
            case Action.VERIFY_DEACTIVE_COMPANY: {
                this.service.logName(LOG_SKY_OWNER);
                this.service.causedBy(causerId, causerType);
                this.service.performedOn(subjectId, subjectType);
                this.service.log(Action.Messages.get(Action.VERIFY_DEACTIVE_COMPANY));
                break;
            }
            case Action.VERIFY_ACTIVE_COMPANY: {
                this.service.logName(LOG_SKY_OWNER);
                this.service.causedBy(causerId, causerType);
                this.service.performedOn(subjectId, subjectType);
                this.service.log(Action.Messages.get(Action.VERIFY_ACTIVE_COMPANY));
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
    private Long getSubjectId(Object subject) {

        Field field = null;
        try {

            field = subject.getClass().getDeclaredField("id");
            field.setAccessible(true);

            return (Long) field.get(subject);

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

    public static class Action {

        public static final String CHANGE_CURRENCY = "changeCurrency";
        public static final String CHANGE_LANGUAGE = "changeLanguage";
        public static final String CHANGE_PASSWORD = "changePassword";
        public static final String CREATE_PASSENGER = "createPassenger";
        public static final String CREATE_STAKE_HOLDER_COMPANY = "createStakeholderCompany";
        public static final String DELETE_PASSENGER = "deletePassenger";
        public static final String DEACTIVE_USER = "deactiveAccount";
        public static final String DELETE_USER_ACCOUNT_CARD = "deleteUserAccountCard";
        public static final String INVITE_SKY_OWNER = "inviteSkyOwner";
        public static final String INVITE_SKY_USER = "inviteSkyUser";
        public static final String INVITE_SKY_USER_ACCOUNT = "inviteSkyUserAccount";
        public static final String LOGIN = "login";
        public static final String LOGIN_OR_REGISTER = "loginOrRegister";
        public static final String LOGIN_REGISTER_SKY_OWNER = "loginRegisterSkyowner";
        public static final String LOGIN_SOCIAL_CHECK = "loginSocialCheck";
        public static final String LOGOUT = "logout";
        public static final String NOTIFICATION_BOOKING = "notificationBooking";
        public static final String NOTIFICATION_BOOKING_COMPANY = "notificationBookingCompany";
        public static final String NOTIFICATION_DAY = "notificationDay";
        public static final String NOTIFICATION_DAY_COMPANY = "notificationDayCompany";
        public static final String NOTIFICATION_EMAIL = "notificationEmail";
        public static final String NOTIFICATION_EMAIL_COMPANY = "notificationEmailCompany";
        public static final String POST_CREDENTIALS = "postCredentials";
        public static final String REGISTER = "register";
        public static final String REGISTER_SKY_OWNER = "registerSkyowner";
        public static final String REQUEST_COMPANY_AGAIN = "requestCompanyAgain";
        public static final String RESET_PASSWORD = "resetPassword";
        public static final String SEND_BOOKING_INFO = "sendBookingInfo";
        public static final String SEND_BOOKING_RECEIPT = "sendBookingReceipt";
        public static final String STORE_USER_ACCOUNT_CARD = "storeUserAccountCard";
        public static final String UPDATE_COMPANY_PROFILE = "updateCompanyProfile";
        public static final String UPDATE_PASSENGER = "updatePassenger";
        public static final String UPDATE_CONTACT = "updateStakeHolderPhoneEmail";
        public static final String UPDATE_STAKE_USER = "updateStakeUser";
        public static final String UPDATE_USER_ACCOUNT_CARD = "updateUserAccountCard";
        public static final String USER_CHANGE_REGION = "userChangeRegion";
        public static final String VERIFY_ACTIVE_COMPANY = "verifyActiveCompany";
        public static final String VERIFY_DEACTIVE_COMPANY = "verifyDeactiveCompany";
        public static final String VERIFY_USER_ACTIVE = "verifyUserActive";

        static HashMap<String, String> Messages = new HashMap<>() {{
            put(REGISTER, "registered skyuser");
            put(REGISTER_SKY_OWNER, "registered skyowner");
            put(LOGIN, "signed in");
            put(VERIFY_USER_ACTIVE, "verify active account");
            put(UPDATE_STAKE_USER, "update profile");
            put(RESET_PASSWORD, "reset your password");
            put(UPDATE_CONTACT, "update contact");
            put(CREATE_STAKE_HOLDER_COMPANY, "apply skyowner");
            put(POST_CREDENTIALS, "change your password");
            put(LOGOUT, "logout");
            put(LOGIN_OR_REGISTER, "signed in");
            put(LOGIN_SOCIAL_CHECK, "signed in");
            put(LOGIN_REGISTER_SKY_OWNER, "registered skyowner");
            put(STORE_USER_ACCOUNT_CARD, "add account card");
            put(UPDATE_USER_ACCOUNT_CARD, "update account card");
            put(DELETE_USER_ACCOUNT_CARD, "delete account card");
            put(DEACTIVE_USER, "deactive account");
            put(NOTIFICATION_DAY, "set notification role expired day");
            put(NOTIFICATION_BOOKING, "change notification role booking");
            put(NOTIFICATION_EMAIL, "change notification role email");
            put(CHANGE_CURRENCY, "change currency");
            put(CHANGE_PASSWORD, "change password");
            put(SEND_BOOKING_INFO, "send booking information");
            put(SEND_BOOKING_RECEIPT, "send booking receipt");
            put(CREATE_PASSENGER, "create passenger");
            put(UPDATE_PASSENGER, "update passenger");
            put(DELETE_PASSENGER, "delete passenger");
            put(UPDATE_COMPANY_PROFILE, "update profile");
            put(REQUEST_COMPANY_AGAIN, "company request again");
            put(INVITE_SKY_OWNER, "request for staff");
            put(INVITE_SKY_USER, "invite staff");
            put(INVITE_SKY_USER_ACCOUNT, "invite sky user to create account");
            put(NOTIFICATION_DAY_COMPANY, "set notification role expired day");
            put(NOTIFICATION_BOOKING_COMPANY, "change notification role booking");
            put(NOTIFICATION_EMAIL_COMPANY, "change notification role email");
            put(VERIFY_DEACTIVE_COMPANY, "deactivate company");
            put(VERIFY_ACTIVE_COMPANY, "active company");
        }};

    }


}

