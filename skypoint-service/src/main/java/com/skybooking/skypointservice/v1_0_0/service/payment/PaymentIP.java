package com.skybooking.skypointservice.v1_0_0.service.payment;

import com.skybooking.skypointservice.constant.ResponseConstant;
import com.skybooking.skypointservice.constant.TransactionStatusConstant;
import com.skybooking.skypointservice.constant.TransactionTypeConstant;
import com.skybooking.skypointservice.constant.UserTypeConstant;
import com.skybooking.skypointservice.httpstatus.BadRequestException;
import com.skybooking.skypointservice.util.ValidateKey;
import com.skybooking.skypointservice.v1_0_0.client.action.NotificationAction;
import com.skybooking.skypointservice.v1_0_0.client.event.action.EventEmailAction;
import com.skybooking.skypointservice.v1_0_0.client.event.action.EventNotificationAction;
import com.skybooking.skypointservice.v1_0_0.client.event.model.requset.NotificationRQ;
import com.skybooking.skypointservice.v1_0_0.client.event.model.requset.SkyPointEarnedRQ;
import com.skybooking.skypointservice.v1_0_0.client.event.model.requset.SkyPointRedeemRQ;
import com.skybooking.skypointservice.v1_0_0.client.event.model.requset.TopUpNotificationRQ;
import com.skybooking.skypointservice.v1_0_0.client.stakeholder.model.response.UserReferenceRS;
import com.skybooking.skypointservice.v1_0_0.helper.AccountHelper;
import com.skybooking.skypointservice.v1_0_0.helper.SkyPointTransactionHelper;
import com.skybooking.skypointservice.v1_0_0.helper.TransactionHelper;
import com.skybooking.skypointservice.v1_0_0.io.entity.account.AccountEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.config.ConfigUpgradeLevelEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.skyPointManagement.SkyOwnerLimitPointEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionValueEntity;
import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.skyPointTransaction.TransactionAmountNQ;
import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.skyPointTransaction.TransactionAmountTO;
import com.skybooking.skypointservice.v1_0_0.io.repository.account.AccountRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.config.ConfigUpgradeLevelRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.skyPointManagement.SkyOwnerLimitPointRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.transaction.TransactionRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.transaction.TransactionValueRP;
import com.skybooking.skypointservice.v1_0_0.service.BaseServiceIP;
import com.skybooking.skypointservice.v1_0_0.service.upgradeLevel.UpgradeLevelSV;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.payment.PaymentRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.payment.PaymentRS;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.payment.RedeemPointRS;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class PaymentIP extends BaseServiceIP implements PaymentSV {
    @Autowired
    Environment environment;
    @Autowired
    private AccountRP accountRP;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private ConfigUpgradeLevelRP configUpgradeLevelRP;
    @Autowired
    private TransactionRP transactionRP;
    @Autowired
    private TransactionValueRP transactionValueRP;
    @Autowired
    private UpgradeLevelSV upgradeLevelSV;
    @Autowired
    private SkyOwnerLimitPointRP skyOwnerLimitPointRP;
    @Autowired
    private TransactionAmountNQ transactionAmountNQ;
    @Autowired
    private AccountHelper accountHelper;
    @Autowired
    private SkyPointTransactionHelper skyPointTransactionHelper;
    @Autowired
    private NotificationAction notificationAction;
    @Autowired
    private TransactionHelper transactionHelper;

    @Autowired
    private EventEmailAction eventEmailAction;

    @Autowired
    private EventNotificationAction eventNotificationAction;

    @Override
    @Transactional
    public StructureRS earning(PaymentRQ paymentRQ) {
        try {
            //======= Validate key
            List<String> key = Arrays.asList("amount", "transactionFor", "contactInfo", "referenceCode");
            ValidateKey.validateKey(paymentRQ, key);
            UserReferenceRS userReferenceRS = accountHelper.getUserReference(httpServletRequest);

            //======= Find account
            AccountEntity accountEntity = accountRP
                    .findAccountEntityByUserCodeAndType(userReferenceRS.getUserCode(), userReferenceRS.getType())
                    .orElse(new AccountEntity());

            ConfigUpgradeLevelEntity configUpgradeLevelEntity = configUpgradeLevelRP.getRecordByFromValueAndToValue(
                    accountEntity.getSavedPoint(),
                    userReferenceRS.getType()
            );

            BigDecimal earningAmount = paymentRQ.getAmount().multiply(configUpgradeLevelEntity.getRate());
            BigDecimal currentEarningAmount = earningAmount.add(accountEntity.getEarning());
            BigDecimal currentBalance = accountEntity.getBalance().add(earningAmount);

            accountEntity.setType(userReferenceRS.getType());
            accountEntity.setUserCode(userReferenceRS.getUserCode());
            accountEntity.setEarning(currentEarningAmount);
            accountEntity.setBalance(currentBalance);
            accountEntity.setSavedPoint(accountEntity.getSavedPoint().add(earningAmount));

            //======== Get upgrade level config
            ConfigUpgradeLevelEntity configUpgradeLevel = configUpgradeLevelRP.getRecordByFromValueAndToValue(
                    accountEntity.getSavedPoint(),
                    userReferenceRS.getType()
            );

            accountEntity.setLevelName(configUpgradeLevel.getLevelName());
            AccountEntity account = accountRP.save(accountEntity);

            //========= Save transaction
            TransactionEntity transactionEntity = new TransactionEntity();
            transactionEntity.setCode(transactionHelper.generateTransactionCode());
            transactionEntity.setAccountId(account.getId());
            transactionEntity.setTransactionFor(paymentRQ.getTransactionFor());
            transactionEntity.setReferenceCode(paymentRQ.getReferenceCode());
            transactionEntity.setAmount(paymentRQ.getAmount());
            transactionEntity.setPaidAmount(paymentRQ.getAmount());
            transactionEntity.setPaymentMethod(paymentRQ.getPaymentMethod());
            transactionEntity.setCreatedBy(userReferenceRS.getStakeholderUserId().toString());
            transactionEntity.setStakeholderUserId(userReferenceRS.getStakeholderUserId());
            transactionEntity.setStakeholderCompanyId(userReferenceRS.getStakeholderCompanyId());
            transactionEntity.setStatus(TransactionStatusConstant.SUCCESS);
            TransactionEntity transaction = transactionRP.save(transactionEntity);

            //======== Save transactionValue
            TransactionValueEntity transactionValueEntity = new TransactionValueEntity();
            transactionValueEntity.setTransactionId(transaction.getId());
            transactionValueEntity.setAmount(paymentRQ.getAmount());
            transactionValueEntity.setExtraRate(configUpgradeLevelEntity.getRate());
            transactionValueEntity.setEarningAmount(earningAmount);
            transactionValueEntity.setTransactionTypeCode(TransactionTypeConstant.EARNING);
            transactionValueRP.save(transactionValueEntity);

            upgradeLevelSV.save(account.getId(), configUpgradeLevel);

            //========= Save skyPoint transaction for skyStaff
            skyPointTransactionHelper.saveSkyPointTransaction(userReferenceRS.getType(),
                    userReferenceRS.getStakeholderCompanyId(),
                    userReferenceRS.getStakeholderUserId(), transaction);

            //======== Send mail
            SkyPointEarnedRQ skyPointEarnedRQ = new SkyPointEarnedRQ();
            skyPointEarnedRQ.setAmount(transaction.getAmount());
            skyPointEarnedRQ.setTransactionCode(paymentRQ.getReferenceCode());
            skyPointEarnedRQ.setEmail(paymentRQ.getContactInfo().getEmail());
            skyPointEarnedRQ.setFullName(paymentRQ.getContactInfo().getName());
            eventEmailAction.earnedEmail(skyPointEarnedRQ);

            //======= Send Notification
            this.sendNotification(transaction, "EARNED_POINT", TransactionTypeConstant.EARNING);

            //======== Date response
            PaymentRS paymentRS = new PaymentRS();
            BeanUtils.copyProperties(accountEntity, paymentRS);
            paymentRS.setEarning(earningAmount);

            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, paymentRS);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Unexpected error.", null);
        }

    }

    @Override
    public StructureRS redeemCheck(PaymentRQ paymentRQ) {
        try {
            //======== Get user reference
            UserReferenceRS userReferenceRS = accountHelper.getUserReference(httpServletRequest);

            //======= Validate user, balance, limit amount for staff
            AccountEntity account = this.validate(paymentRQ, userReferenceRS);

            //======= Return response
            return this.redeemPointResponse(account, paymentRQ.getAmount(), null);
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
    public StructureRS redeemConfirm(PaymentRQ paymentRQ) {
        try {
            //======== Get user reference
            UserReferenceRS userReferenceRS = accountHelper.getUserReference(httpServletRequest);
            AccountEntity account = this.validate(paymentRQ, userReferenceRS);

            //========= Redeem skyPoint from account
            account.setWithdrawal(account.getWithdrawal().add(paymentRQ.getAmount()));
            account.setBalance(account.getBalance().subtract(paymentRQ.getAmount()));
            account.setUpdatedAt(new Date());
            account = accountRP.save(account);

            //========= Save transaction
            TransactionEntity transaction = new TransactionEntity();
            transaction.setCode(transactionHelper.generateTransactionCode());
            transaction.setAmount(paymentRQ.getAmount());
            transaction.setPaidAmount(paymentRQ.getAmount());
            transaction.setAccountId(account.getId());
            transaction.setTransactionFor(paymentRQ.getTransactionFor());
            transaction.setReferenceCode(paymentRQ.getReferenceCode());
            transaction.setPaymentMethod("POINT");
            transaction.setStakeholderUserId(userReferenceRS.getStakeholderUserId());
            transaction.setStakeholderCompanyId(userReferenceRS.getStakeholderCompanyId());
            transaction.setStatus(TransactionStatusConstant.SUCCESS);
            transaction.setCreatedBy(userReferenceRS.getStakeholderUserId().toString());
            transaction.setCreatedAt(new Date());
            transaction = transactionRP.save(transaction);

            //========= Save transaction value
            TransactionValueEntity transactionValue = new TransactionValueEntity();
            transactionValue.setAmount(paymentRQ.getAmount());
            transactionValue.setTransactionId(transaction.getId());
            transactionValue.setEarningAmount(BigDecimal.valueOf(0));
            transactionValue.setExtraRate((BigDecimal.valueOf(0)));
            transactionValue.setCreatedAt(new Date());
            transactionValue.setTransactionTypeCode(TransactionTypeConstant.WITHDRAWAL);
            transactionValueRP.save(transactionValue);

            //========= Save skyPoint transaction for skyStaff
            skyPointTransactionHelper.saveSkyPointTransaction(userReferenceRS.getType(),
                    userReferenceRS.getStakeholderCompanyId(),
                    userReferenceRS.getStakeholderUserId(), transaction);

            //======= Send mail and notification
            SkyPointRedeemRQ skyPointRedeemRQ = new SkyPointRedeemRQ();
            skyPointRedeemRQ.setAmount(transaction.getAmount());
            skyPointRedeemRQ.setEmail(paymentRQ.getContactInfo().getEmail());
            skyPointRedeemRQ.setFullName(paymentRQ.getContactInfo().getName());
            skyPointRedeemRQ.setTransactionCode(paymentRQ.getReferenceCode());
            eventEmailAction.redeemEmail(skyPointRedeemRQ);

            //======= Send Notification
            this.sendNotification(transaction, "REDEEM_POINT", TransactionTypeConstant.WITHDRAWAL);

            //========= Response
            return this.redeemPointResponse(account, transaction.getAmount(), transaction.getCode());
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Unexpected error.", null);
        }
    }

    private AccountEntity validate(PaymentRQ paymentRQ, UserReferenceRS userReferenceRS) {
        //======= Validate key
        List<String> key = Arrays.asList("amount", "transactionFor", "contactInfo", "referenceCode");
        ValidateKey.validateKey(paymentRQ, key);

        //======= Check limitation of skyStaff
        if (userReferenceRS.getType().equalsIgnoreCase(UserTypeConstant.SKYOWNER)) {
            if (!userReferenceRS.getUserRole().equalsIgnoreCase("admin")) {
                SkyOwnerLimitPointEntity pointLimit = skyOwnerLimitPointRP.findByStakeholderUserIdAndStatus(
                        userReferenceRS.getStakeholderUserId(), true);

                if (pointLimit == null) {
                    throw new BadRequestException("Please config limit point for this staff first.", null);
                }
                //========= Get all transaction amount for skyStaff used per today
                TransactionAmountTO transactionAmountTO = transactionAmountNQ.getAmountRedeemForCurrentDateByAccount(
                        userReferenceRS.getStakeholderUserId(), userReferenceRS.getStakeholderCompanyId())
                        .orElse(new TransactionAmountTO());
                if (transactionAmountTO.getAmount() == null) {
                    transactionAmountTO.setAmount(BigDecimal.valueOf(0));
                }

                if (pointLimit.getValue().compareTo(transactionAmountTO.getAmount()) <= 0) {
                    throw new BadRequestException("You can't perform over limitation amount per day.", null);
                }
            }
        }

        AccountEntity account = accountRP.findAccountEntityByUserCodeAndType(
                userReferenceRS.getUserCode(), userReferenceRS.getType())
                .orElse(null);
        //====== Check account
        if (account == null) {
            throw new BadRequestException("This account has no point.", null);
        }

        if (account.getBalance().compareTo(paymentRQ.getAmount()) < 0) {
            throw new BadRequestException("Your balance is not enough", null);
        }

        return account;
    }

    private StructureRS redeemPointResponse(AccountEntity account, BigDecimal amount, String transactionCode) {
        RedeemPointRS redeemPointRS = new RedeemPointRS();
        BeanUtils.copyProperties(account, redeemPointRS);
        redeemPointRS.setRedeem(amount);
        redeemPointRS.setTransactionCode(transactionCode);
        return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, redeemPointRS);
    }

    private void sendNotification(TransactionEntity transaction, String type, String transactionTypeCode) {
        NotificationRQ notificationRQ = new NotificationRQ();
        TransactionValueEntity transactionValue = transactionValueRP.findByTransactionIdAndTransactionTypeCode(transaction.getId(),
                transactionTypeCode);
        notificationRQ.setBookingId(transactionValue.getId());
        notificationRQ.setType(type);
        eventNotificationAction.sendNotification(notificationRQ);
    }
}
