package com.skybooking.skypointservice.v1_0_0.service.hisotry;

import com.skybooking.skypointservice.constant.ResponseConstant;
import com.skybooking.skypointservice.httpstatus.BadRequestException;
import com.skybooking.skypointservice.v1_0_0.client.stakeholder.model.response.UserReferenceRS;
import com.skybooking.skypointservice.v1_0_0.helper.AccountHelper;
import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.history.TransactionHistoryDetailTO;
import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.history.TransactionHistoryNQ;
import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.history.TransactionHistoryTO;
import com.skybooking.skypointservice.v1_0_0.service.BaseServiceIP;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.history.FilterTransactionHistoryRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.history.FilterTransactionHistoryRS;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.history.TransactionHistoryDetailRS;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.history.TransactionHistoryRS;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class HistoryIP extends BaseServiceIP implements HistorySV {
    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private TransactionHistoryNQ transactionHistoryNQ;

    @Autowired
    private AccountHelper accountHelper;

    @Override
    public StructureRS getTransactionHistoryByUserAccount() {
        try {
            //======== userReferenceRS
            UserReferenceRS userReferenceRS = accountHelper.getUserReference(httpServletRequest);
            String userCode = userReferenceRS.getUserCode();
            List<TransactionHistoryTO> transactionHistoryTOList = transactionHistoryNQ
                    .getTransactionHistoryByAccount(userCode);

            TransactionHistoryRS transactionHistoryRS = new TransactionHistoryRS();
            transactionHistoryRS.setTransactionHistoryList(transactionHistoryTOList);

            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, transactionHistoryRS);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Unexpected error.", null);
        }
    }

    @Override
    public StructureRS filterTransactionHistoryByAccount(FilterTransactionHistoryRQ filterTransactionHistoryRQ) {
        try {
            //======== userReferenceRS
            UserReferenceRS userReferenceRS = accountHelper.getUserReference(httpServletRequest);
            String userCode = userReferenceRS.getUserCode();
            List<TransactionHistoryTO> transactionHistoryTOList = transactionHistoryNQ
                    .filterTransactionHistoryByAccount(userCode,
                            filterTransactionHistoryRQ.getStartDate(), filterTransactionHistoryRQ.getEndDate());

            FilterTransactionHistoryRS filterTransactionHistoryRS = new FilterTransactionHistoryRS();
            filterTransactionHistoryRS.setTransactionHistoryList(transactionHistoryTOList);

            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, filterTransactionHistoryRS);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Unexpected error.", null);
        }
    }

    @Override
    public StructureRS getTransactionHistoryDetail(Integer transactionValueId) {
        try {
            //======== userReferenceRS
            TransactionHistoryDetailTO transactionHistoryDetailTO = transactionHistoryNQ.
                    getTransactionHistoryDetail(transactionValueId);

            TransactionHistoryDetailRS transactionHistoryDetailRS = new TransactionHistoryDetailRS();
            BeanUtils.copyProperties(transactionHistoryDetailTO, transactionHistoryDetailRS);
            
            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, transactionHistoryDetailRS);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Unexpected error.", null);
        }
    }
}
