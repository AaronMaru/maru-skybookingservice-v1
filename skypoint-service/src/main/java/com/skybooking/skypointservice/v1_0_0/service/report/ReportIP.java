package com.skybooking.skypointservice.v1_0_0.service.report;

import com.skybooking.skypointservice.constant.ResponseConstant;
import com.skybooking.skypointservice.constant.UserTypeConstant;
import com.skybooking.skypointservice.httpstatus.BadRequestException;
import com.skybooking.skypointservice.httpstatus.InternalServerError;
import com.skybooking.skypointservice.util.HeaderDataUtil;
import com.skybooking.skypointservice.util.AmountFormatUtil;
import com.skybooking.skypointservice.v1_0_0.client.stakeholder.action.StakeholderAction;
import com.skybooking.skypointservice.v1_0_0.client.stakeholder.model.response.ClientResponseUserCompany;
import com.skybooking.skypointservice.v1_0_0.client.stakeholder.model.response.CompanyUserTO;
import com.skybooking.skypointservice.v1_0_0.client.stakeholder.model.response.UserAccountInfoRS;
import com.skybooking.skypointservice.v1_0_0.helper.AccountHelper;
import com.skybooking.skypointservice.v1_0_0.io.entity.account.AccountEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.config.ConfigUpgradeLevelEntity;
import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.report.*;
import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.transaction.TopUpTransactionDetailTO;
import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.transaction.TransactionNQ;
import com.skybooking.skypointservice.v1_0_0.io.repository.account.AccountRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.config.ConfigUpgradeLevelRP;
import com.skybooking.skypointservice.v1_0_0.service.BaseServiceIP;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.account.BalanceInfo;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.report.*;
import com.skybooking.skypointservice.v1_0_0.util.datetime.DateTimeBean;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ReportIP extends BaseServiceIP implements ReportSV {
    @Autowired
    private ReportNQ reportNQ;

    @Autowired
    private TransactionNQ transactionNQ;

    @Autowired
    private AccountHelper accountHelper;

    @Autowired
    private AccountRP accountRP;

    @Autowired
    private ConfigUpgradeLevelRP configUpgradeLevelRP;

    @Autowired
    private StakeholderAction stakeholderAction;

    @Autowired
    private HeaderDataUtil headerDataUtil;

    @Autowired
    private DateTimeBean dateTimeBean;

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    @Override
    public StructureRS backendDashboardReport() {
        try {
            BackendDashBoardReportRS backendDashBoardReportRS = new BackendDashBoardReportRS();
            AccountSummaryTO accountSummaryTO = reportNQ.getAccountSummary();
            List<TopUpTransactionDetailTO> offlineTopUTransactionList = transactionNQ
                    .getRecentTopUp();
            List<TopBalanceTO> topBalanceTOList = reportNQ.getTopBalance();
            List<TopEarningTO> topEarningTOList = reportNQ.getTopEarning();

            AccountSummary accountSummary = new AccountSummary();
            BeanUtils.copyProperties(new AccountSummaryTO(accountSummaryTO), accountSummary);

            TopBalance topBalance;
            List<TopBalance> topBalancesList = new ArrayList<>();
            for (TopBalanceTO topBalanceTO : topBalanceTOList) {
                topBalance = new TopBalance();
                BeanUtils.copyProperties(topBalanceTO, topBalance);
                //======= Get basic account info by userCode
                UserAccountInfoRS userAccountInfoRS =
                        accountHelper.getUserOrCompanyDetailByUserCode(topBalanceTO.getUserCode());
                topBalance.setUserName(userAccountInfoRS.getName());
                topBalance.setThumbnail(userAccountInfoRS.getThumbnail());
                topBalance.setBalance(AmountFormatUtil.roundAmount(topBalanceTO.getBalance()));
                topBalancesList.add(topBalance);
            }

            TopEarning topEarning;
            List<TopEarning> topEarningList = new ArrayList<>();
            for (TopEarningTO topEarningTO : topEarningTOList) {
                topEarning = new TopEarning();
                BeanUtils.copyProperties(topEarningTO, topEarning);
                //======= Get basic account info by userCode
                UserAccountInfoRS userAccountInfoRS =
                        accountHelper.getUserOrCompanyDetailByUserCode(topEarningTO.getUserCode());
                topEarning.setUserName(userAccountInfoRS.getName());
                topEarning.setThumbnail(userAccountInfoRS.getThumbnail());
                topEarning.setSavedPoint(AmountFormatUtil.roundAmount(topEarningTO.getSavedPoint()));
                topEarningList.add(topEarning);
            }

            backendDashBoardReportRS.setAccountSummary(accountSummary);
            backendDashBoardReportRS.setTopBalanceList(topBalancesList);
            backendDashBoardReportRS.setTopEarningList(topEarningList);
            backendDashBoardReportRS.setRecentOfflineTopUpList(formatOfflineTopUTransactionList(offlineTopUTransactionList));

            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, backendDashBoardReportRS);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("unexpected_error", null);
        }
    }

    @Override
    public StructureRS backendAccountInfoReport(HttpServletRequest httpServletRequest, String userCode) {
        try {
            String languageCode = headerDataUtil.languageCodeExist(httpServletRequest);
            BackendAccountInfoReportRS backendAccountInfoReportRS = new BackendAccountInfoReportRS();
            AccountEntity accountEntity = accountRP.findAccountEntityByUserCode(userCode)
                    .orElse(new AccountEntity("LEVEL1"));

            if (userCode.contains("SKYO")) {
                accountEntity.setType(UserTypeConstant.SKYOWNER);
            } else {
                accountEntity.setType(UserTypeConstant.SKYUSER);
            }
            ConfigUpgradeLevelEntity configUpgradeLevel = configUpgradeLevelRP.getRecordByFromValueAndToValueAndTypeAndLanguageCode(
                    accountEntity.getSavedPoint(),
                    accountEntity.getType(),
                    languageCode);
            BalanceInfo balanceInfo = new BalanceInfo();
            BeanUtils.copyProperties(accountEntity, balanceInfo);
            balanceInfo.setLevelName(configUpgradeLevel.getLevelName());

            List<TransactionSummaryReportTO> transactionSummaryReportTOList =
                    reportNQ.transactionSummaryListReportByAccount(userCode);

            backendAccountInfoReportRS.setTransactionSummaryReportList(formatTransactionSummaryReport(transactionSummaryReportTOList));
            backendAccountInfoReportRS.setBalanceInfo(new BalanceInfo(balanceInfo));

            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, backendAccountInfoReportRS);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("unexpected_error", null);
        }
    }

    @Override
    public StructureRS transactionSummaryReport(String starDate, String endDate) {
        try {
            List<TransactionSummaryReportTO> transactionSummaryReportTOList = reportNQ.transactionListSummaryReport(starDate, endDate);
            TransactionSummaryReportTO transactionSummaryReportTO = reportNQ.transactionSummaryReport(starDate, endDate);

            TransactionSummaryReportRS transactionSummaryReportRS = new TransactionSummaryReportRS();
            transactionSummaryReportRS.setTransactionSummaryReportInfo(new TransactionSummaryReportTO(transactionSummaryReportTO));
            transactionSummaryReportRS.setTransactionSummaryReportList(formatTransactionSummaryReport(transactionSummaryReportTOList));
            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, transactionSummaryReportRS);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("unexpected_error", null);
        }
    }

    @Override
    public StructureRS topUpPointSummaryReport(String startDate, String endDate, String userCode) {
        try {
            TopUpPointSummaryReportTO topUpPointSummaryReportTO = reportNQ.topUpPointSummaryReport(
                    startDate, endDate, userCode);
            TopUpPointSummaryReportRS topUpPointSummaryReportRS = new TopUpPointSummaryReportRS();
            BeanUtils.copyProperties(topUpPointSummaryReportTO, topUpPointSummaryReportRS);
            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, topUpPointSummaryReportRS);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("unexpected_error", null);
        }
    }

    @Override
    public StructureRS topUpPointDetailReport(HttpServletRequest httpServletRequest, String startDate, String endDate, String userCode) {
        try {
            String languageCode = headerDataUtil.languageCodeExist(httpServletRequest);
            List<TopUpPointDetailReportTO> topUpPointDetailReportTOList = reportNQ.topUpPointDetailReport(
                    startDate, endDate, languageCode, userCode);

            if (topUpPointDetailReportTOList != null) {

                List<TopUpPointDetailReportTO> distinctUserCode = topUpPointDetailReportTOList.stream()
                        .filter(distinctByKey(TopUpPointDetailReportTO::getUserCode))
                        .collect(Collectors.toList());

                ArrayList<String> keyCode = new ArrayList<>();

                distinctUserCode.forEach(item -> keyCode.add(item.getUserCode()));

                Map<String, CompanyUserTO> companyUserMap = mapCodeUserName(keyCode);

                topUpPointDetailReportTOList.forEach(topUp -> {
                    var found = companyUserMap.get(topUp.getUserCode());
                    if (found != null) {
                        topUp.setUserName(found.getName());
                    }
                });
            }

            TopUpPointDetailReportRS topUpPointDetailReportRS = new TopUpPointDetailReportRS();
            topUpPointDetailReportRS.setTopUpPointDetailReportList(topUpPointDetailReportTOList);
            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, topUpPointDetailReportRS);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("unexpected_error", null);
        }
    }

    @Override
    public StructureRS spentPointSummaryReport(String startDate, String endDate, String userCode) {
        try {
            SpentPointSummaryReportTO spentPointSummaryReportTO = reportNQ.spentPointSummaryReport(
                    startDate, endDate, userCode);

            SpentPointSummaryReportRS spentPointSummaryReportRS = new SpentPointSummaryReportRS();
            BeanUtils.copyProperties(spentPointSummaryReportTO, spentPointSummaryReportRS);
            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, spentPointSummaryReportRS);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("unexpected_error", null);
        }
    }

    @Override
    public StructureRS spentPointDetailReport(HttpServletRequest httpServletRequest, String startDate, String endDate, String userCode) {
        try {
            String languageCode = headerDataUtil.languageCodeExist(httpServletRequest);
            List<SpentPointDetailReportTO> spentPointDetailReportTOList = reportNQ.spentPointDetailReport(
                    startDate, endDate, languageCode, userCode);

            if (spentPointDetailReportTOList != null) {

                List<SpentPointDetailReportTO> distinctUserCode = spentPointDetailReportTOList.stream()
                        .filter(distinctByKey(SpentPointDetailReportTO::getUserCode))
                        .collect(Collectors.toList());

                ArrayList<String> keyCode = new ArrayList<>();

                distinctUserCode.forEach(item -> keyCode.add(item.getUserCode()));

                Map<String, CompanyUserTO> companyUserMap = mapCodeUserName(keyCode);

                spentPointDetailReportTOList.forEach(topUp -> {
                    var found = companyUserMap.get(topUp.getUserCode());
                    if (found != null) {
                        topUp.setUserName(found.getName());
                    }
                });
            }

            SpentPointDetailReportRS spentPointDetailReportRS = new SpentPointDetailReportRS();
            spentPointDetailReportRS.setSpentPointDetailReportList(spentPointDetailReportTOList);
            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, spentPointDetailReportRS);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("unexpected_error", null);
        }
    }

    @Override
    public StructureRS earnedPointSummaryReport(String startDate, String endDate, String userCode) {
        try {
            EarnedPointSummaryReportTO earnedPointSummaryReportTO = reportNQ.earnedPointSummaryReport(
                    startDate, endDate, userCode);

            EarnedPointSummaryReportRS earnedPointSummaryReportRS = new EarnedPointSummaryReportRS();
            BeanUtils.copyProperties(earnedPointSummaryReportTO, earnedPointSummaryReportRS);
            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, earnedPointSummaryReportRS);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("unexpected_error", null);
        }
    }

    @Override
    public StructureRS earnedPointDetailReport(HttpServletRequest httpServletRequest, String startDate, String endDate, String userCode) {
        try {
            String languageCode = headerDataUtil.languageCodeExist(httpServletRequest);
            List<EarnedPointDetailReportTO> earnedPointDetailReportTOList = reportNQ.earnedPointDetailReport(
                    startDate, endDate, languageCode, userCode);

            if (earnedPointDetailReportTOList != null) {

                List<EarnedPointDetailReportTO> distinctUserCode = earnedPointDetailReportTOList.stream()
                        .filter(distinctByKey(EarnedPointDetailReportTO::getUserCode))
                        .collect(Collectors.toList());

                ArrayList<String> keyCode = new ArrayList<>();

                distinctUserCode.forEach(item -> keyCode.add(item.getUserCode()));

                Map<String, CompanyUserTO> companyUserMap = mapCodeUserName(keyCode);

                earnedPointDetailReportTOList.forEach(topUp -> {
                    var found = companyUserMap.get(topUp.getUserCode());
                    if (found != null) {
                        topUp.setUserName(found.getName());
                    }
                });
            }

            EarnedPointDetailReportRS earnedPointDetailReportRS = new EarnedPointDetailReportRS();
            earnedPointDetailReportRS.setEarnedPointDetailReportList(earnedPointDetailReportTOList);
            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, earnedPointDetailReportRS);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("unexpected_error", null);
        }
    }

    @Override
    public StructureRS refundedPointSummaryReport(String startDate, String endDate, String userCode) {
        try {
            RefundedPointSummaryReportTO refundedPointSummaryReportTO = reportNQ.refundedPointSummaryReport(
                    startDate, endDate, userCode);

            RefundedPointSummaryReportRS refundedPointSummaryReportRS = new RefundedPointSummaryReportRS();
            BeanUtils.copyProperties(refundedPointSummaryReportTO, refundedPointSummaryReportRS);
            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, refundedPointSummaryReportRS);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("unexpected_error", null);
        }
    }

    @Override
    public StructureRS refundedPointDetailReport(HttpServletRequest httpServletRequest, String startDate, String endDate, String userCode) {
        try {
            String languageCode = headerDataUtil.languageCodeExist(httpServletRequest);
            List<RefundedPointDetailReportTO> refundedPointDetailReportTOList = reportNQ.refundedPointDetailReport(
                    startDate, endDate, languageCode, userCode);

            if (refundedPointDetailReportTOList != null) {

                List<RefundedPointDetailReportTO> distinctUserCode = refundedPointDetailReportTOList.stream()
                        .filter(distinctByKey(RefundedPointDetailReportTO::getUserCode))
                        .collect(Collectors.toList());

                ArrayList<String> keyCode = new ArrayList<>();

                distinctUserCode.forEach(item -> keyCode.add(item.getUserCode()));

                Map<String, CompanyUserTO> companyUserMap = mapCodeUserName(keyCode);

                refundedPointDetailReportTOList.forEach(topUp -> {
                    var found = companyUserMap.get(topUp.getUserCode());
                    if (found != null) {
                        topUp.setUserName(found.getName());
                    }
                });
            }

            RefundedPointDetailReportRS refundedPointDetailReportRS = new RefundedPointDetailReportRS();
            refundedPointDetailReportRS.setRefundPointDetailReportList(refundedPointDetailReportTOList);
            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, refundedPointDetailReportRS);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("unexpected_error", null);
        }
    }

    @Override
    public StructureRS upgradedLevelReport(String startDate, String endDate) {
        try {
            UpgradedLevelSummaryReportTO upgradedLevelSummaryReportTO = reportNQ.upgradedLevelSummaryReport(
                    startDate, endDate);

            UpgradedLevelSummaryReportRS upgradedLevelSummaryReportRS = new UpgradedLevelSummaryReportRS();
            BeanUtils.copyProperties(upgradedLevelSummaryReportTO, upgradedLevelSummaryReportRS);
            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, upgradedLevelSummaryReportRS);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("unexpected_error", null);
        }
    }

    @Override
    public StructureRS transactionReportExport(HttpServletRequest httpServletRequest, String startDate, String endDate) {
        try {
            String languageCode = headerDataUtil.languageCodeExist(httpServletRequest);
            List<TransactionReportTO> transactionReportTOList = reportNQ.getTransactionReport(startDate, endDate, languageCode);

            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, formatTransactionReport(transactionReportTOList));
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("unexpected_error", null);
        }
    }

    private Map<String, CompanyUserTO> mapCodeUserName(ArrayList<String> keyCode) {
        ClientResponseUserCompany clientResponse = stakeholderAction.getUserOrCompanyListUserCode(keyCode);

        List<CompanyUserTO> companyUserTOList = clientResponse.getData();

        return companyUserTOList
                .stream()
                .collect(Collectors.toMap(CompanyUserTO::getCode, Function.identity(), (existing, replacement) -> existing));
    }

    private List<TransactionSummaryReport> formatTransactionSummaryReport(List<TransactionSummaryReportTO> transactionSummaryReportTOList) {
        List<TransactionSummaryReport> transactionSummaryReportList = new ArrayList<>();
        if (transactionSummaryReportTOList.size() > 0) {
            transactionSummaryReportTOList.forEach(transactionSummaryReportTO -> {
                TransactionSummaryReport transactionSummaryReport = new TransactionSummaryReport();
                BeanUtils.copyProperties(new TransactionSummaryReportTO(transactionSummaryReportTO),
                        transactionSummaryReport);
                transactionSummaryReportList.add(transactionSummaryReport);
            });
        }

        return transactionSummaryReportList;
    }


    private List<TransactionReport> formatTransactionReport(List<TransactionReportTO> transactionReportTOList) {
        List<TransactionReport> transactionReportList = new ArrayList<>();
        if (transactionReportTOList.size() > 0) {
            //========== Format date
            transactionReportTOList.forEach(transactionReportTO -> {
                TransactionReport transactionReport = new TransactionReport();
                BeanUtils.copyProperties(new TransactionReportTO(transactionReportTO), transactionReport);
                transactionReport.setTransactionDate(dateTimeBean.convertDateTime(transactionReportTO.getTransactionDate()));
                transactionReportList.add(transactionReport);
            });

            //========= Get Account Name
            List<TransactionReport> distinctUserCode = transactionReportList.stream()
                    .filter(distinctByKey(TransactionReport::getUserCode))
                    .collect(Collectors.toList());
            ArrayList<String> keyCode = new ArrayList<>();
            distinctUserCode.forEach(item -> keyCode.add(item.getUserCode()));
            Map<String, CompanyUserTO> companyUserMap = mapCodeUserName(keyCode);

            transactionReportList.forEach(transaction -> {
                var found = companyUserMap.get(transaction.getUserCode());
                if (found != null) {
                    transaction.setAccountName(found.getName());
                }
            });
        }

        return transactionReportList;
    }

    private List<TopUpTransactionDetailTO> formatOfflineTopUTransactionList(List<TopUpTransactionDetailTO>
                                                                                    topUpTransactionDetailTOList) {
        List<TopUpTransactionDetailTO> offlineTopUpTransactionDetailList = new ArrayList<>();
        if (topUpTransactionDetailTOList.size() > 0) {
            topUpTransactionDetailTOList.forEach(offlineTopUpTransactionDetailTO -> {
                offlineTopUpTransactionDetailList.add(new TopUpTransactionDetailTO(offlineTopUpTransactionDetailTO));
            });
        }
        return offlineTopUpTransactionDetailList;
    }


}
