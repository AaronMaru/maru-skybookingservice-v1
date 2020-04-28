package com.skybooking.stakeholderservice.v1_0_0.util.skyuser;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.skybooking.stakeholderservice.exception.httpstatus.BadRequestException;
import com.skybooking.stakeholderservice.exception.httpstatus.InternalServerError;
import com.skybooking.stakeholderservice.exception.httpstatus.UnauthorizedException;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.company.BussinessTypeEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.company.StakeholderCompanyDocsEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.company.StakeholderCompanyEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.contact.ContactEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.locale.LocaleEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.notification.UserPlayerEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.redis.UserTokenEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.OauthUserAccessTokenEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.UserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.currency.CurrencyNQ;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.currency.CurrencyTO;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.user.*;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.company.BussinessTypeRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.company.CompanyDocRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.company.CompanyHasUserRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.company.CompanyStatusRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.contact.ContactRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.country.LocationRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.locale.CountryLocaleRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.locale.LocaleRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.notification.UserPlayerRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.redis.UserTokenRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.OauthUserRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.UserRepository;
import com.skybooking.stakeholderservice.v1_0_0.transformer.TokenTF;
import com.skybooking.stakeholderservice.v1_0_0.transformer.UserDetailsTF;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.company.CompanyRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.company.LicenseRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.passenger.PassengerRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.PermissionRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user.UserDetailsTokenRS;
import com.skybooking.stakeholderservice.v1_0_0.util.JwtUtils;
import com.skybooking.stakeholderservice.v1_0_0.util.datetime.DateTimeBean;
import com.skybooking.stakeholderservice.v1_0_0.util.general.ApiBean;
import com.skybooking.stakeholderservice.v1_0_0.util.general.GeneralBean;
import com.skybooking.stakeholderservice.v1_0_0.util.header.HeaderBean;
import com.skybooking.stakeholderservice.v1_0_0.util.passenger.Passenger;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.math.NumberUtils;
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
import java.util.List;
import java.util.*;

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
    private CompanyStatusRP companyStatusRP;

    @Autowired
    private UserPlayerRP userPlayerRP;

    @Autowired
    private UserTokenRP userTokenRP;

    @Autowired
    private LocationRP locationRP;

    @Autowired
    private BussinessTypeRP bussinessTypeRP;

    @Autowired
    private DateTimeBean dateTimeBean;

    @Autowired
    private LocaleRP localeRP;

    @Autowired
    private CurrencyNQ currencyNQ;

    @Autowired
    private HeaderBean headerBean;

    @Autowired
    private OauthUserRP oauthUserRP;

    @Autowired
    private CountryLocaleRP countryLocaleRP;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CompanyDocRP companyDocRP;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get user principal
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return UserEntity
     */
    public UserEntity getUserPrincipal() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUsername(authentication.getName());

        if (user == null) {
            throw new UnauthorizedException("Unauthorized", null);
        }

        if (user.getStakeHolderUser().getStatus() != 1) {
            throw new UnauthorizedException("Unauthorized", null);
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

        String baseUrl = String.format("%s://%s:%d/", request.getScheme(), request.getServerName(), request.getServerPort());

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
        userDetailDao.setGender(generalBean.strCv(user.getStakeHolderUser().getGender()).toUpperCase());
        userDetailDao.setIsSkyowner(user.getStakeHolderUser().getIsSkyowner());
        userDetailDao.setUserCode(user.getStakeHolderUser().getUserCode());
        userDetailDao.setDob(generalBean.strCv(user.getStakeHolderUser().getDateOfBirth()));
        userDetailDao.setJoined(dateTimeBean.convertDateTimeISO(user.getCreatedAt()));
        userDetailDao.setCurrencyId(user.getStakeHolderUser().getCurrencyId());

        String nationlity = getNationality(user.getStakeHolderUser().getNationality());
        userDetailDao.setNationality(nationlity);
        userDetailDao.setIsoCountry(generalBean.strCv(user.getStakeHolderUser().getNationality()).toUpperCase());

        LocaleEntity defaultLocale = localeRP.findLocaleByLocale("en");
        List<CurrencyTO> currencyTOList = currencyNQ.findAllByLocaleId(defaultLocale.getId());

        currencyTOList.forEach(item -> {
            if ((long) item.getCurrencyId() == user.getStakeHolderUser().getCurrencyId())
                userDetailDao.setCurencyCode(item.getCode());
        });

        TotalBookingTO totalBooking = userNQ.totalBooking(user.getStakeHolderUser().getId(), (long) 0, "skyuser");
        userDetailDao.setTotalBooking(totalBooking.getBookingQty());

        if (user.getStakeHolderUser().getStakeholderCompanies() != null) {
            if (user.getStakeHolderUser().getStakeholderCompanies().size() > 0) {
                List<CompanyRS> companyRS = companiesDetails(user.getStakeHolderUser().getStakeholderCompanies());
                userDetailDao.setCompanies(companyRS);
            }
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

            userDetailDao.setIsSkyowner(userHasCompany.getStatus());

            userDetailDao.setRole(userHasCompany.getSkyuserRole());
            List<PermissionRS> permissions = getPermissions(userHasCompany.getSkyuserRole());
            userDetailDao.setPermission(permissions);

        }

        return userDetailDao;

    }


    private String getNationality(String iso) {
        NationalityTO nationalityTO = userNQ.getNationality(iso != null ? iso : "", headerBean.getLocalizationId());

        String nationlity = "";

        if (nationalityTO != null) {
            nationlity = nationalityTO.getNationality();
        }

        return nationlity;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Getting a listing of permission
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param role
     * @Return List<PermissionRS>
     */
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
     * @Param companies
     * @Return List<CompanyRS>
     */
    public List<CompanyRS> companiesDetails(List<StakeholderCompanyEntity> companies) {

        List<CompanyRS> companyRSS = new ArrayList<>();

        for (StakeholderCompanyEntity company : companies) {

            CompanyRS companyRS = new CompanyRS();
            companyRS.setId(company.getId());

            companyRS.setCompanyName(company.getCompanyName());
            companyRS.setContactPerson(company.getContactPerson());
            companyRS.setContactPosition(company.getContactPosition());
            companyRS.setBusinessTypeId(company.getBussinessTypeId());

            BussinessTypeEntity business = bussinessTypeRP.findById(company.getBussinessTypeId()).orElse(null);
            companyRS.setBusinessTypeName(business.getName());

            companyRS.setDescription(company.getDescription());

            String status = companyStatus(company.getStatus());
            companyRS.setStatus(status.toUpperCase());

            var country = locationRP.findByLocationableId(company.getId());
            if (country != null) {
                var countryLocale = countryLocaleRP.findCountryBylocale(country.getCountryId(), headerBean.getLocalizationId());
                companyRS.setCountryId(country.getCountryId());
                companyRS.setCountryName(countryLocale.getName());
            }

            List<LicenseRS> licenses = new ArrayList<>();

            var companyDocs = companyDocRP.findByStakeholderCompanyAndType(company, "license");
            for (StakeholderCompanyDocsEntity doc : companyDocs) {
                LicenseRS license = new LicenseRS();
                license.setDocUrl(environment.getProperty("spring.awsImageUrl.companyLicense") + doc.getFileName());
                licenses.add(license);
            }
            companyRS.setLicenses(licenses);

            var profileItenaryEntity = companyDocRP.findByStakeholderCompanyAndType(company, "itenery");
            String profileItenary = (profileItenaryEntity.size() == 0) ? "default.png" : profileItenaryEntity.get(0).getFileName();
            companyRS.setProfileItenary(environment.getProperty("spring.awsImageUrl.companyProfile") + "origin/" + profileItenary);

            String profile = (company.getProfileImg() == null) ? "default.png" : company.getProfileImg();
            companyRS.setProfileImg(environment.getProperty("spring.awsImageUrl.companyProfile") + "medium/" + profile);

            TotalBookingTO totalBooking = userNQ.totalBooking((long) 0, company.getId(), "company");
            companyRS.setTotalBooking(totalBooking.getBookingQty());

            var companyStatus = companyStatusRP.findByCompany(company.getId());
            if (companyStatus != null) {
                if (companyStatus.getStatus() == 1) {
                    companyRS.setReason(companyStatus.getComment());
                }
            }

            companyContacts(company, companyRS);

            companyRSS.add(companyRS);
        }

        return companyRSS;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get company status
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return String
     */
    public String companyStatus(int status) {

        String companyStatus = "";

        switch (status) {
            case 0:
                companyStatus = "pending";
                break;
            case 1:
                companyStatus = "reject";
                break;
            case 3:
                companyStatus = "banned";
                break;
            case 4:
                companyStatus = "approved";
                break;
            case 5:
                companyStatus = "deactive";
                break;
            default:
                companyStatus = "other";
        }

        return companyStatus;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get company contacts
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return company
     * @Return companyRS
     */
    public void companyContacts(StakeholderCompanyEntity company, CompanyRS companyRS) {

        List<ContactEntity> contacts = contactRP.getContactCM(company.getId());
        for (ContactEntity contact : contacts) {
            switch (contact.getType()) {
                case "a":
                    companyRS.setAddress(contact.getValue());
                    break;
                case "p":
                    companyRS.setPhone(contact.getValue());
                    break;
                case "z":
                    companyRS.setPostalOrZipCode(contact.getValue());
                    break;
                case "w":
                    companyRS.setWebsite(contact.getValue());
                    break;
                case "c":
                    companyRS.setCity(contact.getValue());
                    break;
                case "e":
                    companyRS.setEmail(contact.getValue());
                    break;
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

            limitFileType(imageEx, new String[]{"PNG", "JPG", "JPEG"});

            uploadFileTos3bucket(fileName + "." + imageEx, outputLarg, "/uploads" + largPath);
            uploadFileTos3bucket(fileName + "." + imageEx, outputSmall, "/uploads" + smallPath);

            outputLarg.delete();
            outputSmall.delete();
        } catch (Exception e) {
//            throw new InternalServerError("up_img_type", null);
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
            limitFileType(imageEx.get(), new String[]{"PNG", "JPG", "JPEG"});

            uploadFileTos3bucket(fileName + "." + imageEx.get(), file, "/uploads" + largPath);
            uploadFileTos3bucket(fileName + "." + imageEx.get(), file, "/uploads" + smallPath);

            file.delete();

        } catch (Exception e) {
            throw new InternalServerError("up_img_type", null);
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
        Optional<String> imageEx = null;

        try {

            File file = convertMultiPartToFile(multipartFile);
            String fileNames = multipartFile.getOriginalFilename();

            fileName = fileName + "." + getExtensionByStringHandling(fileNames).get();

            if (originName != null) {
                fileName = originName;
            }

            imageEx = getExtensionByStringHandling(fileNames);
            limitFileType(imageEx.get(), new String[]{"PNG", "JPG", "JPEG", "PDF", "DOC", "DOCX"});

            uploadFileTos3bucket(fileName, file, "/uploads" + largPath);
            file.delete();

        } catch (Exception e) {
            throw new InternalServerError("up_img_type_cm", null);
        }

        return fileName;

    }

    private void limitFileType(String ext, String fileType[]) {

        Boolean b = false;

        for (int i = 0; i < fileType.length; i++) {
            if (fileType[i].equals(ext.toUpperCase())) {
                b = true;
            }
        }
        if (!b) {
            throw new BadRequestException("File type not allow", null);
        }

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Remove prefix string
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param s
     * @Param prefix
     */
    public static String removePrefix(String s, String prefix) {
        if (s != null && prefix != null && s.startsWith(prefix)) {
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
    public void registerPlayer(UserEntity user) {

        String playerId = request.getHeader("X-PlayerId");

        if (playerId == "" || playerId == null) {
            throw new UnauthorizedException("sth_w_w", null);
        }

        UserPlayerEntity player = userPlayerRP.findByStakeholderUserIdAndPlayerId(user.getStakeHolderUser().getId(), playerId);

        if (player == null) {
            player = new UserPlayerEntity();
            player.setPlayerId(playerId);
            player.setStakeholderUserId(user.getStakeHolderUser().getId());

            if (user.getStakeHolderUser().getIsSkyowner() == 1) {
                player.setStakeholderCompanyId(user.getStakeHolderUser().getStakeholderCompanies().get(0).getId());
            }

            userPlayerRP.save(player);
        }

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get staff profile
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param skyuserRQ
     * @Param httpHeaders
     * @Param id
     * @Param password
     */
    public void storeTokenRedis(UserEntity user, String password) {

        String username = (user.getEmail() != null) ? user.getEmail()
                : user.getPhone();

        String credentials = environment.getProperty("spring.oauth2.client-id") + ":" + environment.getProperty("spring.oauth2.client-secret");
        String encCredentials = "Basic " + new String(Base64.encodeBase64(credentials.getBytes()));
        TokenTF data = getCredential(username, password, encCredentials, user.getCode(), null);

        UserTokenEntity userToken = userTokenRP.findById(user.getId()).orElse(new UserTokenEntity());

        userToken.setUserId(user.getId());
        userToken.setToken(data.getAccess_token());

        userTokenRP.save(userToken);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get username
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param username
     * @Param code
     */
    public String getUsername(String username, String code) {

        String fullUsername = "";

        if (NumberUtils.isNumber(username)) {
            fullUsername = code + "" + username.replaceFirst("^0+(?!$)", "");
            if (code == null) {
                fullUsername = username.replaceFirst("^0+(?!$)", "");
            }
        } else {
            fullUsername = username;
        }

        return fullUsername;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get user details
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param user
     * @Param username
     * @Param password
     */
    public UserDetailsTokenRS userDetailByLogin(UserEntity user, String username, String password) {

        UserDetailsTokenRS userDetailsTokenRS = new UserDetailsTokenRS();
        String credentials = environment.getProperty("spring.oauth2.client-id") + ":" + environment.getProperty("spring.oauth2.client-secret");
        String encCredentials = "Basic " + new String(Base64.encodeBase64(credentials.getBytes()));
        TokenTF data = getCredential(username, password, encCredentials, user.getCode(), null);
        BeanUtils.copyProperties(userFields(user, data.getAccess_token()), userDetailsTokenRS);

        return userDetailsTokenRS;

    }



    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Store Token
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param user
     * @Param username
     * @Param password
     */
    public void storeUserTokenLastLogin(String token, UserEntity user) {

        OauthUserAccessTokenEntity oauthUserAccessTokenEntity = new OauthUserAccessTokenEntity();
        oauthUserAccessTokenEntity.setJwtId(jwtUtils.getClaim(token, "jti", String.class));
        oauthUserAccessTokenEntity.setUserId(user.getId());
        oauthUserAccessTokenEntity.setName("Skybooking client");
        oauthUserAccessTokenEntity.setStatus(1);
        oauthUserRP.save(oauthUserAccessTokenEntity);

        user.setLastLoginAt(new Date());
        userRepository.save(user);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Set passenger type
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param birthday
     * @Param passenger
     * @Return
     */
    public void setPasengerType(String birthday, PassengerRS passenger) {

        DateTimeBean dateTime = new DateTimeBean();
        Passenger clPassenger = new Passenger();

        Date birth = dateTime.convertDateTimes(birthday);
        String pType = clPassenger.passengerType(birth);
        passenger.setPassType(pType);

    }


}