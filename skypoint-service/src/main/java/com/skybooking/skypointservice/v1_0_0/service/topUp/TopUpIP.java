package com.skybooking.skypointservice.v1_0_0.service.topUp;

import com.skybooking.skypointservice.constant.*;
import com.skybooking.skypointservice.httpstatus.BadRequestException;
import com.skybooking.skypointservice.httpstatus.InternalServerError;
import com.skybooking.skypointservice.util.HeaderDataUtil;
import com.skybooking.skypointservice.util.AmountFormatUtil;
import com.skybooking.skypointservice.util.ValidateKeyUtil;
import com.skybooking.skypointservice.v1_0_0.client.ClientResponse;
import com.skybooking.skypointservice.v1_0_0.client.event.action.EventNotificationAction;
import com.skybooking.skypointservice.v1_0_0.client.event.model.requset.TopUpNotificationRQ;
import com.skybooking.skypointservice.v1_0_0.client.payment.action.PaymentAction;
import com.skybooking.skypointservice.v1_0_0.client.payment.model.requset.PaymentGetUrlPaymentRQ;
import com.skybooking.skypointservice.v1_0_0.client.stakeholder.model.response.BasicCompanyAccountInfoRS;
import com.skybooking.skypointservice.v1_0_0.client.stakeholder.model.response.UserReferenceRS;
import com.skybooking.skypointservice.v1_0_0.helper.*;
import com.skybooking.skypointservice.v1_0_0.io.entity.account.AccountEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.config.ConfigTopUpEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.config.ConfigUpgradeLevelEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.redis.BookingLanguageCached;
import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionContactInfoEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionValueEntity;
import com.skybooking.skypointservice.v1_0_0.io.repository.account.AccountRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.config.ConfigTopUpRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.config.ConfigUpgradeLevelRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.redis.BookingLanguageRedisRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.transaction.TransactionContactInfoRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.transaction.TransactionRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.transaction.TransactionValueRP;
import com.skybooking.skypointservice.v1_0_0.service.BaseServiceIP;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
    private TransactionContactInfoRP transactionContactInfoRP;

    @Autowired
    private EventNotificationAction eventNotificationAction;

    @Autowired
    private TransactionValueRP transactionValueRP;

    @Autowired
    private ConfigUpgradeLevelRP configUpgradeLevelRP;

    @Autowired
    private HeaderDataUtil headerDataUtil;

    @Autowired
    private BookingLanguageRedisRP bookingLanguageRedisRP;

    @Autowired
    private SendMailSMSHelper sendMailSMSHelper;

    @Override
    @Transactional(rollbackFor = {})
    public StructureRS offlineTopUp(HttpServletRequest httpServletRequest, OfflineTopUpRQ offlineTopUpRQ) {
        try {
            String languageCode = headerDataUtil.languageCode(httpServletRequest);
            //========== Validate key
            OfflineTopUpRQ offlineTopUpRQValidate = new OfflineTopUpRQ();
            BeanUtils.copyProperties(offlineTopUpRQ, offlineTopUpRQValidate);
            offlineTopUpRQValidate.setFile(null);
            List<String> keyList = Arrays.asList("amount", "userCode", "referenceCode", "createdBy");
            //ValidateKeyUtil.validateMultipartFile(offlineTopUpRQ.getFile(), "file");
            ValidateKeyUtil.validateKey(offlineTopUpRQValidate, keyList);

            //========= Validate amount not negative
            ValidateKeyUtil.validateAmountNotNegative(offlineTopUpRQ.getAmount());

            //========== Validate user
            BasicCompanyAccountInfoRS basicCompanyAccountInfo = accountHelper.getBasicCompanyAccountInfo(offlineTopUpRQ.getUserCode());

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
                    new TransactionEntity(), account, offlineTopUpRQ, createdBy, basicCompanyAccountInfo);

            //========= Save file (topUp document)
            topUpHelper.saveFileForTopUpOffline(offlineTopUpRQ.getFile(), transaction);

            //======== Save transactionValue
            BigDecimal extraEarning = transaction.getAmount().multiply(configTopUp.getValue());
            topUpHelper.saveTransactionValueForTopUp(transaction, configTopUp, extraEarning);

            //======== Save top up contact info
            TransactionContactInfoEntity transactionContactInfo = new TransactionContactInfoEntity();
            BeanUtils.copyProperties(basicCompanyAccountInfo, transactionContactInfo);
            transactionContactInfo.setTransactionId(transaction.getId());
            transactionContactInfo.setPhoneCode("");
            transactionContactInfoRP.save(transactionContactInfo);

            //======= Hit api to get basic account info
            BasicCompanyAccountInfoRS basicCompanyAccountInfoRS = new BasicCompanyAccountInfoRS();
            BeanUtils.copyProperties(basicCompanyAccountInfo, basicCompanyAccountInfoRS);
            basicCompanyAccountInfoRS.setStakeholderUserId(basicCompanyAccountInfo.getStakeholderUserId());
            basicCompanyAccountInfoRS.setStakeholderCompanyId(basicCompanyAccountInfo.getStakeholderCompanyId());

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
    @Transactional(rollbackFor = {})
    public StructureRS confirmOfflineTopUp(HttpServletRequest httpServletRequest, ConfirmOfflineTopUpRQ confirmOfflineTopUpRQ) {
        try {
            //======== Validate key request
            List<String> keyList = Arrays.asList("transactionCode", "status");
            ValidateKeyUtil.validateKey(confirmOfflineTopUpRQ, keyList);

            //======= Validate status
            this.validateStatus(confirmOfflineTopUpRQ.getStatus());

            TransactionValueEntity transactionValue = transactionValueRP.findByCode(confirmOfflineTopUpRQ.getTransactionCode());
            if (transactionValue == null) {
                throw new BadRequestException("transaction_not_found", null);
            }

            TransactionEntity transaction = transactionRP.getOne(transactionValue.getTransactionId());
            if (transaction.getStatus().equalsIgnoreCase(TransactionStatusConstant.SUCCESS)) {
                throw new BadRequestException("transaction_approved", null);
            }

            if (transaction.getStatus().equalsIgnoreCase(TransactionStatusConstant.REJECTED)) {
                throw new BadRequestException("transaction_rejected", null);
            }

            if (confirmOfflineTopUpRQ.getStatus().equalsIgnoreCase(TransactionStatusConstant.APPROVED)) {
                updateTopUp(httpServletRequest, transaction, transactionValue);
            } else if (confirmOfflineTopUpRQ.getStatus().equalsIgnoreCase(TransactionStatusConstant.REJECTED)){
                transaction.setStatus(TransactionStatusConstant.REJECTED);
                transactionRP.save(transaction);
            }

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
            BigDecimal topUpSkyPoint = AmountFormatUtil.roundAmount(onlineTopUpRQ.getAmount());
            BigDecimal earnSkyPoint = AmountFormatUtil.roundAmount(onlineTopUpRQ.getAmount().multiply(configTopUp.getValue()));
            BigDecimal totalSkyPoint = AmountFormatUtil.roundAmount(topUpSkyPoint.add(earnSkyPoint));

            PreTopUpRS preTopUpRS = new PreTopUpRS();
            preTopUpRS.setTopUpSkyPoint(topUpSkyPoint);
            preTopUpRS.setEarnSkyPoint(earnSkyPoint);
            preTopUpRS.setTotalSkyPoint(totalSkyPoint);
            preTopUpRS.setAmountPayable(AmountFormatUtil.roundAmount(onlineTopUpRQ.getAmount()));
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
            List<String> keyList = Arrays.asList("amount", "paymentMethodCode");
            ValidateKeyUtil.validateKey(onlineTopUpRQ, keyList);

            //========= Validate amount not negative
            ValidateKeyUtil.validateAmountNotNegative(onlineTopUpRQ.getAmount());

            //======== Get userType
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
            if (dataRS == null) {
                throw new BadRequestException("payment_method_not_valid", null);
            }
            BigDecimal percentage = new BigDecimal(dataRS.get("percentage").toString());
            BigDecimal fee = onlineTopUpRQ.getAmount().multiply(percentage.divide(new BigDecimal(100)));
            String paymentMethod = String.valueOf(dataRS.get("method"));

            //======== Create transaction
            TransactionEntity transaction = transactionHelper.saveTransactionOnlineTopUp(new TransactionEntity(),
                    account, onlineTopUpRQ, fee, paymentMethod, stakeholderUserId, stakeholderCompanyId);

            //======== Save top up contact info
            TransactionContactInfoEntity transactionContactInfo = new TransactionContactInfoEntity();
            BeanUtils.copyProperties(userReferenceRS, transactionContactInfo);
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
            ClientResponse paymentUrlData = paymentAction.getPaymentUrl(httpServletRequest, paymentGetUrlPaymentRQ);

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
            } else if (postOnlineTopUpRQ.getPaymentStatus().equalsIgnoreCase("failed")) {
                transaction.setStatus(TransactionStatusConstant.FAILED);
                transactionRP.save(transaction);

                TransactionContactInfoEntity transactionContactInfo =
                        transactionContactInfoRP.findByTransactionId(transaction.getId());
                sendMailSMSHelper.sendMailOrSmsTopUpFailed(httpServletRequest, transactionValue, transactionContactInfo);
            } else {
                throw new BadRequestException("paymentStatus_invalid", null);
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

    private void sendNotificationTopUp(HttpServletRequest httpServletRequest, TransactionEntity transaction) {
        TopUpNotificationRQ topUpNotificationRQ = new TopUpNotificationRQ();
        TransactionValueEntity transactionValue = transactionValueRP.findByTransactionIdAndTransactionTypeCode(transaction.getId(),
                TransactionTypeConstant.TOP_UP);
        topUpNotificationRQ.setTransactionCode(transactionValue.getCode());
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
        sendMailSMSHelper.sendMailOrSmsTopUp(transaction, transactionValue, transactionContactInfo,
                configTopUp, languageCode, httpServletRequest);

        //======= Send Notification
        this.sendNotificationTopUp(httpServletRequest, transaction);
    }

    private void validateStatus(String status) {
        List<String> statuses = new ArrayList<>();
        statuses.add(TransactionStatusConstant.APPROVED);
        statuses.add(TransactionStatusConstant.REJECTED);
        if (!statuses.contains(status)) {
            throw new BadRequestException("status_not_valid", null);
        }
    }
}
