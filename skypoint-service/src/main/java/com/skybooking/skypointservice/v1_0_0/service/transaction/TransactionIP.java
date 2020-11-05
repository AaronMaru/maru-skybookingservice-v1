package com.skybooking.skypointservice.v1_0_0.service.transaction;

import com.skybooking.skypointservice.constant.ResponseConstant;
import com.skybooking.skypointservice.httpstatus.BadRequestException;
import com.skybooking.skypointservice.httpstatus.InternalServerError;
import com.skybooking.skypointservice.util.HeaderDataUtil;
import com.skybooking.skypointservice.util.AmountFormatUtil;
import com.skybooking.skypointservice.v1_0_0.client.stakeholder.model.response.BasicCompanyAccountInfoRS;
import com.skybooking.skypointservice.v1_0_0.helper.AccountHelper;
import com.skybooking.skypointservice.v1_0_0.helper.TopUpHelper;
import com.skybooking.skypointservice.v1_0_0.io.entity.account.AccountEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.config.ConfigUpgradeLevelEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionContactInfoEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionValueEntity;
import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.transaction.*;
import com.skybooking.skypointservice.v1_0_0.io.repository.account.AccountRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.config.ConfigUpgradeLevelRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.transaction.TransactionContactInfoRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.transaction.TransactionRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.transaction.TransactionValueRP;
import com.skybooking.skypointservice.v1_0_0.service.BaseServiceIP;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.transaction.TransactionRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.PagingRS;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.transaction.*;
import com.skybooking.skypointservice.v1_0_0.util.datetime.DateTimeBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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

    @Autowired
    private TransactionValueRP transactionValueRP;

    @Autowired
    private ConfigUpgradeLevelRP configUpgradeLevelRP;

    @Autowired
    private HeaderDataUtil headerDataUtil;

    @Autowired
    private TopUpHelper topUpHelper;

    @Autowired
    private DateTimeBean dateTimeBean;

    @Override
    public StructureRS getTransactionDetail(TransactionRQ transactionRQ) {
        try {
            TransactionValueEntity transactionValue = transactionValueRP.findByCode(transactionRQ.getCode());
            if (transactionValue == null) {
                throw new BadRequestException("transaction_not_found", null);
            }

            //========== Get transaction
            TransactionEntity transaction = transactionRP.getOne(transactionValue.getTransactionId());

            //========= Get transaction contact info
            TransactionContactInfoEntity transactionContactInfo = transactionContactInfoRP.findByTransactionId(transaction.getId());
            if (transactionContactInfo == null) {
                throw new BadRequestException("transaction_contactInfo_not_found", null);
            }

            TransactionRS transactionRS = new TransactionRS();
            BeanUtils.copyProperties(transaction, transactionRS);
            transactionRS.setAmount(AmountFormatUtil.roundAmount(transaction.getAmount()));
            transactionRS.setCode(transactionValue.getCode());
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
            throw new InternalServerError("unexpected_error", null);
        }
    }

    @Override
    public StructureRS getRecentTopUp() {
        try {
            List<TopUpTransactionDetailTO> topUpTransactionDetailTO =
                    transactionNQ.getRecentTopUp();

            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS,
                    formatOfflineTopUpTransactionList(topUpTransactionDetailTO));
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("unexpected_error", null);
        }
    }

    @Override
    public StructureRS getRecentTransaction(HttpServletRequest httpServletRequest, String userCode, Integer size, Integer page) {
        try {
            String languageCode = headerDataUtil.languageCodeExist(httpServletRequest);
            List<AccountTransactionTO> recentTransactionList;
            PagingRS pagesRS = new PagingRS();
            if (size != 0) {
                Integer offset = (page - 1) * size;
                recentTransactionList = transactionNQ.getRecentTransactionByAccount(userCode, languageCode, size, offset);

                Long total = (long) transactionNQ.getAllRecentTransactionByAccount(userCode, languageCode).size();

                pagesRS.setPage(page);
                pagesRS.setSize(size);
                pagesRS.setTotals(total);

            } else {
                recentTransactionList = transactionNQ.getAllRecentTransactionByAccount(userCode, languageCode);
            }

            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, formatRecentTransactionList(recentTransactionList), pagesRS);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("unexpected_error", null);
        }
    }

    @Override
    public StructureRS getOfflineTopUpTransactionDetail(HttpServletRequest httpServletRequest, String transactionCode) {
        try {
            String languageCode = headerDataUtil.languageCodeExist(httpServletRequest);
            TransactionValueEntity transactionValue = transactionValueRP.findByCode(transactionCode);
            if (transactionValue == null) {
                throw new BadRequestException("transaction_not_found", null);
            }

            //========== Get transaction
            TransactionEntity transaction = transactionRP.getOne(transactionValue.getTransactionId());
            AccountEntity account = accountRP.getOne(transaction.getAccountId());

            //======= Hit api to get basic account info
            BasicCompanyAccountInfoRS basicAccountInfo = accountHelper.getBasicCompanyAccountInfo(
                    account.getUserCode());

            AccountInfo accountInfo = new AccountInfo();
            BeanUtils.copyProperties(account, accountInfo);
            //====== Get config level upgrade
            ConfigUpgradeLevelEntity configUpgradeLevel = configUpgradeLevelRP.getRecordByFromValueAndToValueAndTypeAndLanguageCode(
                    account.getSavedPoint(),
                    account.getType(),
                    languageCode
            );
            accountInfo.setLevelName(configUpgradeLevel.getLevelName());

            //======== Set topUpInfo response
            TopUpInfo topUpInfo = topUpHelper.topUpInfo(transaction, transactionValue);

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
            throw new InternalServerError("unexpected_error", null);
        }
    }

    @Override
    public StructureRS searchOfflineTransaction(String searchValue) {
        try {
            List<TopUpTransactionDetailTO> topUpTransactionDetailTOList = new ArrayList<>();
            if (!searchValue.equalsIgnoreCase("")) {
                topUpTransactionDetailTOList = transactionNQ.searchOfflineTopUpTransaction(searchValue);
            }

            SearchOfflineTopUpTransactionRS searchOfflineTopUpTransactionRS = new SearchOfflineTopUpTransactionRS();
            searchOfflineTopUpTransactionRS.setOfflineTopUpTransactionList(
                    formatOfflineTopUpTransactionList(topUpTransactionDetailTOList));
            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, searchOfflineTopUpTransactionRS);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("unexpected_error", null);
        }
    }

    @Override
    public StructureRS getOnlineTopUpTransactionDetail(String transactionCode) {
        try {
            OnlineTopUpTransactionDetailTO onlineTopUpTransactionDetailTO = transactionNQ.
                    getOnlineTopUpTransactionDetail(transactionCode).orElse(null);

            if (onlineTopUpTransactionDetailTO == null) {
                throw new BadRequestException("transaction_not_found", null);
            }

            OnlineTopUpTransactionDetailRS onlineTopUpTransactionDetailRS = new OnlineTopUpTransactionDetailRS();
            BeanUtils.copyProperties(new OnlineTopUpTransactionDetailTO(onlineTopUpTransactionDetailTO),
                    onlineTopUpTransactionDetailRS);
            onlineTopUpTransactionDetailRS.setTransactionDate(dateTimeBean.convertDateTime(
                    onlineTopUpTransactionDetailTO.getTransactionDate()));

            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, onlineTopUpTransactionDetailRS);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("unexpected_error", null);
        }
    }

    @Override
    public StructureRS getPendingOfflineTopUpList(Integer page, Integer size) {
        try {
            Integer offset = (page - 1) * size;
            List<PendingOfflineTopUpTransactionTO> pendingOfflineTransactionList = transactionNQ
                    .getPendingOfflineTopUpList(size, offset);

            Long total = (long) transactionNQ.countAllPendingOfflineTopUpTransaction().size();

            PagingRS pagingRS = new PagingRS();
            pagingRS.setPage(page);
            pagingRS.setSize(size);
            pagingRS.setTotals(total);

            PendingOfflineTopUpTransactionRS pendingOfflineTopUpTransactionRS = new PendingOfflineTopUpTransactionRS();
            pendingOfflineTopUpTransactionRS.setPendingOfflineTransactionList(
                    formatPendingOfflineTopUpTransactionList(pendingOfflineTransactionList));
            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, pendingOfflineTopUpTransactionRS, pagingRS);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("unexpected_error", null);
        }
    }

    private List<PendingOfflineTopUpTransactionTO> formatPendingOfflineTopUpTransactionList(
            List<PendingOfflineTopUpTransactionTO> pendingOfflineTopUpTransactionTOList) {
        List<PendingOfflineTopUpTransactionTO> pendingOfflineTopUpTransactionList = new ArrayList<>();
        if (pendingOfflineTopUpTransactionTOList.size() > 0) {
            pendingOfflineTopUpTransactionTOList.forEach(pendingOfflineTopUpTransactionTO -> {
                pendingOfflineTopUpTransactionList.add(new PendingOfflineTopUpTransactionTO(pendingOfflineTopUpTransactionTO));
            });
        }

        return pendingOfflineTopUpTransactionList;
    }

    private List<AccountTransactionTO> formatRecentTransactionList(List<AccountTransactionTO> accountTransactionTOList) {
        List<AccountTransactionTO> recentTransactionList = new ArrayList<>();
        if (accountTransactionTOList.size() > 0) {
            accountTransactionTOList.forEach(accountTransactionTO -> {
                recentTransactionList.add(new AccountTransactionTO(accountTransactionTO));
            });
        }
        return recentTransactionList;
    }

    private List<TopUpTransactionDetailTO> formatOfflineTopUpTransactionList(
            List<TopUpTransactionDetailTO> topUpTransactionDetailTOList) {
        List<TopUpTransactionDetailTO> offlineTopUpTransactionDetailList = new ArrayList<>();
        if (topUpTransactionDetailTOList.size() > 0) {
            topUpTransactionDetailTOList.forEach(offlineTopUpTransactionDetailTO -> {
                offlineTopUpTransactionDetailList.add(new TopUpTransactionDetailTO(offlineTopUpTransactionDetailTO));
            });
        }

        return offlineTopUpTransactionDetailList;
    }
}
