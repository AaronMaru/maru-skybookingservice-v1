package com.skybooking.skypointservice.v1_0_0.service.refund;

import com.skybooking.skypointservice.constant.ResponseConstant;
import com.skybooking.skypointservice.constant.TransactionStatusConstant;
import com.skybooking.skypointservice.constant.TransactionTypeConstant;
import com.skybooking.skypointservice.httpstatus.BadRequestException;
import com.skybooking.skypointservice.httpstatus.InternalServerError;
import com.skybooking.skypointservice.v1_0_0.client.event.action.EventNotificationAction;
import com.skybooking.skypointservice.v1_0_0.client.event.model.requset.NotificationRQ;
import com.skybooking.skypointservice.v1_0_0.client.stakeholder.model.response.UserAccountInfoRS;
import com.skybooking.skypointservice.v1_0_0.helper.AccountHelper;
import com.skybooking.skypointservice.v1_0_0.helper.SendMailSMSHelper;
import com.skybooking.skypointservice.v1_0_0.helper.SkyPointTransactionHelper;
import com.skybooking.skypointservice.v1_0_0.helper.TransactionValueHelper;
import com.skybooking.skypointservice.v1_0_0.io.entity.account.AccountEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.config.ConfigTopUpEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionValueEntity;
import com.skybooking.skypointservice.v1_0_0.io.repository.account.AccountRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.transaction.TransactionRP;
import com.skybooking.skypointservice.v1_0_0.service.BaseServiceIP;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.refund.RefundRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.refund.RefundRS;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@Service
public class RefundIP extends BaseServiceIP implements RefundSV {
    @Autowired
    private AccountRP accountRP;

    @Autowired
    private TransactionRP transactionRP;

    @Autowired
    private TransactionValueHelper transactionValueHelper;

    @Autowired
    private SkyPointTransactionHelper skyPointTransactionHelper;

    @Autowired
    private EventNotificationAction eventNotificationAction;

    @Autowired
    private AccountHelper accountHelper;

    @Autowired
    private SendMailSMSHelper sendMailOrSMSHelper;

    @Override
    public StructureRS refund(HttpServletRequest httpServletRequest, RefundRQ refundRQ) {

        try {
            AccountEntity account = accountRP.findAccountEntityByUserCode(refundRQ.getUserCode())
                    .orElse(null);

            if (account == null) {
                throw new BadRequestException("account_not_found", null);
            }

            //========== Save transaction
            TransactionEntity transaction = new TransactionEntity();
            BeanUtils.copyProperties(refundRQ, transaction);
            transaction.setAccountId(account.getId());
            transaction.setPaidAmount(refundRQ.getAmount());
            transaction.setStatus(TransactionStatusConstant.SUCCESS);
            transaction = transactionRP.save(transaction);

            //========== Save transactionValue
            TransactionValueEntity transactionValue = transactionValueHelper.saveTransactionValue(transaction,
                    new ConfigTopUpEntity(), new BigDecimal(0), TransactionTypeConstant.REFUNDED);

            //========= Save skyPoint transaction for skyStaff
            skyPointTransactionHelper.saveSkyPointTransaction(account.getType(), refundRQ.getStakeholderCompanyId(),
                    refundRQ.getStakeholderUserId(), transaction);

            //========= Update account balance
            account.setBalance(account.getBalance().add(refundRQ.getAmount()));
            account.setWithdrawal(account.getWithdrawal().subtract(refundRQ.getAmount()));
            account = accountRP.save(account);

            //======= Send mail or SMS
            UserAccountInfoRS userAccountInfoRS = accountHelper.getUserOrCompanyDetailByUserCode(refundRQ.getUserCode());
            sendMailOrSMSHelper.sendMailOrSmsRefund(userAccountInfoRS, transaction, refundRQ.getReferenceCode(), httpServletRequest);

            //========== Send notification
            NotificationRQ notificationRQ = new NotificationRQ();
            notificationRQ.setTransactionCode(transactionValue.getCode());
            notificationRQ.setType("REFUND_POINT");
            eventNotificationAction.sendNotification(httpServletRequest, notificationRQ);

            RefundRS refundRS = new RefundRS();
            BeanUtils.copyProperties(account, refundRS);
            refundRS.setRefundAmount(refundRQ.getAmount());
            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, refundRS);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("unexpected_error", null);
        }
    }

}
