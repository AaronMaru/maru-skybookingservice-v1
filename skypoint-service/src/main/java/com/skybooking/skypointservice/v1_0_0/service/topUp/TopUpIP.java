package com.skybooking.skypointservice.v1_0_0.service.topUp;

import com.skybooking.skypointservice.constant.*;
import com.skybooking.skypointservice.httpstatus.BadRequestException;
import com.skybooking.skypointservice.util.ValidateKey;
import com.skybooking.skypointservice.v1_0_0.client.ClientResponse;
import com.skybooking.skypointservice.v1_0_0.client.event.action.EventEmailAction;
import com.skybooking.skypointservice.v1_0_0.client.event.action.EventNotificationAction;
import com.skybooking.skypointservice.v1_0_0.client.event.model.requset.SkyPointTopUpRQ;
import com.skybooking.skypointservice.v1_0_0.client.event.model.requset.TopUpNotificationRQ;
import com.skybooking.skypointservice.v1_0_0.client.payment.action.PaymentAction;
import com.skybooking.skypointservice.v1_0_0.client.payment.model.requset.PaymentGetUrlPaymentRQ;
import com.skybooking.skypointservice.v1_0_0.client.stakeholder.model.response.BasicAccountInfoRS;
import com.skybooking.skypointservice.v1_0_0.client.stakeholder.model.response.UserReferenceRS;
import com.skybooking.skypointservice.v1_0_0.helper.AccountHelper;
import com.skybooking.skypointservice.v1_0_0.helper.SkyPointTransactionHelper;
import com.skybooking.skypointservice.v1_0_0.helper.TopUpHelper;
import com.skybooking.skypointservice.v1_0_0.helper.TransactionHelper;
import com.skybooking.skypointservice.v1_0_0.io.entity.account.AccountEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.config.ConfigTopUpEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.topUp.TopUpDocumentEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionContactInfoEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionValueEntity;
import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.transaction.OfflineTopUpTransactionDetailTO;
import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.transaction.TransactionNQ;
import com.skybooking.skypointservice.v1_0_0.io.repository.account.AccountRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.config.ConfigTopUpRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.transaction.TransactionContactInfoRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.transaction.TransactionRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.transaction.TransactionValueRP;
import com.skybooking.skypointservice.v1_0_0.service.BaseServiceIP;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.ContactInfo;
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

@Service
public class TopUpIP extends BaseServiceIP implements TopUpSV {
    @Autowired
    private HttpServletRequest httpServletRequest;
    
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
    private TransactionNQ transactionNQ;

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

