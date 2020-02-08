package com.skybooking.stakeholderservice.v1_0_0.util.general;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.contact.ContactEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.country.LocationEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.sluggle.SluggleEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeHolderUserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeholderUserStatusEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.UserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.verify.VerifyUserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.contact.ContactRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.country.LocationRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.sluggle.SluggleRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.StakeHolderUserStatusRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.verify.VerifyUserRP;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.*;

public class ApiBean {

    @Autowired
    private SluggleRP sluggleRepository;

    @Autowired
    private ContactRP contactRP;

    @Autowired
    private StakeHolderUserStatusRP userStatusRP;

    @Autowired
    private LocationRP locationRP;

    @Autowired
    private VerifyUserRP verifyUserRP;


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
    public void storeOrUpdateCountry(Long countryCityID, String stake, Long stakeID) {

        LocationEntity location = locationRP.findByLocationableId(stakeID);
        if (location == null) {
            location = new LocationEntity();

            location.setLocationableType(stake);
            location.setLocationableId(stakeID);
        }

        location.setCountryId(countryCityID);

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
    public int createVerifyCode(UserEntity user, int status, String username) {

        int num = generateCode();

        List<VerifyUserEntity> verifyUserList = new ArrayList<>();
        VerifyUserEntity verifyUser = new VerifyUserEntity();

        verifyUser.setToken(Integer.toString(num));
        verifyUser.setStatus(status);
        verifyUserList.add(verifyUser);

        if (user != null) {
            user.setVerifyUserEntity(verifyUserList);
            verifyUser.setUserEntity(user);
        } else {
            verifyUser.setUsername(username);
            verifyUserRP.save(verifyUser);
        }

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
