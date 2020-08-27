package com.skybooking.skypointservice.v1_0_0.service.transaction;

import com.skybooking.skypointservice.constant.ResponseConstant;
import com.skybooking.skypointservice.httpstatus.BadRequestException;
import com.skybooking.skypointservice.v1_0_0.client.stakeholder.model.response.BasicAccountInfoRS;
import com.skybooking.skypointservice.v1_0_0.helper.AccountHelper;
import com.skybooking.skypointservice.v1_0_0.io.entity.account.AccountEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionContactInfoEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionEntity;
import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.transaction.OfflineTopUpTransactionDetailTO;
import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.transaction.OnlineTopUpTransactionDetailTO;
import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.transaction.TransactionNQ;
import com.skybooking.skypointservice.v1_0_0.io.repository.account.AccountRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.transaction.TransactionContactInfoRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.transaction.TransactionRP;
import com.skybooking.skypointservice.v1_0_0.service.BaseServiceIP;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.transaction.TransactionRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.transaction.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class TransactionIP extends BaseServiceIP implements TransactionSV {
    @Autowired
    private TransactionRP transactionRP;

    @Autowired
    private TransactionNQ transactionNQ;

    @Autowired
    private AccountRP accountRP;

    @Autowired
    private AccountHelper accountHelper;

    @Autowired
    private TransactionContactInfoRP transactionContactInfoRP;

    @Override
    public StructureRS getTransactionDetail(TransactionRQ transactionRQ) {
        try {
            TransactionEntity transaction = transactionRP.findByCode(transactionRQ.getCode());
            if (transaction == null) {
                throw new BadRequestException("Transaction code not found", null);
            }

            //========= Get transaction contact info
            TransactionContactInfoEntity transactionContactInfo = transactionContactInfoRP.findByTransactionId(transaction.getId());

            TransactionRS transactionRS = new TransactionRS();
            BeanUtils.copyProperties(transaction, transactionRS);
            transactionRS.setName(transactionContactInfo.getName());
            transactionRS.setEmail(transactionContactInfo.getEmail());
            transactionRS.setPhoneNumber(transactionContactInfo.getPhoneCode() + " "
                    + transactionContactInfo.getPhoneNumber());
            transactionRS.setDescription("Top up");

            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, transactionRS);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Unexpected error.", null);
        }
    }

    @Override
    public StructureRS getRecentOfflineTopUp() {
        try {
            List<OfflineTopUpTransactionDetailTO> offlineTopUpTransactionDetailTO =
                    transactionNQ.getRecentOfflineTopUp();

            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, offlineTopUpTransactionDetailTO);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Unexpected error.", null);
        }
    }

    @Override
    public StructureRS getOfflineTopUpTransactionDetail(HttpServletRequest httpServletRequest, TransactionRQ transactionRQ) {
        try {
            TransactionEntity transaction = transactionRP.findByCode(transactionRQ.getCode());
            if (transaction == null) {
                throw new BadRequestException("Transaction code not found", null);
            }

            AccountEntity account = accountRP.getOne(transaction.getAccountId());

            //======= Hit api to get basic account info
            BasicAccountInfoRS basicAccountInfo = accountHelper.getBasicAccountInfo(account.getUserCode(), httpServletRequest);

            AccountInfo accountInfo = new AccountInfo();
            BeanUtils.copyProperties(account, accountInfo);

            TopUpInfo topUpInfo = new TopUpInfo();
            BeanUtils.copyProperties(transaction, topUpInfo);
            topUpInfo.setTransactionCode(transaction.getCode());

            OfflineTopUpTransactionDetailRS offlineTopUpTransactionDetailRS = new OfflineTopUpTransactionDetailRS();
            offlineTopUpTransactionDetailRS.setBasicAccountInfo(basicAccountInfo);
            offlineTopUpTransactionDetailRS.setAccountInfo(accountInfo);
            offlineTopUpTransactionDetailRS.setTopUpInfo(topUpInfo);

            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, offlineTopUpTransactionDetailRS);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Unexpected error.", null);
        }
    }

    @Override
    public StructureRS searchOfflineTransaction(String searchValue) {
        try {
            List<OfflineTopUpTransactionDetailTO> topUpTransactionDetailTOList =
                    transactionNQ.searchOfflineTopUpTransaction(searchValue);

            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, topUpTransactionDetailTOList);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Unexpected error.", null);
        }
    }

    @Override
    public StructureRS getOnlineTopUpTransactionDetail(TransactionRQ transactionRQ) {
        try {
            OnlineTopUpTransactionDetailTO onlineTopUpTransactionDetailTO = transactionNQ.
                    getOnlineTopUpTransactionDetail(transactionRQ.getCode());

            OnlineTopUpTransactionDetailRS onlineTopUpTransactionDetailRS = new OnlineTopUpTransactionDetailRS();
            BeanUtils.copyProperties(onlineTopUpTransactionDetailTO, onlineTopUpTransactionDetailRS);
            onlineTopUpTransactionDetailRS.setTransactionDate(onlineTopUpTransactionDetailTO.getTransactionDate());

            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, onlineTopUpTransactionDetailRS);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Unexpected error.", null);
        }
    }

}
