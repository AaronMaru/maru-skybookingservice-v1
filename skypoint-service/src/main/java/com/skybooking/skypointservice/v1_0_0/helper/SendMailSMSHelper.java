package com.skybooking.skypointservice.v1_0_0.helper;

import com.skybooking.skypointservice.util.AmountFormatUtil;
import com.skybooking.skypointservice.v1_0_0.client.ClientResponse;
import com.skybooking.skypointservice.v1_0_0.client.event.action.EventEmailAction;
import com.skybooking.skypointservice.v1_0_0.client.event.action.EventSmsAction;
import com.skybooking.skypointservice.v1_0_0.client.event.model.requset.SkyPointMailRQ;
import com.skybooking.skypointservice.v1_0_0.client.event.model.requset.SkyPointTopUpFailedRQ;
import com.skybooking.skypointservice.v1_0_0.client.event.model.requset.SkyPointTopUpSuccessRQ;
import com.skybooking.skypointservice.v1_0_0.client.event.model.requset.sms.*;
import com.skybooking.skypointservice.v1_0_0.client.stakeholder.model.response.UserAccountInfoRS;
import com.skybooking.skypointservice.v1_0_0.client.stakeholder.model.response.UserReferenceRS;
import com.skybooking.skypointservice.v1_0_0.io.entity.config.ConfigTopUpEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.topUp.TopUpDocumentEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionContactInfoEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionValueEntity;
import com.skybooking.skypointservice.v1_0_0.io.repository.topUp.TopUpDocumentRP;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.payment.PaymentRQ;
import com.skybooking.skypointservice.v1_0_0.util.datetime.DateTimeBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

import static com.skybooking.skypointservice.constant.TopUpTypeConstant.TOPUP_RECEIPT;

@Component
public class SendMailSMSHelper {
    @Autowired
    private EventEmailAction eventEmailAction;

    @Autowired
    private TopUpDocumentRP topUpDocumentRP;

    @Autowired
    private DateTimeBean dateTimeBean;

    @Autowired
    private EventSmsAction eventSmsAction;

    public void sendMailOrSmsEarnRedeem(UserReferenceRS userReferenceRS, TransactionEntity transaction, PaymentRQ paymentRQ,
                              HttpServletRequest httpServletRequest, String type, String transactionFor,
                              BigDecimal earnedAmount) {
        if (!userReferenceRS.getEmail().equalsIgnoreCase("")) {
            //======= Send mail
            SkyPointMailRQ skyPointMailRQ = new SkyPointMailRQ();
            skyPointMailRQ.setAmount(AmountFormatUtil.roundAmount(transaction.getAmount()));
            skyPointMailRQ.setEmail(userReferenceRS.getEmail());
            skyPointMailRQ.setFullName(userReferenceRS.getName());
            skyPointMailRQ.setTransactionCode(paymentRQ.getReferenceCode());
            if (type.equalsIgnoreCase("redeemed")) {
                eventEmailAction.redeemEmail(httpServletRequest, skyPointMailRQ);
            } else if (type.equalsIgnoreCase("earned")) {
                eventEmailAction.earnedEmail(httpServletRequest, skyPointMailRQ);
            }

        } else if (!userReferenceRS.getPhoneNumber().equalsIgnoreCase("")) {
            //======= Send sms
            if (type.equalsIgnoreCase("redeemed")) {
                SkyPointRedeemSmsRQ skyPointRedeemSmsRQ = new SkyPointRedeemSmsRQ();
                skyPointRedeemSmsRQ.setBookingId(transaction.getReferenceCode());
                skyPointRedeemSmsRQ.setPhone((userReferenceRS.getPhoneCode() + userReferenceRS.getPhoneNumber()).trim());
                skyPointRedeemSmsRQ.setTransactionFor(transactionFor);
                eventSmsAction.redeemedSms(httpServletRequest, skyPointRedeemSmsRQ);
            } else if (type.equalsIgnoreCase("earned")) {
                SkyPointEarnSmsRQ skyPointEarnSmsRQ = new SkyPointEarnSmsRQ();
                skyPointEarnSmsRQ.setAmount(AmountFormatUtil.roundAmount(earnedAmount));
                skyPointEarnSmsRQ.setBookingId(transaction.getReferenceCode());
                skyPointEarnSmsRQ.setPhone((userReferenceRS.getPhoneCode() + userReferenceRS.getPhoneNumber()).trim());
                skyPointEarnSmsRQ.setTransactionFor(transactionFor);
                eventSmsAction.earnedSms(httpServletRequest, skyPointEarnSmsRQ);
            }
        }
    }

    public void sendMailOrSmsRefund(UserAccountInfoRS userAccountInfoRS, TransactionEntity transaction, String referenceCode,
                              HttpServletRequest httpServletRequest) {
        if (!userAccountInfoRS.getEmail().equalsIgnoreCase("")) {
            //======= Send mail and notification
            SkyPointMailRQ skyPointMailRQ = new SkyPointMailRQ();
            skyPointMailRQ.setAmount(AmountFormatUtil.roundAmount(transaction.getAmount()));
            skyPointMailRQ.setEmail(userAccountInfoRS.getEmail());
            skyPointMailRQ.setFullName(userAccountInfoRS.getName());
            skyPointMailRQ.setTransactionCode(referenceCode);
            eventEmailAction.refundedEmail(httpServletRequest, skyPointMailRQ);
        } else if (!userAccountInfoRS.getPhone().equalsIgnoreCase("")) {
            //======= Send sms
            SkyPointRefundSmsRQ skyPointRefundSmsRQ = new SkyPointRefundSmsRQ();
            skyPointRefundSmsRQ.setPhone(userAccountInfoRS.getPhone());
            eventSmsAction.refundedSms(httpServletRequest, skyPointRefundSmsRQ);

        }
    }

