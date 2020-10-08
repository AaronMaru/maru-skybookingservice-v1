package com.skybooking.skypointservice.v1_0_0.service.topUp;

import com.skybooking.skypointservice.constant.*;
import com.skybooking.skypointservice.httpstatus.BadRequestException;
import com.skybooking.skypointservice.httpstatus.InternalServerError;
import com.skybooking.skypointservice.util.HeaderDataUtil;
import com.skybooking.skypointservice.util.ValidateKeyUtil;
import com.skybooking.skypointservice.v1_0_0.client.ClientResponse;
import com.skybooking.skypointservice.v1_0_0.client.event.action.EventEmailAction;
import com.skybooking.skypointservice.v1_0_0.client.event.action.EventNotificationAction;
import com.skybooking.skypointservice.v1_0_0.client.event.model.requset.SkyPointTopUpRQ;
import com.skybooking.skypointservice.v1_0_0.client.event.model.requset.TopUpNotificationRQ;
import com.skybooking.skypointservice.v1_0_0.client.payment.action.PaymentAction;
import com.skybooking.skypointservice.v1_0_0.client.payment.model.requset.PaymentGetUrlPaymentRQ;
import com.skybooking.skypointservice.v1_0_0.client.stakeholder.model.response.BasicCompanyAccountInfoRS;
import com.skybooking.skypointservice.v1_0_0.client.stakeholder.model.response.UserReferenceRS;
import com.skybooking.skypointservice.v1_0_0.helper.AccountHelper;
import com.skybooking.skypointservice.v1_0_0.helper.SkyPointTransactionHelper;
import com.skybooking.skypointservice.v1_0_0.helper.TopUpHelper;
import com.skybooking.skypointservice.v1_0_0.helper.TransactionHelper;
import com.skybooking.skypointservice.v1_0_0.io.entity.account.AccountEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.config.ConfigTopUpEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.config.ConfigUpgradeLevelEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.redis.BookingLanguageCached;
import com.skybooking.skypointservice.v1_0_0.io.entity.topUp.TopUpDocumentEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionContactInfoEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionValueEntity;
import com.skybooking.skypointservice.v1_0_0.io.repository.account.AccountRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.config.ConfigTopUpRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.config.ConfigUpgradeLevelRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.redis.BookingLanguageRedisRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.topUp.TopUpDocumentRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.transaction.TransactionContactInfoRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.transaction.TransactionRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.transaction.TransactionValueRP;
import com.skybooking.skypointservice.v1_0_0.service.BaseServiceIP;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.ContactInfo;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.topUp.ConfirmOfflineTopUpRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.topUp.OfflineTopUpRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.topUp.OnlineTopUpRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.topUp.PostOnlineTopUpRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.topUp.OfflineTopUpRS;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.topUp.PreTopUpRS;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.topUp.ProceedTopUpRS;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.transaction.AccountInfo;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.transaction.TopUpInfo;
import com.skybooking.skypointservice.v1_0_0.util.datetime.DateTimeBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.skybooking.skypointservice.constant.TopUpTypeConstant.TOPUP_RECEIPT;

@Service
public class TopUpIP extends BaseServiceIP implements TopUpSV {
    @Autowired
    private AccountRP accountRP;

    @Autowired
    private TransactionRP transactionRP;

    @Autowired
    private ConfigTopUpRP configTopUpRP;

    @Autowired
    private AccountHelper accountHelper;

    @Autowired
    private TransactionHelper transactionHelper;

    @Autowired
    private TopUpHelper topUpHelper;

    @Autowired
    private PaymentAction paymentAction;

    @Autowired
    private SkyPointTransactionHelper skyPointTransactionHelper;

    @Autowired
    private EventEmailAction eventEmailAction;

    @Autowired
    private TransactionContactInfoRP transactionContactInfoRP;

    @Autowired
    private EventNotificationAction eventNotificationAction;

    @Autowired
    private TransactionValueRP transactionValueRP;

    @Autowired
    private DateTimeBean dateTimeBean;

    @Autowired
    private ConfigUpgradeLevelRP configUpgradeLevelRP;

    @Autowired
    private TopUpDocumentRP topUpDocumentRP;

    @Autowired
    private HeaderDataUtil headerDataUtil;

    @Autowired
    private BookingLanguageRedisRP bookingLanguageRedisRP;

