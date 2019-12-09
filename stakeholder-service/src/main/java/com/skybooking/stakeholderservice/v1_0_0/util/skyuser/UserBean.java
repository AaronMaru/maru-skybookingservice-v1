package com.skybooking.stakeholderservice.v1_0_0.util.skyuser;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.skybooking.stakeholderservice.exception.httpstatus.InternalServerError;
import com.skybooking.stakeholderservice.exception.httpstatus.UnauthorizedException;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.company.StakeholderCompanyDocsEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.company.StakeholderCompanyEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.contact.ContactEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.notification.UserPlayerEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.UserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.user.PermissionTO;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.user.UserNQ;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.user.UserProfileTO;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.company.CompanyHasUserRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.contact.ContactRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.notification.UserPlayerRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.UserRepository;
import com.skybooking.stakeholderservice.v1_0_0.transformer.TokenTF;
import com.skybooking.stakeholderservice.v1_0_0.transformer.UserDetailsTF;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.company.CompanyRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.PermissionRS;
import com.skybooking.stakeholderservice.v1_0_0.util.general.ApiBean;
import com.skybooking.stakeholderservice.v1_0_0.util.general.GeneralBean;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserBean {

    @Autowired
    private GeneralBean generalBean;

    @Autowired
    private ApiBean apiBean;

    @Autowired
    Environment environment;

    @Autowired
    private AmazonS3 s3client;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ContactRP contactRP;

    @Autowired
    private UserNQ userNQ;

    @Autowired
    private CompanyHasUserRP companyHasUserRP;

    @Autowired
    private UserPlayerRP userPlayerRP;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get user principal
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return UserEntity
     */
    public UserEntity getUserPrincipal() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

        UserEntity user = userRepository.findByUsername(authentication.getName());

        if (user == null) {
            throw new UnauthorizedException("sth_w_w", "");
        }

        if (user.getStakeHolderUser().getStatus() != 1) {
            throw new UnauthorizedException("sth_w_w", "");
        }

        return user;

    }
    public UserEntity getUserPrincipalVld() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(authentication.getName());
        return user;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get Credential login
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param username
     * @Param password
     * @Param credential
     * @Param code
     * @Param provider
     * @Return TokenTF
     */
    public TokenTF getCredential(String username, String password, String credential, String code, String provider) {

        String baseUrl = String.format("%s://%s:%d/",request.getScheme(),  request.getServerName(), request.getServerPort());

        RestTemplate restAPi = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", credential);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", username);
        map.add("password", password);
        map.add("code", code);
        map.add("provider", provider);
        map.add("grant_type", "password");

        HttpEntity<MultiValueMap<String, String>> requestSMS =
                new HttpEntity<>(map, headers);
        TokenTF data = restAPi.exchange(baseUrl + "oauth/token", HttpMethod.POST, requestSMS, TokenTF.class).getBody();

        return data;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get credential oauth2 and make validation
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param httpHeaders
     * @Return String
     */
    public String oauth2Credential(HttpHeaders httpHeaders) {

        String credentials = environment.getProperty("spring.oauth2.client-id") + ":" + environment.getProperty("spring.oauth2.client-secret");
        String encCredentials = "Basic " + new String(Base64.encodeBase64(credentials.getBytes()));

        if (!httpHeaders.getFirst("Authorization").equals(encCredentials)) {
            throw new UnauthorizedException("Unauthorized", null);
        }
        return httpHeaders.getFirst("Authorization");

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Fields list user details
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param token
     * @Param user
     * @Return UserDetailsTF
     */
    public UserDetailsTF userFields(UserEntity user, String token) {

        UserDetailsTF userDetailDao = new UserDetailsTF();

        userDetailDao.setToken(token);
        userDetailDao.setFirstName(generalBean.strCv(user.getStakeHolderUser().getFirstName()));
        userDetailDao.setLastName(generalBean.strCv(user.getStakeHolderUser().getLastName()));
        userDetailDao.setEmail(generalBean.strCv(user.getEmail()));
        userDetailDao.setPhone(generalBean.strCv(user.getPhone()));
        userDetailDao.setCode(generalBean.strCv(user.getCode()));
        userDetailDao.setGender(generalBean.strCv(user.getStakeHolderUser().getGender()));
        userDetailDao.setStatus(user.getStakeHolderUser().getStatus());
        userDetailDao.setSkyowner(user.getStakeHolderUser().getIsSkyowner());
        userDetailDao.setUserCode(user.getStakeHolderUser().getUserCode());

        if (user.getStakeHolderUser().getIsSkyowner() == 1) {
            List<CompanyRS> companyRS = companiesDetails(user.getStakeHolderUser().getStakeholderCompanies());
            userDetailDao.setCompanies(companyRS);
        }

        for (ContactEntity contacts : user.getStakeHolderUser().getContactEntities()) {
            if (contacts.getType().equals("a")) {
                userDetailDao.setAddress(generalBean.strCv(contacts.getValue()));
            }
        }

        String photoName = user.getStakeHolderUser().getPhoto();
        if (user.getStakeHolderUser().getPhoto() == null) {
            photoName = "default.png";
        }

        userDetailDao.setPhotoMedium(environment.getProperty("spring.awsImageUrl.profile.url_larg") + photoName);
        userDetailDao.setPhotoSmall(environment.getProperty("spring.awsImageUrl.profile.url_small") + photoName);

        var userHasCompany = companyHasUserRP.findByStakeholderUserId(user.getStakeHolderUser().getId());
        if (userHasCompany != null) {
            userDetailDao.setRole(userHasCompany.getSkyuserRole());
            List<PermissionRS> permissions = getPermissions(userHasCompany.getSkyuserRole());
            userDetailDao.setPermission(permissions);
        }

        return userDetailDao;

    }


    public List<PermissionRS> getPermissions(String role) {

        List<PermissionTO> pmsTO = userNQ.listPermission(role);
        List<PermissionRS> pmsRS = new ArrayList<>();

        for (PermissionTO pmTO : pmsTO) {
            PermissionRS pmRS = new PermissionRS();
            BeanUtils.copyProperties(pmTO, pmRS);
            pmsRS.add(pmRS);
        }

        return pmsRS;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Fields list companies details
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return stkCompanies
     */
    public List<CompanyRS> companiesDetails(List<StakeholderCompanyEntity> companies) {

        List<CompanyRS> companyRSS = new ArrayList<>();

        for (StakeholderCompanyEntity company: companies) {

            CompanyRS companyRS = new CompanyRS();
            companyRS.setId(company.getId());
            companyRS.setCompanyName(company.getCompanyName());
            companyRS.setContactPerson(company.getContactPerson());
            companyRS.setContactPosition(company.getContactPosition());

            if (company.getStakeholderCompanyDocs().size() > 0) {
                Optional<StakeholderCompanyDocsEntity> stkCompanyDocs = company.getStakeholderCompanyDocs().stream().findFirst();
                companyRS.setLicense(environment.getProperty("spring.awsImageUrl.companyLicense") + stkCompanyDocs.get().getImage());
            }
            if (company.getStakeholderCompanyDocs().size() > 1) {
                Optional<StakeholderCompanyDocsEntity> stkCompanyDocsSecond = company.getStakeholderCompanyDocs().stream().skip(company.getStakeholderCompanyDocs().size() - 1).findFirst();
                companyRS.setLicenseSecond(environment.getProperty("spring.awsImageUrl.companyLicense") + stkCompanyDocsSecond.get().getImage());
            }

            companyRS.setProfileImg(company.getProfileImg());
            companyRS.setTypeValue(company.getTypeValue());

            companyContacts(company, companyRS);

            companyRSS.add(companyRS);
        }

        return companyRSS;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get company contacts
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return stkCompanies
     */
    public void companyContacts(StakeholderCompanyEntity company, CompanyRS companyRS) {

        List<ContactEntity> contacts = contactRP.getContactCM(company.getId());
        for (ContactEntity contact : contacts) {
            if (contact.getType().equals("a")) {
                companyRS.setAddress(contact.getValue());
            }
            if (contact.getType().equals("e")) {
                companyRS.setEmail(contact.getValue());
            }
            if (contact.getType().equals("p")) {
                companyRS.setPhone(contact.getValue());
            }
        }

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Implement upload image
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param url
     * @Param fileName
     * @Param largPath
     * @Param smallPath
     * @Return String
     */
    public String uploadPhoto(String url, String largPath, String smallPath) {

        String fileName = apiBean.generateFileName(null);

        String imageEx = null;
        BufferedImage image = null;

        try {
            String patter = "^(http|https|ftp)://.*$";
          if (url.matches(patter)) {
                URL urls = new URL(url);
                image = ImageIO.read(urls);

                URLConnection urlConnection = urls.openConnection();
                String mimeType = urlConnection.getContentType();

                imageEx = removePrefix(mimeType, "image/");

            } else {
                File input = new File(url);
                image = ImageIO.read(input);
                imageEx = getExtensionByStringHandling(url).get();
            }

            BufferedImage resizedSmall = resize(image, 150, 150);

            File outputSmall = new File("small." + imageEx);

            BufferedImage resizedLarg = resize(image, 450, 450);
            File outputLarg = new File("larg." + imageEx);
            ImageIO.write(resizedLarg, imageEx, outputLarg);
            ImageIO.write(resizedSmall, imageEx, outputSmall);

            uploadFileTos3bucket(fileName + "." + imageEx, outputLarg, "/uploads" + largPath);
            uploadFileTos3bucket(fileName + "." + imageEx, outputSmall, "/uploads" + smallPath);

            outputLarg.delete();
            outputSmall.delete();

        } catch (Exception e) {
            throw new InternalServerError("Can not specific file", null);
        }
        return fileName + "." + imageEx;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Implement upload image
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param url
     * @Param fileName
     * @Param largPath
     * @Param smallPath
     * @Return String
     */
    public String uploadFileForm(MultipartFile multipartFile, String largPath, String smallPath) {

        String fileName = apiBean.generateFileName(null);
        Optional<String> imageEx = null;

        try {

            File file = convertMultiPartToFile(multipartFile);

            String fileNames = multipartFile.getOriginalFilename();
            imageEx = getExtensionByStringHandling(fileNames);

            uploadFileTos3bucket(fileName + "." + imageEx.get(), file, "/uploads" + largPath);
            uploadFileTos3bucket(fileName + "." + imageEx.get(), file, "/uploads" + smallPath);

            file.delete();

        } catch (Exception e) {
            throw new InternalServerError("Can not specific file", null);
        }
        return fileName + "." + imageEx.get();

    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Implement upload license
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param url
     * @Param fileName
     * @Param largPath
     * @Param smallPath
     * @Return String
     */
    public String uploadLicense(MultipartFile multipartFile, String largPath, String originName) {

        String fileName = apiBean.generateFileName(null);

        try {

            File file = convertMultiPartToFile(multipartFile);
            String fileNames = multipartFile.getOriginalFilename();

            fileName = fileName + "." + getExtensionByStringHandling(fileNames).get();

            if (originName != null) {
                fileName = originName;
            }

            uploadFileTos3bucket(fileName, file, "/uploads" + largPath);
            file.delete();
        } catch (Exception e) {
            throw new InternalServerError("Can not specific file", null);
        }

        return fileName;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Remove prefix string
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param s
     * @Param prefix
     */
    public static String removePrefix(String s, String prefix)
    {
        if (s != null && prefix != null && s.startsWith(prefix)){
            return s.substring(prefix.length());
        }
        return s;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Upload file to amazon
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param filename
     * @Param file
     */
    private String uploadFileTos3bucket(String fileName, File file, String path) {

        try {
            s3client.putObject(new PutObjectRequest(environment.getProperty("spring.awsImage.bucket") + path, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (AmazonServiceException e) {
            return "uploadFileTos3bucket().Uploading failed :" + e.getMessage();
        }

        return "Uploading Successfull -> ";

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get extension image
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param filename
     */
    public Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Resize image
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param img
     * @Param height
     * @Param width
     */
    private static BufferedImage resize(BufferedImage img, int height, int width) {

        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get staff profile
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return UserProfileTO
     */
    public UserProfileTO getUserProfile() {

        var user = getUserPrincipal();
        var company = companyHasUserRP.findByStakeholderUserId(user.getStakeHolderUser().getId());

        var profile = new UserProfileTO();

        profile.setCompanyId(company.getStakeholderCompanyId());
        profile.setStakeHolderId(user.getStakeHolderUser().getId());
        profile.setUserId(user.getId());
        profile.setUserRole(company.getSkyuserRole());
        profile.setUserType(user.getStakeHolderUser().getIsSkyowner() == 1 ? "skyowner" : "skyuser");

        return profile;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get staff profile
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return UserProfileTO
     */
    public void registerPlayer(Long skyuserId) {

        String playerId = request.getHeader("X-PlayerId");

        if (playerId == "" || playerId == null) {
            throw new UnauthorizedException("sth_w_w", "");
        }

        UserPlayerEntity player = userPlayerRP.findByStuserIdAndPlayerId(skyuserId, playerId);

        if (player == null) {
            player = new UserPlayerEntity();
            player.setPlayerId(playerId);
            player.setStuserId(skyuserId);

            userPlayerRP.save(player);
        }

    }




}
