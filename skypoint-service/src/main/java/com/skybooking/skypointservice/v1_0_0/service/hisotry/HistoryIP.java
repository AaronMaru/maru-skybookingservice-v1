package com.skybooking.skypointservice.v1_0_0.service.hisotry;

import com.skybooking.skypointservice.constant.ResponseConstant;
import com.skybooking.skypointservice.httpstatus.BadRequestException;
import com.skybooking.skypointservice.httpstatus.InternalServerError;
import com.skybooking.skypointservice.util.HeaderDataUtil;
import com.skybooking.skypointservice.v1_0_0.client.ClientResponse;
import com.skybooking.skypointservice.v1_0_0.client.event.action.EventDownloadAction;
import com.skybooking.skypointservice.v1_0_0.client.event.model.requset.SkyPointTopUpRQ;
import com.skybooking.skypointservice.v1_0_0.client.stakeholder.action.StakeholderAction;
import com.skybooking.skypointservice.v1_0_0.client.stakeholder.model.response.UserReferenceRS;
import com.skybooking.skypointservice.v1_0_0.helper.AccountHelper;
import com.skybooking.skypointservice.v1_0_0.io.entity.account.AccountEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.topUp.TopUpDocumentEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionValueEntity;
import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.history.*;
import com.skybooking.skypointservice.v1_0_0.io.repository.account.AccountRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.topUp.TopUpDocumentRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.transaction.TransactionRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.transaction.TransactionValueRP;
import com.skybooking.skypointservice.v1_0_0.service.BaseServiceIP;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.PagingRS;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.download.TopUpRS;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.history.SkyOwnerTransactionHistoryRS;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.history.TransactionHistoryDetailRS;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.history.TransactionHistoryRS;
import com.skybooking.skypointservice.v1_0_0.util.datetime.DateTimeBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.skybooking.skypointservice.constant.TopUpTypeConstant.TOPUP_RECEIPT;

@Service
public class HistoryIP extends BaseServiceIP implements HistorySV {

    @Autowired
    private HistoryNQ historyNQ;

    @Autowired
    private AccountHelper accountHelper;

    @Autowired
    private AccountRP accountRP;

    @Autowired
    private DateTimeBean dateTimeBean;

    @Autowired
    private Environment environment;

    @Autowired
    private EventDownloadAction eventDownloadAction;

    @Autowired
    private HeaderDataUtil headerDataUtil;

    @Autowired
    private StakeholderAction stakeholderAction;

    @Autowired
    private TopUpDocumentRP topUpDocumentRP;

    @Autowired
    private TransactionValueRP transactionValueRP;

    @Autowired
    private TransactionRP transactionRP;

