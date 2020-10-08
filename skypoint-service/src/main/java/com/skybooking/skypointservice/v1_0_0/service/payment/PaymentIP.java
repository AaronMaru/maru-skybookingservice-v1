package com.skybooking.skypointservice.v1_0_0.service.payment;

import com.skybooking.skypointservice.constant.*;
import com.skybooking.skypointservice.httpstatus.BadRequestException;
import com.skybooking.skypointservice.httpstatus.InternalServerError;
import com.skybooking.skypointservice.util.HeaderDataUtil;
import com.skybooking.skypointservice.util.ValidateKeyUtil;
import com.skybooking.skypointservice.v1_0_0.client.event.action.EventEmailAction;
import com.skybooking.skypointservice.v1_0_0.client.event.action.EventNotificationAction;
import com.skybooking.skypointservice.v1_0_0.client.event.model.requset.NotificationRQ;
import com.skybooking.skypointservice.v1_0_0.client.event.model.requset.SkyPointEarnedRQ;
import com.skybooking.skypointservice.v1_0_0.client.event.model.requset.SkyPointRedeemRQ;
import com.skybooking.skypointservice.v1_0_0.client.stakeholder.model.response.UserReferenceRS;
import com.skybooking.skypointservice.v1_0_0.helper.AccountHelper;
import com.skybooking.skypointservice.v1_0_0.helper.SkyPointTransactionHelper;
import com.skybooking.skypointservice.v1_0_0.helper.TransactionValueHelper;
import com.skybooking.skypointservice.v1_0_0.io.entity.account.AccountEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.config.ConfigUpgradeLevelEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.pointLimit.SkyOwnerLimitPointEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionValueEntity;
import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.skyPointTransaction.TransactionAmountNQ;
import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.skyPointTransaction.TransactionAmountTO;
import com.skybooking.skypointservice.v1_0_0.io.repository.account.AccountRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.config.ConfigUpgradeLevelRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.pointLimit.SkyOwnerLimitPointRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.transaction.TransactionRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.transaction.TransactionValueRP;
import com.skybooking.skypointservice.v1_0_0.service.BaseServiceIP;
import com.skybooking.skypointservice.v1_0_0.service.upgradeLevel.UpgradeLevelSV;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.ContactInfo;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.payment.PaymentRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.payment.PaymentRS;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.payment.RedeemPointRS;
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
    private TransactionValueHelper transactionValueHelper;

    @Autowired
    private EventEmailAction eventEmailAction;

    @Autowired
    private EventNotificationAction eventNotificationAction;

    @Autowired
    private HeaderDataUtil headerDataUtil;

    @Override
    public StructureRS earningCheck(HttpServletRequest httpServletRequest, PaymentRQ paymentRQ) {
        UserReferenceRS userReferenceRS = accountHelper.getUserReference(httpServletRequest);
        List<String> key = Arrays.asList("amount");
        ValidateKeyUtil.validateKey(paymentRQ, key);

        //======= Validate amount not negative
        ValidateKeyUtil.validateAmountNotNegative(paymentRQ.getAmount());

        //======= Find account
        AccountEntity account = accountRP
                .findAccountEntityByUserCode(userReferenceRS.getUserCode())
                .orElse(new AccountEntity());

        ConfigUpgradeLevelEntity configUpgradeLevel = configUpgradeLevelRP.getRecordByFromValueAndTypeAndToValue(
                account.getSavedPoint(),
                userReferenceRS.getType()
        );

        //======== Data response
        return this.earnPointResponse(account, configUpgradeLevel, paymentRQ.getAmount().multiply(configUpgradeLevel.getRate()));
    }

    @Override
    @Transactional
    public StructureRS earningConfirm(HttpServletRequest httpServletRequest, PaymentRQ paymentRQ) {
        try {
            String languageCode = headerDataUtil.languageCode(httpServletRequest);
            //======= Validate key
            List<String> key = Arrays.asList("amount", "transactionFor", "contactInfo", "referenceCode");
            ValidateKeyUtil.validateKey(paymentRQ, key);

            //======== Get userType
            ContactInfo contactInfo = paymentRQ.getContactInfo();
            List<String> contactInfoKey = Arrays.asList("email", "name", "phoneCode", "phoneNumber");
            ValidateKeyUtil.validateKey(contactInfo, contactInfoKey);

            //======= Validate amount not negative
            ValidateKeyUtil.validateAmountNotNegative(paymentRQ.getAmount());

            //======= Validate name & phoneNumber
            ValidateKeyUtil.validateLetter(contactInfo.getName());
            ValidateKeyUtil.validateNumber(contactInfo.getPhoneNumber());

            String transactionTypeCode = this.getTransactionTypeCode(paymentRQ.getTransactionFor(), "earned");
            UserReferenceRS userReferenceRS = accountHelper.getUserReference(httpServletRequest);

            //======= Find account
            AccountEntity account = accountRP
                    .findAccountEntityByUserCode(userReferenceRS.getUserCode())
                    .orElse(new AccountEntity());

            ConfigUpgradeLevelEntity configUpgradeLevelEntity = configUpgradeLevelRP.getRecordByFromValueAndTypeAndToValue(
                    account.getSavedPoint(),
                    userReferenceRS.getType()
            );

            BigDecimal earningAmount = paymentRQ.getAmount().multiply(configUpgradeLevelEntity.getRate());
            BigDecimal currentEarningAmount = earningAmount.add(account.getEarning());
            BigDecimal currentBalance = account.getBalance().add(earningAmount);

            account.setType(userReferenceRS.getType());
            account.setUserCode(userReferenceRS.getUserCode());
            account.setEarning(currentEarningAmount);
            account.setBalance(currentBalance);
            account.setSavedPoint(account.getSavedPoint().add(earningAmount));

            //======== Get upgrade level config
            ConfigUpgradeLevelEntity configUpgradeLevel = configUpgradeLevelRP.getRecordByFromValueAndToValueAndTypeAndLanguageCode(
                    account.getSavedPoint(),
                    userReferenceRS.getType(),
                    languageCode
            );

            account.setLevelCode(configUpgradeLevel.getCode());
            account = accountRP.save(account);

            //========= Save transaction
            TransactionEntity transactionEntity = new TransactionEntity();
            transactionEntity.setAccountId(account.getId());
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
            transactionValueEntity.setCode(transactionValueHelper.generateTransactionValueCode());
            transactionValueEntity.setTransactionId(transaction.getId());
            transactionValueEntity.setAmount(paymentRQ.getAmount());
            transactionValueEntity.setExtraRate(configUpgradeLevelEntity.getRate());
            transactionValueEntity.setEarningAmount(earningAmount);
            transactionValueEntity.setTransactionTypeCode(transactionTypeCode);
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
            eventEmailAction.earnedEmail(httpServletRequest, skyPointEarnedRQ);

            //======= Send Notification
            this.sendNotification(httpServletRequest, transaction, "EARNED_POINT", transactionTypeCode);

            //======== Data response
            return this.earnPointResponse(account, configUpgradeLevel, earningAmount);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("unexpected_error", null);
        }

    }

    @Override
    public StructureRS redeemCheck(HttpServletRequest httpServletRequest, PaymentRQ paymentRQ) {
        try {
            //======== Get user reference
            UserReferenceRS userReferenceRS = accountHelper.getUserReference(httpServletRequest);

            //======= Validate user, balance, limit amount for staff
            AccountEntity account = this.validate(httpServletRequest, paymentRQ, userReferenceRS);

            //======= Return response
            return this.redeemPointResponse(httpServletRequest, account, paymentRQ.getAmount(), null);
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
    public StructureRS redeemConfirm(HttpServletRequest httpServletRequest, PaymentRQ paymentRQ) {
        try {
            //======== Get user reference
            UserReferenceRS userReferenceRS = accountHelper.getUserReference(httpServletRequest);
            AccountEntity account = this.validate(httpServletRequest, paymentRQ, userReferenceRS);
            String transactionTypeCode = getTransactionTypeCode(paymentRQ.getTransactionFor(), "redeemed");

            //========= Redeem skyPoint from account
            account.setWithdrawal(account.getWithdrawal().add(paymentRQ.getAmount()));
            account.setBalance(account.getBalance().subtract(paymentRQ.getAmount()));
            account.setUpdatedAt(new Date());
            account = accountRP.save(account);

            //========= Save transaction
            TransactionEntity transaction = new TransactionEntity();
            transaction.setAmount(paymentRQ.getAmount());
            transaction.setPaidAmount(paymentRQ.getAmount());
            transaction.setAccountId(account.getId());
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
            transactionValue.setCode(transactionValueHelper.generateTransactionValueCode());
            transactionValue.setAmount(paymentRQ.getAmount());
            transactionValue.setTransactionId(transaction.getId());
            transactionValue.setEarningAmount(BigDecimal.valueOf(0));
            transactionValue.setExtraRate((BigDecimal.valueOf(0)));
            transactionValue.setCreatedAt(new Date());
            transactionValue.setTransactionTypeCode(transactionTypeCode);
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
            eventEmailAction.redeemEmail(httpServletRequest, skyPointRedeemRQ);

            //======= Send Notification
            this.sendNotification(httpServletRequest, transaction, "REDEEM_POINT", transactionTypeCode);

            //========= Response
            return this.redeemPointResponse(httpServletRequest, account, transaction.getAmount(), transactionValue.getCode());
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("unexpected_error", null);
        }
    }

    private AccountEntity validate(HttpServletRequest httpServletRequest, PaymentRQ paymentRQ, UserReferenceRS userReferenceRS) {
        //======= Validate key
        List<String> key = Arrays.asList("amount", "transactionFor", "contactInfo", "referenceCode");
        ValidateKeyUtil.validateKey(paymentRQ, key);

        //======== Get userType
        ContactInfo contactInfo = paymentRQ.getContactInfo();
        List<String> contactInfoKey = Arrays.asList("email", "name", "phoneCode", "phoneNumber");
        ValidateKeyUtil.validateKey(contactInfo, contactInfoKey);

        //======= Validate amount not negative
        ValidateKeyUtil.validateAmountNotNegative(paymentRQ.getAmount());

        //======= Validate name & phoneNumber
        ValidateKeyUtil.validateLetter(contactInfo.getName());
        ValidateKeyUtil.validateNumber(contactInfo.getPhoneNumber());

        //======= Check limitation of skyStaff
        if (userReferenceRS.getType().equalsIgnoreCase(UserTypeConstant.SKYOWNER)) {
            if (!userReferenceRS.getUserRole().equalsIgnoreCase("admin")) {
                SkyOwnerLimitPointEntity pointLimit = skyOwnerLimitPointRP.findByStakeholderUserIdAndStatus(
                        userReferenceRS.getStakeholderUserId(), true);

                if (pointLimit == null) {
                    throw new BadRequestException("limit_point_not_config", null);
                }
                //========= Get all transaction amount for skyStaff used per today
                TransactionAmountTO transactionAmountTO = transactionAmountNQ.getAmountRedeemForCurrentDateByAccount(
                        userReferenceRS.getStakeholderUserId(), userReferenceRS.getStakeholderCompanyId())
                        .orElse(new TransactionAmountTO());
                if (transactionAmountTO.getAmount() == null) {
                    transactionAmountTO.setAmount(BigDecimal.valueOf(0));
                }

                if (pointLimit.getValue().compareTo(transactionAmountTO.getAmount()) <= 0) {
                    throw new BadRequestException("redeem_over_limitation", null);
                }
            }
        }

        AccountEntity account = accountRP.findAccountEntityByUserCode(
                userReferenceRS.getUserCode())
                .orElse(null);
        //====== Check account
        if (account == null) {
            throw new BadRequestException("account_not_found", null);
        }

        if (account.getBalance().compareTo(paymentRQ.getAmount()) < 0) {
            throw new BadRequestException("balance_not_enough", null);
        }

        return account;
    }

    private StructureRS earnPointResponse(AccountEntity account, ConfigUpgradeLevelEntity configUpgradeLevel, BigDecimal earningAmount) {
        PaymentRS paymentRS = new PaymentRS();
        BeanUtils.copyProperties(account, paymentRS);
        paymentRS.setLevelName(configUpgradeLevel.getLevelName());
        paymentRS.setEarning(earningAmount);
        return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, paymentRS);
    }

    private StructureRS redeemPointResponse(HttpServletRequest httpServletRequest, AccountEntity account,
                                            BigDecimal amount, String transactionCode) {
        //======== Get level Name
        String languageCode = headerDataUtil.languageCode(httpServletRequest);
        ConfigUpgradeLevelEntity configUpgradeLevel = configUpgradeLevelRP.getRecordByFromValueAndToValueAndTypeAndLanguageCode(
                account.getSavedPoint(),
                account.getType(),
                languageCode
        );
        RedeemPointRS redeemPointRS = new RedeemPointRS();
        BeanUtils.copyProperties(account, redeemPointRS);
        redeemPointRS.setRedeem(amount);
        redeemPointRS.setLevelName(configUpgradeLevel.getLevelName());
        redeemPointRS.setTransactionCode(transactionCode);
        return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, redeemPointRS);
    }

    private void sendNotification(HttpServletRequest httpServletRequest, TransactionEntity transaction, String type, String transactionTypeCode) {
        NotificationRQ notificationRQ = new NotificationRQ();
        TransactionValueEntity transactionValue = transactionValueRP.findByTransactionIdAndTransactionTypeCode(transaction.getId(),
                transactionTypeCode);
        notificationRQ.setBookingId(transactionValue.getCode());
        notificationRQ.setType(type);
        eventNotificationAction.sendNotification(httpServletRequest, notificationRQ);
    }

    private String getTransactionTypeCode(String transactionFor, String transactionType) {
        String transactionTypeCode = "";
        if (transactionFor.equalsIgnoreCase(TransactionForConstant.FLIGHT)) {
            if (transactionType.equalsIgnoreCase("earned")) {
                transactionTypeCode = TransactionTypeConstant.EARNED_FLIGHT;
            } else if (transactionType.equalsIgnoreCase("redeemed")) {
                transactionTypeCode = TransactionTypeConstant.REDEEMED_FLIGHT;
            }
        } else if (transactionFor.equalsIgnoreCase(TransactionForConstant.HOTEL)) {
            if (transactionType.equalsIgnoreCase("earned")) {
                transactionTypeCode = TransactionTypeConstant.EARNED_HOTEL;
            } else if (transactionType.equalsIgnoreCase("redeemed")) {
                transactionTypeCode = TransactionTypeConstant.REDEEMED_HOTEL;
            }
        } else {
            throw new BadRequestException("transactionFor_not_valid", null);
        }
        return transactionTypeCode;
    }
}
