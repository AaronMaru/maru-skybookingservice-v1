package com.skybooking.skypointservice.v1_0_0.service.account;

import com.skybooking.skypointservice.constant.ResponseConstant;
import com.skybooking.skypointservice.constant.UserTypeConstant;
import com.skybooking.skypointservice.httpstatus.BadRequestException;
import com.skybooking.skypointservice.v1_0_0.client.stakeholder.model.response.BasicAccountInfoRS;
import com.skybooking.skypointservice.v1_0_0.client.stakeholder.model.response.UserReferenceRS;
import com.skybooking.skypointservice.v1_0_0.helper.AccountHelper;
import com.skybooking.skypointservice.v1_0_0.io.entity.account.AccountEntity;
import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.account.AccountNQ;
import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.account.AccountSummaryTO;
import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.account.TopBalanceTO;
import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.account.TopEarningTO;
import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.transaction.AccountTransactionTO;
import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.transaction.OfflineTopUpTransactionDetailTO;
import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.transaction.TransactionNQ;
import com.skybooking.skypointservice.v1_0_0.io.repository.account.AccountRP;
import com.skybooking.skypointservice.v1_0_0.service.BaseServiceIP;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.account.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountIP extends BaseServiceIP implements AccountSV {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private AccountRP accountRP;

    @Autowired
    private AccountHelper accountHelper;

    @Autowired
    private AccountNQ accountNQ;

    @Autowired
    private TransactionNQ transactionNQ;


    @Override
    public StructureRS getBalance() {
        try {
            BalanceRS balanceRS = new BalanceRS();

            UserReferenceRS userReferenceRS = accountHelper.getUserReference(httpServletRequest);
            String userCode = userReferenceRS.getUserCode();
            String userType = userReferenceRS.getType().toUpperCase();

            AccountEntity accountEntity = accountRP.findAccountEntityByUserCodeAndType(userCode, userType)
                    .orElse(new AccountEntity("Blue"));

            BeanUtils.copyProperties(accountEntity, balanceRS);

            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, balanceRS);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Unexpected error.", null);
        }
    }

    @Override
    public StructureRS backendDashboard() {
        try {
            BackendDashBoardRS backendDashBoardRS = new BackendDashBoardRS();
            AccountSummaryTO accountSummaryTO = accountNQ.getAccountSummary();
            List<OfflineTopUpTransactionDetailTO> offlineTopUTransactionList = transactionNQ
                    .getRecentOfflineTopUp();
            List<TopBalanceTO> topBalanceTOList = accountNQ.getTopBalance();
            List<TopEarningTO> topEarningTOList = accountNQ.getTopEarning();

            AccountSummary accountSummary = new AccountSummary();
            BeanUtils.copyProperties(accountSummaryTO, accountSummary);

            TopBalance topBalance;
            List<TopBalance> topBalancesList = new ArrayList<>();
            for (TopBalanceTO topBalanceTO : topBalanceTOList) {
                topBalance = new TopBalance();
                BeanUtils.copyProperties(topBalanceTO, topBalance);
                //======= Get basic account info by userCode
                BasicAccountInfoRS basicAccountInfoRS =
                        accountHelper.getBasicAccountInfo(topBalanceTO.getUserCode(), httpServletRequest);
                topBalance.setUserName(basicAccountInfoRS.getName());
                topBalancesList.add(topBalance);
            }

            TopEarning topEarning;
            List<TopEarning> topEarningList = new ArrayList<>();
            for (TopEarningTO topEarningTO : topEarningTOList) {
                topEarning = new TopEarning();
                BeanUtils.copyProperties(topEarningTO, topEarning);
                //======= Get basic account info by userCode
                BasicAccountInfoRS basicAccountInfoRS =
                        accountHelper.getBasicAccountInfo(topEarningTO.getUserCode(), httpServletRequest);
                topEarning.setUserName(basicAccountInfoRS.getName());
                topEarningList.add(topEarning);
            }

            backendDashBoardRS.setAccountSummary(accountSummary);
            backendDashBoardRS.setTopBalanceList(topBalancesList);
            backendDashBoardRS.setTopEarningList(topEarningList);
            backendDashBoardRS.setRecentOfflineTopUpList(offlineTopUTransactionList);

            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, backendDashBoardRS);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Unexpected error.", null);
        }
    }

    @Override
    public StructureRS backendAccountInfo(String userCode) {
        try {
            BackendAccountInfoRS backendAccountInfoRS = new BackendAccountInfoRS();
            AccountEntity accountEntity = accountRP.findAccountEntityByUserCodeAndType(userCode, UserTypeConstant.SKYOWNER)
                    .orElse(new AccountEntity("Blue"));

            BalanceInfo balanceInfo = new BalanceInfo();
            BeanUtils.copyProperties(accountEntity, balanceInfo);

            List<AccountTransactionTO> accountTransactionTOList = transactionNQ.getAccountRecentTransactionByAccount(userCode);

            backendAccountInfoRS.setAccountRecentTransactionList(accountTransactionTOList);
            backendAccountInfoRS.setBalanceInfo(balanceInfo);

            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, backendAccountInfoRS);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Unexpected error.", null);
        }
    }
}
