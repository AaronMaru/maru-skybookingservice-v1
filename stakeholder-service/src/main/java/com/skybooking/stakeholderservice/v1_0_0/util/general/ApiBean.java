package com.skybooking.stakeholderservice.v1_0_0.util.general;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.contact.ContactEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.country.LocationEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.sluggle.SluggleEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeHolderUserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeholderUserStatusEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.UserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.verify.VerifyUserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.contact.ContactRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.country.CountryRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.country.LocationRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.sluggle.SluggleRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.StakeHolderUserStatusRP;
import com.skybooking.stakeholderservice.v1_0_0.util.cls.SendingMailThroughAWSSESSMTPServer;
import com.skybooking.stakeholderservice.v1_0_0.util.cls.SmsMessage;
import freemarker.template.Configuration;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

public class ApiBean {

    @Autowired
    Environment environment;

    @Autowired
    private SluggleRP sluggleRepository;

    @Autowired
    private ContactRP contactRP;

    @Autowired
    private StakeHolderUserStatusRP userStatusRP;

    @Autowired
    private Configuration configuration;

    @Autowired
    private CountryRP countryRP;

    @Autowired
    private LocationRP locationRP;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Generate unique name for file
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return String
     */
    public String generateFileName(String ext) {

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String name = UUID.randomUUID().toString().replace("-", "").substring(0, 10) + timeStamp;

        if (ext != null) {
            name = name + "." + ext;
        }

        return name;

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Create contacts user
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param type
     * @Param stkUser
     */
    public void addContact(String username, String code, StakeHolderUserEntity skyuser, String type) {

        List<ContactEntity> contacts = new ArrayList<>();
        ContactEntity contactEntity = new ContactEntity();

        String phoneEmail = username;

        if (NumberUtils.isNumber(username)) {
            contactEntity.setType("p");
            phoneEmail = code + "-" + username.replaceFirst("^0+(?!$)", "");
        }
        if (EmailValidator.getInstance().isValid(username)) {
            contactEntity.setType("e");
        }
        if (type != null) {
            contactEntity.setType("a");
        }
        contactEntity.setValue(phoneEmail);

        contacts.add(contactEntity);

        for (ContactEntity contact : contacts) {
            contact.setStakeHolderUserEntity(skyuser);
        }

        skyuser.setContactEntities(contacts);

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Update contacts user
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param username
     * @Param code
     * @Param stkHolder
     */
    public void updateContact(String username, String code, StakeHolderUserEntity skyuser, String type, String address) {

        Boolean b = false;

        String phoneEmail = username;
        if (NumberUtils.isNumber(username)) {
            phoneEmail = code + "-" + username.replaceFirst("^0+(?!$)", "");
        }

        for (ContactEntity contact : skyuser.getContactEntities()) {
            if (contact.getType().equals(type)) {
                contact.setValue(phoneEmail);
                b = true;
            }
        }
        if (!b) {
            addContact(username, code, skyuser, address);
        }

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Create simple contact
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param id
     * @Param value
     * @Param type
     */
    public void addSimpleContact(Long id, String value, String type, String skyType) {
        contactRP.createContact(id, value, type, skyType);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send email and sms
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param reciever
     * @Param message
     */
    public Boolean sendEmailSMS(String receiver, String message, Map<String, Object> mailTemplateData) {

        SmsMessage sms = new SmsMessage();

        boolean validEmail = EmailValidator.getInstance().isValid(receiver);
        if (NumberUtils.isNumber(receiver.replaceAll("[+]", ""))) {
            sms(receiver, sms.sendSMS(message, Integer.parseInt(mailTemplateData.get("code").toString())));
            return true;
        } else if (validEmail) {
            email(receiver, mailTemplateData);
            return true;
        }
        return false;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Email
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param TO
     * @Param MESSAGE
     */
    public void email(String TO, Map<String, Object> mailTemplateData) {

        Map<String, String> mailProperty = new HashMap<>();
        mailProperty.put("SMTP_SERVER_HOST", environment.getProperty("spring.email.host"));
        mailProperty.put("SMTP_SERVER_PORT", environment.getProperty("spring.email.port"));
        mailProperty.put("SUBJECT", "Skybooking");
        mailProperty.put("SMTP_USER_NAME", environment.getProperty("spring.email.username"));
        mailProperty.put("SMTP_USER_PASSWORD", environment.getProperty("spring.email.password"));
        mailProperty.put("FROM_USER_EMAIL", environment.getProperty("spring.email.from-address"));
        mailProperty.put("FROM_USER_FULLNAME", environment.getProperty("spring.email.from-name"));
        mailProperty.put("TO", TO);

        mailTemplateData.put("mailUrl", environment.getProperty("spring.awsImageUrl.mailTemplate"));

        SendingMailThroughAWSSESSMTPServer sendingMailThroughAWSSESSMTPServer = new SendingMailThroughAWSSESSMTPServer();
        sendingMailThroughAWSSESSMTPServer.sendMail(configuration, mailProperty, mailTemplateData);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Sms
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param TO
     * @Param MESSAGE
     */
    public void sms(String TO, String MESSAGE) {

        RestTemplate restAPi = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("username", environment.getProperty("spring.sms.username"));
        map.add("pass", environment.getProperty("spring.sms.pass"));
        map.add("sender", environment.getProperty("spring.sms.sender"));
        map.add("cd", environment.getProperty("spring.sms.cd"));
        map.add("smstext", MESSAGE);
        map.add("isflash", environment.getProperty("spring.sms.isflash"));
        map.add("gsm", TO);
        map.add("int", environment.getProperty("spring.sms.int"));

        HttpEntity<MultiValueMap<String, String>> requestSMS = new HttpEntity<>(map, headers);
        restAPi.exchange(environment.getProperty("spring.sms.url"), HttpMethod.POST, requestSMS, String.class)
                .getBody();

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Generate code sluggle for user
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param key
     */
    public String createSlug(String key) {

        int num = generateCode();
        SluggleEntity sluggle = sluggleRepository.findByKey(key);

        String sluggleGenerate = sluggle.getPrefix() + num;

        return sluggleGenerate;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Store user status
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param user
     * @Param status
     * @Param comment
     */
    public void storeUserStatus(UserEntity user, Integer status, String comment) {

        StakeholderUserStatusEntity skyuserStatus = new StakeholderUserStatusEntity();

        skyuserStatus.setActionableId(user.getId());
        skyuserStatus.setSkyuserId(user.getStakeHolderUser().getId());
        skyuserStatus.setStatus(status);
        skyuserStatus.setComment(comment);

        userStatusRP.save(skyuserStatus);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Store contacts
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param skyownerRQ
     * @Param id
     */
    public void storeOrUpdateCountry(Long countryID, String stake, Long stakeID) {

        LocationEntity location = locationRP.findByLocationableId(stakeID);
        System.out.println(location);
        if (location == null) {
            location = new LocationEntity();

            location.setLocationableType(stake);
            location.setLocationableId(stakeID);
        }

        location.setCountryId(countryID);

        locationRP.save(location);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Create login code
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param userEntity
     * @Param status
     */
    public int createVerifyCode(UserEntity user, int status) {

        int num = generateCode();

        List<VerifyUserEntity> verifyUserList = new ArrayList<>();
        VerifyUserEntity verifyUser = new VerifyUserEntity();

        verifyUser.setToken(Integer.toString(num));
        verifyUser.setStatus(status);
        verifyUserList.add(verifyUser);

        user.setVerifyUserEntity(verifyUserList);
        verifyUser.setUserEntity(user);

        return num;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Generate 6 gidit code
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return int
     */
    public int generateCode() {

        Random generator = new Random();
        generator.setSeed(System.currentTimeMillis());

        int num = generator.nextInt(999999);

        if (num < 100000 || num > 999999) {
            num = generator.nextInt(99999) + 99999;
            if (num < 100000 || num > 999999) {
                return 153679;
            }
        }

        return num;

    }

}