    @Override
    public StructureRS getTransactionHistoryByUserAccount(HttpServletRequest httpServletRequest, String startDate,
                                                          String endDate, String transactionTypCode, Integer page, Integer limit) {
        try {
            String languageCode = headerDataUtil.languageCode(httpServletRequest);
            // ======== userReferenceRS
            UserReferenceRS userReferenceRS = accountHelper.getUserReference(httpServletRequest);
            String userCode = userReferenceRS.getUserCode();
            List<TransactionHistoryTO> transactionHistoryTOList;
            Integer offset = (page - 1) * limit;
            PagingRS pagingRS = new PagingRS();
            List<CountTransactionHistory> countTransactionHistory;
            if (startDate != null && endDate != null) {
                transactionHistoryTOList = historyNQ.filterTransactionHistoryByAccount(userCode, transactionTypCode,
                        startDate, endDate, languageCode, limit, offset);
                countTransactionHistory = historyNQ.countAllFilterTransactionHistoryByAccount(userCode,
                        transactionTypCode, startDate, endDate);
            } else {

                transactionHistoryTOList = historyNQ.getTransactionHistoryByAccount(
                        userCode, transactionTypCode, languageCode, limit, offset);
                countTransactionHistory = historyNQ.countAllTransactionHistoryByAccount(userCode, transactionTypCode);
            }

            Long total = (long) countTransactionHistory.size();
            Integer pages = (int) Math.ceil(total / Double.valueOf(limit));
            pagingRS.setPage(page);
            pagingRS.setSize(pages);
            pagingRS.setTotals(total);

            TransactionHistoryRS transactionHistoryRS = new TransactionHistoryRS();
            transactionHistoryRS.setTransactionHistoryList(transactionHistoryTOList);

            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, transactionHistoryRS, pagingRS);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("unexpected_error", null);
        }
    }

    @Override
    public StructureRS getTransactionHistoryDetail(HttpServletRequest httpServletRequest, String transactionCode) {
        try {
            String languageCode = headerDataUtil.languageCode(httpServletRequest);
            TransactionHistoryDetailTO transactionHistoryDetailTO = historyNQ
                    .getTransactionHistoryDetail(transactionCode, languageCode).orElse(null);
            if (transactionHistoryDetailTO == null) {
                throw new BadRequestException("transaction_not_found", null);
            }

            TransactionHistoryDetailRS transactionHistoryDetailRS = new TransactionHistoryDetailRS();
            BeanUtils.copyProperties(transactionHistoryDetailTO, transactionHistoryDetailRS);

            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, transactionHistoryDetailRS);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("unexpected_error", null);
        }
    }

    @Override
    public StructureRS skyOwnerTransactionHistory(HttpServletRequest httpServletRequest, Integer page, Integer limit) {
        try {
            String languageCode = headerDataUtil.languageCode(httpServletRequest);
            UserReferenceRS userReferenceRS = accountHelper.getUserReference(httpServletRequest);
            Integer offset = (page - 1) * limit;
            List<SkyOwnerTransactionHistoryTO> skyOwnerTransactionHistoryList = historyNQ
                    .skyOwnerTransactionHistory(userReferenceRS.getUserCode(), languageCode, limit, offset);

            Long total = (long) historyNQ.skyOwnerTransactionHistoryAll(userReferenceRS.getUserCode(), languageCode)
                    .size();

            Integer pages = (int) Math.ceil(total / Double.valueOf(limit));

            PagingRS pagingRS = new PagingRS();
            pagingRS.setPage(page);
            pagingRS.setSize(pages);
            pagingRS.setTotals(total);

            SkyOwnerTransactionHistoryRS skyOwnerTransactionHistoryRS = new SkyOwnerTransactionHistoryRS();
            skyOwnerTransactionHistoryRS.setSkyOwnerTransactionHistoryList(skyOwnerTransactionHistoryList);
            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, skyOwnerTransactionHistoryRS, pagingRS);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("unexpected_error", null);
        }
    }

    @Override
    public StructureRS downloadReceipt(HttpServletRequest httpServletRequest, String code) {

        TransactionValueEntity transactionValue = transactionValueRP.findByCode(code);

        if (transactionValue == null) {
            throw new BadRequestException("no_top_up", null);
        }

        String languageCode = headerDataUtil.languageCodeExist(httpServletRequest);

        TopUpDocumentEntity topUpDocumentEntity = topUpDocumentRP
                .findByTransactionIdAndLanguageCode(transactionValue.getTransactionId(), languageCode);

        TopUpRS topUpRS;

        if (topUpDocumentEntity != null) {
            topUpRS = new TopUpRS(environment.getProperty("spring.aws.awsUrl.topUp") + topUpDocumentEntity.getPath()
                    + "/" + topUpDocumentEntity.getFile());
        } else {

            SkyPointTopUpRQ skyPointTopUpRQ = new SkyPointTopUpRQ();
            skyPointTopUpRQ.setAmount(transactionValue.getAmount());
            skyPointTopUpRQ.setEarnAmount(transactionValue.getAmount().multiply(transactionValue.getExtraRate()));
            skyPointTopUpRQ.setTransactionId(transactionValue.getCode());
            skyPointTopUpRQ.setTransactionDate(dateTimeBean.convertDateTime(transactionValue.getCreatedAt()));

            TransactionEntity transactionEntity = transactionRP.findById(transactionValue.getTransactionId())
                    .orElse(null);

            if (transactionEntity != null) {

                AccountEntity accountEntity = accountRP.findById(transactionEntity.getAccountId()).orElse(null);

                if (accountEntity != null) {

                    ClientResponse clientResponse = stakeholderAction
                            .getUserOrCompanyDetailByUserCode(accountEntity.getUserCode());

                    Map<String, Object> data = clientResponse.getData();

                    skyPointTopUpRQ.setEmail(data.get("email").toString());
                    skyPointTopUpRQ.setFullName(data.get("name").toString());
                    skyPointTopUpRQ.setPhone(data.get("phone") != null ? data.get("phone").toString() : "");
                }

            }

            ClientResponse s3UploadRS = eventDownloadAction.downloadReceipt(httpServletRequest, skyPointTopUpRQ);

            Map<String, Object> dataRS = s3UploadRS.getData();

            TopUpDocumentEntity topUpDocument = new TopUpDocumentEntity();
            topUpDocument.setTransactionId(transactionValue.getTransactionId());
            topUpDocument.setFile(dataRS.get("file").toString());
            topUpDocument.setPath(dataRS.get("path").toString());
            topUpDocument.setType(TOPUP_RECEIPT);
            topUpDocument.setLanguageCode(languageCode);
            topUpDocumentRP.save(topUpDocument);

            topUpRS = new TopUpRS(environment.getProperty("spring.aws.awsUrl.topUp") + dataRS.get("path").toString()
                    + "/" + dataRS.get("file").toString());
        }

        return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, topUpRS);
    }

    @Override
    public StructureRS exportTransactionHistoryByUserAccount(HttpServletRequest httpServletRequest, String startDate,
                                                             String endDate) {
        try {
            String languageCode = headerDataUtil.languageCode(httpServletRequest);
            // ======== userReferenceRS
            UserReferenceRS userReferenceRS = accountHelper.getUserReference(httpServletRequest);
            String userCode = userReferenceRS.getUserCode();
            List<TransactionHistoryTO> transactionHistoryTOList = historyNQ.getAllTransactionHistoryByAccount(userCode,
                    languageCode, startDate, endDate);

            TransactionHistoryRS transactionHistoryRS = new TransactionHistoryRS();
            transactionHistoryRS.setTransactionHistoryList(transactionHistoryTOList);

            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, transactionHistoryRS);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("unexpected_error", null);
        }
    }
}