    @Override
    @Transactional(rollbackFor = {})
    public StructureRS offlineTopUp(HttpServletRequest httpServletRequest, OfflineTopUpRQ offlineTopUpRQ) {
        try {
            String languageCode = headerDataUtil.languageCode(httpServletRequest);
            //========== Validate key
            OfflineTopUpRQ offlineTopUpRQValidate = new OfflineTopUpRQ();
            BeanUtils.copyProperties(offlineTopUpRQ, offlineTopUpRQValidate);
            offlineTopUpRQValidate.setFile(null);
            List<String> keyList = Arrays.asList("amount", "userCode", "referenceCode", "createdBy",
                    "stakeholderCompanyId", "stakeholderUserId", "email", "name", "phoneNumber");
            ValidateKeyUtil.validateMultipartFile(offlineTopUpRQ.getFile(), "file");
            ValidateKeyUtil.validateKey(offlineTopUpRQValidate, keyList);

            //========= Validate amount not negative
            ValidateKeyUtil.validateAmountNotNegative(offlineTopUpRQ.getAmount());

            //========= Validate name
            ValidateKeyUtil.validateLetter(offlineTopUpRQ.getName());
            //========= Validate phone number
            ValidateKeyUtil.validateNumber(offlineTopUpRQ.getPhoneNumber());

            //========== Validate user
            accountHelper.getBasicCompanyAccountInfo(offlineTopUpRQ.getUserCode());

            //========== Get value from jwt header
            String createdBy = offlineTopUpRQ.getCreatedBy();
            String userType = UserTypeConstant.SKYOWNER;

            //============ Get config extra rate by userType
            ConfigTopUpEntity configTopUp = configTopUpRP.findByTypeAndTopUpKeyAndStatus(
                    userType, ConfigTopUpTypeConstant.EXTRA_RATE, true);

            //======== Create account if not exist
            AccountEntity account = topUpHelper.createAccountIfNotExist(offlineTopUpRQ.getUserCode(), userType);

            //========= Save transaction
            TransactionEntity transaction = transactionHelper.saveTransactionOfflineTopUp(
                    new TransactionEntity(), account, offlineTopUpRQ, createdBy);

            //========= Save file (topUp document)
            topUpHelper.saveFileForTopUpOffline(offlineTopUpRQ.getFile(), transaction);

            //======== Save transactionValue
            BigDecimal extraEarning = transaction.getAmount().multiply(configTopUp.getValue());
            topUpHelper.saveTransactionValueForTopUp(transaction, configTopUp, extraEarning);

            //======== Save top up contact info
            TransactionContactInfoEntity transactionContactInfo = new TransactionContactInfoEntity();
            BeanUtils.copyProperties(offlineTopUpRQ, transactionContactInfo);
            transactionContactInfo.setTransactionId(transaction.getId());
            transactionContactInfo.setPhoneCode("");
            transactionContactInfoRP.save(transactionContactInfo);

            //======= Hit api to get basic account info
            BasicCompanyAccountInfoRS basicCompanyAccountInfoRS = new BasicCompanyAccountInfoRS();
            BeanUtils.copyProperties(offlineTopUpRQ, basicCompanyAccountInfoRS);

            //======== Return response
            AccountInfo accountInfo = new AccountInfo();
            BeanUtils.copyProperties(account, accountInfo);
            //====== Get config level upgrade
            ConfigUpgradeLevelEntity configUpgradeLevel = configUpgradeLevelRP.getRecordByFromValueAndToValueAndTypeAndLanguageCode(
                    account.getSavedPoint(),
                    account.getType(),
                    languageCode
            );
            accountInfo.setLevelName(configUpgradeLevel.getLevelName());

            //======== Set topUP info response
            TransactionValueEntity transactionValue = transactionValueRP.findByTransactionIdAndTransactionTypeCode(transaction.getId(),
                    TransactionTypeConstant.TOP_UP);
            TopUpInfo topUpInfo = topUpHelper.topUpInfo(transaction, transactionValue);

            OfflineTopUpRS offlineTopUpRS = new OfflineTopUpRS();
            offlineTopUpRS.setBasicCompanyAccountInfoRS(basicCompanyAccountInfoRS);
            offlineTopUpRS.setAccountInfo(accountInfo);
            offlineTopUpRS.setTopUpInfo(topUpInfo);

            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, offlineTopUpRS);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("unexpected_error", null);
        }
    }

    @Override
    public StructureRS confirmOfflineTopUp(HttpServletRequest httpServletRequest, ConfirmOfflineTopUpRQ confirmOfflineTopUpRQ) {
        try {
            StructureRS structureRS = new StructureRS();
            //======== Validate key request
            List<String> keyList = Arrays.asList("transactionCode");
            ValidateKeyUtil.validateKey(confirmOfflineTopUpRQ, keyList);

            TransactionValueEntity transactionValue = transactionValueRP.findByCode(confirmOfflineTopUpRQ.getTransactionCode());
            if (transactionValue == null) {
                throw new BadRequestException("transaction_not_found", null);
            }

            TransactionEntity transaction = transactionRP.getOne(transactionValue.getTransactionId());
            if (transaction.getStatus().equalsIgnoreCase(TransactionStatusConstant.SUCCESS)) {
                throw new BadRequestException("transaction_approved", null);
            }

            updateTopUp(httpServletRequest, transaction, transactionValue);

            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, null);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("unexpected_error", null);
        }
    }

    @Override
    public StructureRS preTopUp(HttpServletRequest httpServletRequest, OnlineTopUpRQ onlineTopUpRQ) {
        try {
            StructureRS structureRS = new StructureRS();
            //======== Validate key request
            List<String> keyList = Arrays.asList("amount");
            ValidateKeyUtil.validateKey(onlineTopUpRQ, keyList);

            //========= Validate amount not negative
            ValidateKeyUtil.validateAmountNotNegative(onlineTopUpRQ.getAmount());

            //======== userReferenceRS
            UserReferenceRS userReferenceRS = accountHelper.getUserReference(httpServletRequest);
            String userType = userReferenceRS.getType();
            String userCode = userReferenceRS.getUserCode();

            AccountEntity account = accountRP.findAccountEntityByUserCode(userCode)
                    .orElse(new AccountEntity(0));

            //======== Validate amount for ONLINE topUp
            topUpHelper.validateForOnlineTopUp(onlineTopUpRQ.getAmount(), userType, account.getId());

            //============ Get config extra rate by userType
            ConfigTopUpEntity configTopUp = configTopUpRP.findByTypeAndTopUpKeyAndStatus(userType,
                    ConfigTopUpTypeConstant.EXTRA_RATE, true);

            //======== Return response
            BigDecimal topUpSkyPoint = onlineTopUpRQ.getAmount();
            BigDecimal earnSkyPoint = onlineTopUpRQ.getAmount().multiply(configTopUp.getValue());
            BigDecimal totalSkyPoint = topUpSkyPoint.add(earnSkyPoint);

            PreTopUpRS preTopUpRS = new PreTopUpRS();
            preTopUpRS.setTopUpSkyPoint(topUpSkyPoint);
            preTopUpRS.setEarnSkyPoint(earnSkyPoint);
            preTopUpRS.setTotalSkyPoint(totalSkyPoint);
            preTopUpRS.setAmountPayable(onlineTopUpRQ.getAmount());
            structureRS.setData(preTopUpRS);
            structureRS.setMessage(ResponseConstant.SUCCESS);
            return structureRS;
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("unexpected_error", null);
        }
    }

    @Override
    @Transactional(rollbackFor = {})
    public StructureRS proceedTopUp(HttpServletRequest httpServletRequest, OnlineTopUpRQ onlineTopUpRQ) {

        try {
            StructureRS structureRS = new StructureRS();
            //======== Validate key request
            List<String> keyList = Arrays.asList("amount", "paymentMethodCode", "contactInfo");
            ValidateKeyUtil.validateKey(onlineTopUpRQ, keyList);

            //========= Validate amount not negative
            ValidateKeyUtil.validateAmountNotNegative(onlineTopUpRQ.getAmount());

            //======== Get userType
            ContactInfo contactInfo = onlineTopUpRQ.getContactInfo();
            List<String> contactInfoKey = Arrays.asList("email", "name", "phoneCode", "phoneNumber");
            ValidateKeyUtil.validateKey(contactInfo, contactInfoKey);

            UserReferenceRS userReferenceRS = accountHelper.getUserReference(httpServletRequest);
            String userType = userReferenceRS.getType();
            String userCode = userReferenceRS.getUserCode();
            Integer stakeholderUserId = userReferenceRS.getStakeholderUserId();
            Integer stakeholderCompanyId = userReferenceRS.getStakeholderCompanyId();

            //======== Create account if not exist
            AccountEntity account = topUpHelper.createAccountIfNotExist(userCode, userType);

            //========== Validate amount
            topUpHelper.validateForOnlineTopUp(onlineTopUpRQ.getAmount(), userType, account.getId());

            //======== Hit api to get fee
            ClientResponse paymentRS = paymentAction.getPaymentMethodFee(onlineTopUpRQ.getPaymentMethodCode());
            Map<String, Object> dataRS = paymentRS.getData();
            BigDecimal percentage = new BigDecimal(dataRS.get("percentage").toString());
            BigDecimal fee = onlineTopUpRQ.getAmount().multiply(percentage.divide(new BigDecimal(100)));
            String paymentMethod = String.valueOf(dataRS.get("method"));

            //======== Create transaction
            TransactionEntity transaction = transactionHelper.saveTransactionOnlineTopUp(new TransactionEntity(),
                    account, onlineTopUpRQ, fee, paymentMethod, stakeholderUserId, stakeholderCompanyId);

            //======== Save top up contact info
            TransactionContactInfoEntity transactionContactInfo = new TransactionContactInfoEntity();
            BeanUtils.copyProperties(contactInfo, transactionContactInfo);
            transactionContactInfo.setTransactionId(transaction.getId());
            transactionContactInfoRP.save(transactionContactInfo);

            //============ Get config extra rate by userType
            ConfigTopUpEntity configTopUp = configTopUpRP.findByTypeAndTopUpKeyAndStatus(userType,
                    ConfigTopUpTypeConstant.EXTRA_RATE, true);

            //======== Save transactionValue
            BigDecimal extraEarning = transaction.getAmount().multiply(configTopUp.getValue());
            topUpHelper.saveTransactionValueForTopUp(transaction, configTopUp, extraEarning);

            //========Get transaction value of top up
            TransactionValueEntity transactionValue = transactionValueRP.findByTransactionIdAndTransactionTypeCode(transaction.getId(),
                    TransactionTypeConstant.TOP_UP);

            //======== Hit api to get paymentUrl
            PaymentGetUrlPaymentRQ paymentGetUrlPaymentRQ = new PaymentGetUrlPaymentRQ();
            paymentGetUrlPaymentRQ.setBookingCode(transactionValue.getCode());
            paymentGetUrlPaymentRQ.setPaymentCode(onlineTopUpRQ.getPaymentMethodCode());
            paymentGetUrlPaymentRQ.setProductType("POINT");
            ClientResponse paymentUrlData = paymentAction.getPaymentUrl(paymentGetUrlPaymentRQ);

            //Save Language to redis for email template
            BookingLanguageCached bookingLanguageCached = new BookingLanguageCached();
            bookingLanguageCached.setBookingCode(transactionValue.getCode());
            bookingLanguageCached.setLanguage(headerDataUtil.languageCodeExist(httpServletRequest));
            bookingLanguageRedisRP.save(bookingLanguageCached);

            Map<String, Object> data = paymentUrlData.getData();
            String url = data.get("urlPayment").toString();

            //======== Return response
            ProceedTopUpRS proceedTopUpRS = new ProceedTopUpRS();
            proceedTopUpRS.setPaymentUrl(url);
            structureRS.setData(proceedTopUpRS);
            return structureRS;
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("unexpected_error", null);
        }
    }

    @Override
    @Transactional(rollbackFor = {})
    public StructureRS postTopUp(HttpServletRequest httpServletRequest, PostOnlineTopUpRQ postOnlineTopUpRQ) {
        try {
            StructureRS structureRS = new StructureRS();
            //======== Validate key request
            List<String> keyList = Arrays.asList("paymentStatus", "transactionCode");
            ValidateKeyUtil.validateKey(postOnlineTopUpRQ, keyList);

            //======== Update transaction status
            TransactionValueEntity transactionValue = transactionValueRP.findByCode(postOnlineTopUpRQ.getTransactionCode());
            if (transactionValue == null) {
                throw new BadRequestException("transaction_not_found", null);
            }
            TransactionEntity transaction = transactionRP.getOne(transactionValue.getTransactionId());

            if (!transaction.getStatus().equalsIgnoreCase(TransactionStatusConstant.PENDING)) {
                throw new BadRequestException("transaction_updated", null);
            }

            if (postOnlineTopUpRQ.getPaymentStatus().equalsIgnoreCase("success")) {
                updateTopUp(httpServletRequest, transaction, transactionValue);
            } else {
                transaction.setStatus(TransactionStatusConstant.FAILED);
                transactionRP.save(transaction);
            }

            return structureRS;
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("unexpected_error", null);
        }
    }

    private ClientResponse sendMailTopUp(HttpServletRequest httpServletRequest, TransactionEntity transaction,
                                         TransactionValueEntity transactionValue, String email, String fullName, BigDecimal earnAmount,
                                         String phone) {
        //======= Send mail
        SkyPointTopUpRQ skyPointTopUpRQ = new SkyPointTopUpRQ();
        skyPointTopUpRQ.setAmount(transactionValue.getAmount());
        skyPointTopUpRQ.setEmail(email);
        skyPointTopUpRQ.setFullName(fullName);
        skyPointTopUpRQ.setEarnAmount(earnAmount);
        skyPointTopUpRQ.setPhone(phone);
        skyPointTopUpRQ.setTransactionId(transactionValue.getCode());
        skyPointTopUpRQ.setTransactionDate(dateTimeBean.convertDateTime(transaction.getCreatedAt()));
        return eventEmailAction.topUpEmail(httpServletRequest, skyPointTopUpRQ);
    }

    private void sendNotificationTopUp(HttpServletRequest httpServletRequest, TransactionEntity transaction) {
        TopUpNotificationRQ topUpNotificationRQ = new TopUpNotificationRQ();
        TransactionValueEntity transactionValue = transactionValueRP.findByTransactionIdAndTransactionTypeCode(transaction.getId(),
                TransactionTypeConstant.TOP_UP);
        topUpNotificationRQ.setBookingId(transactionValue.getCode());
        topUpNotificationRQ.setStakeholderCompanyId(transaction.getStakeholderCompanyId());
        topUpNotificationRQ.setStakeholderUserId(transaction.getStakeholderUserId());
        topUpNotificationRQ.setType("TOP_UP_POINT");
        eventNotificationAction.topUpNotification(httpServletRequest, topUpNotificationRQ);
    }

    private void updateTopUp(HttpServletRequest httpServletRequest, TransactionEntity transaction,
                             TransactionValueEntity transactionValue) {
        String languageCode = headerDataUtil.languageCode(httpServletRequest);
        transaction.setStatus(TransactionStatusConstant.SUCCESS);
        transactionRP.save(transaction);

        //======== Get account by accountId
        AccountEntity account = accountRP.getOne(transaction.getAccountId());

        //======== Get userType
        String userType = account.getType();

        //============ Get config extra rate by userType
        ConfigTopUpEntity configTopUp = configTopUpRP.findByTypeAndTopUpKeyAndStatus(userType,
                ConfigTopUpTypeConstant.EXTRA_RATE, true);

        //======== Save transactionValue && update account balance
        BigDecimal extraEarning = transaction.getAmount().multiply(configTopUp.getValue());
        topUpHelper.updateAccountAndSaveUpgradeLevelHistory(account, transaction, extraEarning, configTopUp.getValue(), userType);

        //======== Save sky point transaction
        skyPointTransactionHelper.saveSkyPointTransaction(userType, transaction.getStakeholderCompanyId(),
                transaction.getStakeholderUserId(), transaction);

        //======= Send mail
        TransactionContactInfoEntity transactionContactInfo = transactionContactInfoRP.findByTransactionId(transaction.getId());
        ClientResponse s3UploadRS = this.sendMailTopUp(httpServletRequest, transaction, transactionValue,
                transactionContactInfo.getEmail(), transactionContactInfo.getName(),
                transaction.getAmount().multiply(configTopUp.getValue()),
                transactionContactInfo.getPhoneCode() + transactionContactInfo.getPhoneNumber());

        Map<String, Object> dataRS = s3UploadRS.getData();

        TopUpDocumentEntity topUpDocument = new TopUpDocumentEntity();
        topUpDocument.setTransactionId(transaction.getId());
        topUpDocument.setFile(dataRS.get("file").toString());
        topUpDocument.setPath(dataRS.get("path").toString());
        topUpDocument.setType(TOPUP_RECEIPT);
        topUpDocument.setLanguageCode(languageCode);
        topUpDocumentRP.save(topUpDocument);
        //======= Send Notification
        this.sendNotificationTopUp(httpServletRequest, transaction);
    }
}