    @Override
    @Transactional(rollbackFor = {})
    public StructureRS offlineTopUp(OfflineTopUpRQ offlineTopUpRQ) {
        try {
            //========== Validate key
            OfflineTopUpRQ offlineTopUpRQValidate = new OfflineTopUpRQ();
            BeanUtils.copyProperties(offlineTopUpRQ, offlineTopUpRQValidate);
            offlineTopUpRQValidate.setFile(null);
            List<String> keyList = Arrays.asList("amount", "userCode", "referenceCode","createdBy",
                    "stakeholderCompanyId", "stakeholderUserId", "email", "name", "phoneNumber");
            ValidateKey.validateMultipartFile(offlineTopUpRQ.getFile(), "file");
            ValidateKey.validateKey(offlineTopUpRQValidate, keyList);

            //========== Get value from jwt header
            String createdBy = offlineTopUpRQ.getCreatedBy();
            String userType = UserTypeConstant.SKYOWNER;

            //============ Get config extra rate by userType
            ConfigTopUpEntity configTopUp = configTopUpRP.findByTypeAndTopupKeyAndStatus(
                    userType, ConfigTopUpTypeConstant.EXTRA_RATE, true);

            //======== Create account if not exist
            AccountEntity account = topUpHelper.createAccountIfNotExist(offlineTopUpRQ.getUserCode(), userType);

            //========= Save transaction
            TransactionEntity transaction = transactionHelper.saveTransactionOfflineTopUp(
                    new TransactionEntity(), account, offlineTopUpRQ, createdBy);

            //========= Save file (topUp document)
            topUpHelper.saveFileForTopUpOffline(offlineTopUpRQ.getFile(), transaction, new TopUpDocumentEntity());

            //======== Save transactionValue && update account balance
            topUpHelper.saveTransactionValueAndUpgradeAccount(transaction, account, configTopUp, userType);

            //======== Save sky point transaction
            skyPointTransactionHelper.saveSkyPointTransaction(userType, transaction.getStakeholderCompanyId(),
                    transaction.getStakeholderUserId(), transaction);

            //======= Hit api to get basic account info
            BasicAccountInfoRS basicAccountInfoRS = new BasicAccountInfoRS();
            BeanUtils.copyProperties(offlineTopUpRQ, basicAccountInfoRS);

            //======= Send mail and notification
            this.sendMailTopUp(transaction, offlineTopUpRQ.getEmail(), offlineTopUpRQ.getName(),
                    transaction.getAmount().multiply(configTopUp.getValue()), offlineTopUpRQ.getPhoneNumber() );

            //======= Send Notification
            this.sendNotificationTopUp(transaction);

            //======== Return response
            AccountInfo accountInfo = new AccountInfo();
            BeanUtils.copyProperties(account, accountInfo);

            TopUpInfo topUpInfo = new TopUpInfo();
            BeanUtils.copyProperties(transaction, topUpInfo);
            topUpInfo.setTransactionCode(transaction.getCode());

            List<OfflineTopUpTransactionDetailTO> offlineTopUpTransactionDetailTOList =
                    transactionNQ.getRecentOfflineTopUp();

            OfflineTopUpRS offlineTopUpRS = new OfflineTopUpRS();
            offlineTopUpRS.setBasicAccountInfoRS(basicAccountInfoRS);
            offlineTopUpRS.setAccountInfo(accountInfo);
            offlineTopUpRS.setTopUpInfo(topUpInfo);
            offlineTopUpRS.setRecentOfflineTopUpTransactionList(offlineTopUpTransactionDetailTOList);

            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, offlineTopUpRS);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Unexpected error.", null);
        }
    }

    @Override
    public StructureRS preTopUp(OnlineTopUpRQ onlineTopUpRQ) {
        try {
            StructureRS structureRS = new StructureRS();
            //======== Validate key request
            List<String> keyList = Arrays.asList("amount");
            ValidateKey.validateKey(onlineTopUpRQ, keyList);

            //======== userReferenceRS
            UserReferenceRS userReferenceRS = accountHelper.getUserReference(httpServletRequest);
            String userType = userReferenceRS.getType();
            String userCode = userReferenceRS.getUserCode();

            AccountEntity account = accountRP.findAccountEntityByUserCodeAndType(userCode, userType)
                    .orElse(new AccountEntity(0));

            //======== Validate amount for ONLINE topUp
            topUpHelper.validateForOnlineTopUp(onlineTopUpRQ.getAmount(), userType, account.getId());

            //============ Get config extra rate by userType
            ConfigTopUpEntity configTopUp = configTopUpRP.findByTypeAndTopupKeyAndStatus(userType,
                    ConfigTopUpTypeConstant.EXTRA_RATE, true);

            //======== Return response
            BigDecimal topUpSkyPoint = onlineTopUpRQ.getAmount();
            BigDecimal earnSkyPoint = onlineTopUpRQ.getAmount().multiply(configTopUp.getValue());
            BigDecimal totalSkyPoint = topUpSkyPoint.add(earnSkyPoint);

            PreTopUpRS preTopUpRS = new PreTopUpRS();
            preTopUpRS.setTopUpSkyPoint(topUpSkyPoint);
            preTopUpRS.setEarnSkyPoint(earnSkyPoint);
            preTopUpRS.setTotalSkyPoint(totalSkyPoint);
            structureRS.setData(preTopUpRS);
            structureRS.setMessage(ResponseConstant.SUCCESS);
            return structureRS;
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Unexpected error.", null);
        }
    }

    @Override
    @Transactional(rollbackFor = {})
    public StructureRS proceedTopUp(OnlineTopUpRQ onlineTopUpRQ) {
        try {
            StructureRS structureRS = new StructureRS();
            //======== Validate key request
            List<String> keyList = Arrays.asList("amount", "paymentMethodCode", "contactInfo");
            ValidateKey.validateKey(onlineTopUpRQ, keyList);

            //======== Get userType
            ContactInfo contactInfo = onlineTopUpRQ.getContactInfo();
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

            //======== Hit api to get paymentUrl
            PaymentGetUrlPaymentRQ paymentGetUrlPaymentRQ = new PaymentGetUrlPaymentRQ();
            paymentGetUrlPaymentRQ.setBookingCode(transaction.getCode());
            paymentGetUrlPaymentRQ.setPaymentCode(onlineTopUpRQ.getPaymentMethodCode());
            paymentGetUrlPaymentRQ.setProductType("POINT");
            ClientResponse paymentUrlData = paymentAction.getPaymentUrl(paymentGetUrlPaymentRQ);
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
            throw new BadRequestException("Unexpected error.", null);
        }
    }

    @Override
    @Transactional(rollbackFor = {})
    public StructureRS postTopUp(PostOnlineTopUpRQ postOnlineTopUpRQ) {
        try {
            StructureRS structureRS = new StructureRS();
            //======== Validate key request
            List<String> keyList = Arrays.asList("paymentStatus", "transactionCode");
            ValidateKey.validateKey(postOnlineTopUpRQ, keyList);

            //======== Update transaction status
            TransactionEntity transaction = transactionRP.findByCode(postOnlineTopUpRQ.getTransactionCode());
            if (transaction == null) {
                throw new BadRequestException("Transaction code not found", null);
            }

            if (!transaction.getStatus().equalsIgnoreCase(TransactionStatusConstant.PRE_TOP_UP)) {
                throw new BadRequestException("Transaction has been updated previously.", null);
            }

            if (postOnlineTopUpRQ.getPaymentStatus().equalsIgnoreCase("success")) {
                transaction.setStatus(TransactionStatusConstant.SUCCESS);
                transactionRP.save(transaction);

                //======== Get account by accountId
                AccountEntity account = accountRP.getOne(transaction.getAccountId());

                //======== Get userType
                String userType = account.getType();

                //============ Get config extra rate by userType
                ConfigTopUpEntity configTopUp = configTopUpRP.findByTypeAndTopupKeyAndStatus(userType,
                        ConfigTopUpTypeConstant.EXTRA_RATE, true);

                //======== Save transactionValue && update account balance
                topUpHelper.saveTransactionValueAndUpgradeAccount(transaction, account, configTopUp, userType);

                //======== Save sky point transaction
                skyPointTransactionHelper.saveSkyPointTransaction(userType, transaction.getStakeholderCompanyId(),
                        transaction.getStakeholderUserId(), transaction);

                //======= Send mail
                TransactionContactInfoEntity transactionContactInfo = transactionContactInfoRP.findByTransactionId(transaction.getId());
                this.sendMailTopUp(transaction, transactionContactInfo.getEmail(), transactionContactInfo.getName(),
                        transaction.getAmount().multiply(configTopUp.getValue()),
                        transactionContactInfo.getPhoneCode() + transactionContactInfo.getPhoneNumber());
                
                //======= Send Notification
                this.sendNotificationTopUp(transaction);

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
            throw new BadRequestException("Unexpected error.", null);
        }
    }

    private void sendMailTopUp(TransactionEntity transaction, String email, String fullName, BigDecimal earnAmount,
                               String phone) {
        //======= Send mail and
        SkyPointTopUpRQ skyPointTopUpRQ = new SkyPointTopUpRQ();
        skyPointTopUpRQ.setAmount(transaction.getAmount());
        skyPointTopUpRQ.setEmail(email);
        skyPointTopUpRQ.setFullName(fullName);
        skyPointTopUpRQ.setEarnAmount(earnAmount);
        skyPointTopUpRQ.setPhone(phone);
        skyPointTopUpRQ.setTransactionId(transaction.getCode());
        skyPointTopUpRQ.setTransactionDate(dateTimeBean.convertDateTime(transaction.getCreatedAt()));
        eventEmailAction.topUpEmail(skyPointTopUpRQ);
    }

    private void sendNotificationTopUp(TransactionEntity transaction) {
        TopUpNotificationRQ topUpNotificationRQ = new TopUpNotificationRQ();
        TransactionValueEntity transactionValue = transactionValueRP.findByTransactionIdAndTransactionTypeCode(transaction.getId(),
                TransactionTypeConstant.TOP_UP);
        topUpNotificationRQ.setBookingId(transactionValue.getId());
        topUpNotificationRQ.setStakeholderCompanyId(transaction.getStakeholderCompanyId());
        topUpNotificationRQ.setStakeholderUserId(transaction.getStakeholderUserId());
        eventNotificationAction.topUpNotification(topUpNotificationRQ);
    }
}