    public void sendMailOrSmsTopUp(TransactionEntity transaction, TransactionValueEntity transactionValue,
                              TransactionContactInfoEntity transactionContactInfo,
                              ConfigTopUpEntity configTopUp, String languageCode,
                              HttpServletRequest httpServletRequest) {
        //======= Send mail or sms
        ClientResponse s3UploadRS = null;
        if (!transactionContactInfo.getEmail().equalsIgnoreCase("")) {
            s3UploadRS = this.sendMailTopUp(httpServletRequest, transaction, transactionValue,
                    transactionContactInfo.getEmail(), transactionContactInfo.getName(),
                    transaction.getAmount().multiply(configTopUp.getValue()),
                    transactionContactInfo.getPhoneCode() + transactionContactInfo.getPhoneNumber());
        } else if (!transactionContactInfo.getPhoneNumber().equalsIgnoreCase("")) {
            //==== Send sms
            SkyPointTopUpSuccessSmsRQ skyPointTopUpSuccessSmsRQ = new SkyPointTopUpSuccessSmsRQ();
            skyPointTopUpSuccessSmsRQ.setAmount(AmountFormatUtil.roundAmount(transaction.getAmount()));
            skyPointTopUpSuccessSmsRQ.setEarnAmount(AmountFormatUtil.roundAmount(
                    transaction.getAmount().multiply(configTopUp.getValue())));
            skyPointTopUpSuccessSmsRQ.setFullName(transactionContactInfo.getName());
            skyPointTopUpSuccessSmsRQ.setPhone((transactionContactInfo.getPhoneCode() + 
                    transactionContactInfo.getPhoneNumber()).trim());
            skyPointTopUpSuccessSmsRQ.setTransactionDate(dateTimeBean.convertDateTime(transaction.getCreatedAt()));
            skyPointTopUpSuccessSmsRQ.setTransactionId(transactionValue.getCode());
            
            s3UploadRS = eventSmsAction.topUpSuccessSms(httpServletRequest, skyPointTopUpSuccessSmsRQ); 
        }

        if (s3UploadRS != null) {
            Map<String, Object> dataRS = s3UploadRS.getData();
            TopUpDocumentEntity topUpDocument = new TopUpDocumentEntity();
            topUpDocument.setTransactionId(transaction.getId());
            topUpDocument.setFile(dataRS.get("file").toString());
            topUpDocument.setPath(dataRS.get("path").toString());
            topUpDocument.setType(TOPUP_RECEIPT);
            topUpDocument.setLanguageCode(languageCode);
            topUpDocumentRP.save(topUpDocument);
        }
    }

    public void sendMailOrSmsTopUpFailed(HttpServletRequest httpServletRequest, TransactionValueEntity transactionValue,
                                         TransactionContactInfoEntity transactionContactInfo) {
        //======= Send mail or sms
        if (!transactionContactInfo.getEmail().equalsIgnoreCase("")) {
            SkyPointTopUpFailedRQ skyPointTopUpFailedRQ = new SkyPointTopUpFailedRQ();
            skyPointTopUpFailedRQ.setAmount(AmountFormatUtil.roundAmount(transactionValue.getAmount()));
            skyPointTopUpFailedRQ.setEmail(transactionContactInfo.getEmail());
            skyPointTopUpFailedRQ.setFullName(transactionContactInfo.getName());
            eventEmailAction.topUpFailedMail(httpServletRequest, skyPointTopUpFailedRQ);
        } else if (!transactionContactInfo.getPhoneNumber().equalsIgnoreCase("")) {
            //Send sms
            SkyPointTopUpFailedSmsRQ skyPointTopUpFailedSmsRQ = new SkyPointTopUpFailedSmsRQ();
            skyPointTopUpFailedSmsRQ.setPhone(transactionContactInfo.getPhoneCode() +
                    transactionContactInfo.getPhoneNumber().trim());

            eventSmsAction.topUpFailedSms(httpServletRequest, skyPointTopUpFailedSmsRQ);

        }
    }

    private ClientResponse sendMailTopUp(HttpServletRequest httpServletRequest, TransactionEntity transaction,
                                         TransactionValueEntity transactionValue, String email, String fullName, BigDecimal earnAmount,
                                         String phone) {
        //======= Send mail
        SkyPointTopUpSuccessRQ skyPointTopUpSuccessRQ = new SkyPointTopUpSuccessRQ();
        skyPointTopUpSuccessRQ.setAmount(AmountFormatUtil.roundAmount(transactionValue.getAmount()));
        skyPointTopUpSuccessRQ.setEmail(email);
        skyPointTopUpSuccessRQ.setFullName(fullName);
        skyPointTopUpSuccessRQ.setEarnAmount(AmountFormatUtil.roundAmount(earnAmount));
        skyPointTopUpSuccessRQ.setPhone(phone);
        skyPointTopUpSuccessRQ.setTransactionId(transactionValue.getCode());
        skyPointTopUpSuccessRQ.setTransactionDate(dateTimeBean.convertDateTime(transaction.getCreatedAt()));
        return eventEmailAction.topUpEmail(httpServletRequest, skyPointTopUpSuccessRQ);
    }
}
