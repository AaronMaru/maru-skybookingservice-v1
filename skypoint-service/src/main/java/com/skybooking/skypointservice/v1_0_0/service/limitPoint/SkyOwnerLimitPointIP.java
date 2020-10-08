package com.skybooking.skypointservice.v1_0_0.service.limitPoint;

import com.skybooking.skypointservice.constant.ResponseConstant;
import com.skybooking.skypointservice.httpstatus.BadRequestException;
import com.skybooking.skypointservice.httpstatus.InternalServerError;
import com.skybooking.skypointservice.v1_0_0.client.stakeholder.model.response.UserReferenceRS;
import com.skybooking.skypointservice.v1_0_0.helper.AccountHelper;
import com.skybooking.skypointservice.v1_0_0.io.entity.account.AccountEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.pointLimit.SkyOwnerLimitPointEntity;
import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.skyPointTransaction.TransactionAmountNQ;
import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.skyPointTransaction.TransactionAmountTO;
import com.skybooking.skypointservice.v1_0_0.io.repository.account.AccountRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.pointLimit.SkyOwnerLimitPointRP;
import com.skybooking.skypointservice.v1_0_0.service.BaseServiceIP;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.limitPoint.AvailablePointRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.limitPoint.SkyOwnerLimitPointRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.limitPoint.AvailablePointRS;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.limitPoint.SkyOwnerLimitPointListRS;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.limitPoint.SkyOwnerLimitPointRS;
import com.skybooking.skypointservice.v1_0_0.util.auth.JwtUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class SkyOwnerLimitPointIP extends BaseServiceIP implements SkyOwnerLimitPointSV {
    @Autowired
    private SkyOwnerLimitPointRP skyOwnerLimitPointRP;

    @Autowired
    private AccountRP accountRP;

    @Autowired
    private AccountHelper accountHelper;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private TransactionAmountNQ transactionAmountNQ;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public StructureRS createLimitPoint(SkyOwnerLimitPointRQ skyOwnerLimitPointRQ) {
        try {
            SkyOwnerLimitPointEntity skyOwnerLimitPoint = new SkyOwnerLimitPointEntity();
            BeanUtils.copyProperties(skyOwnerLimitPointRQ, skyOwnerLimitPoint);

            Integer stakeholderCompanyId = this.validateCompanyId();
            UserReferenceRS userReferenceRS = accountHelper.getUserReference(
                    skyOwnerLimitPointRQ.getStakeholderUserId(), stakeholderCompanyId);

            //==== Get account by userCode
            AccountEntity account = accountRP.findAccountEntityByUserCode(userReferenceRS.getUserCode())
                    .orElse(null);
            if (account == null) {
                throw new BadRequestException("account_not_found", null);
            }

            SkyOwnerLimitPointEntity checkSkyOwnerLimitPoint = skyOwnerLimitPointRP.findByStakeholderUserIdAndStatus(
                    skyOwnerLimitPointRQ.getStakeholderUserId(), true);

            if (checkSkyOwnerLimitPoint != null) {
                throw new BadRequestException("staff_limited_point_already", null);
            }

            skyOwnerLimitPoint.setCreatedBy(jwtUtils.userToken().getUserId().toString());
            skyOwnerLimitPoint.setStakeholderUserId(stakeholderCompanyId);
            skyOwnerLimitPoint.setAccountId(account.getId());
            skyOwnerLimitPoint = skyOwnerLimitPointRP.save(skyOwnerLimitPoint);

            SkyOwnerLimitPointRS skyOwnerLimitPointRS = new SkyOwnerLimitPointRS();
            skyOwnerLimitPointRS.setSkyOwnerLimitPoint(skyOwnerLimitPoint);
            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, skyOwnerLimitPoint);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("unexpected_error", null);
        }
    }

    @Override
    public StructureRS updateLimitPoint(SkyOwnerLimitPointRQ skyOwnerLimitPointRQ) {
        try {

            SkyOwnerLimitPointEntity skyOwnerLimitPoint = skyOwnerLimitPointRP.findByStakeholderUserIdAndStatus(
                    skyOwnerLimitPointRQ.getStakeholderUserId(), true);
            if (skyOwnerLimitPoint == null) {
                throw new BadRequestException("point_limit_not_found", null);
            }

            skyOwnerLimitPoint.setModifiedBy(jwtUtils.userToken().getUserId().toString());
            skyOwnerLimitPoint.setValue(skyOwnerLimitPointRQ.getValue());
            skyOwnerLimitPoint = skyOwnerLimitPointRP.save(skyOwnerLimitPoint);

            SkyOwnerLimitPointRS skyOwnerLimitPointRS = new SkyOwnerLimitPointRS();
            skyOwnerLimitPointRS.setSkyOwnerLimitPoint(skyOwnerLimitPoint);
            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, skyOwnerLimitPoint);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("unexpected_error", null);
        }
    }

    @Override
    public StructureRS getDetailLimitPoint(Integer limitPointId) {
        try {

            SkyOwnerLimitPointEntity skyOwnerLimitPoint = skyOwnerLimitPointRP.findByIdAndStatus(
                    limitPointId, true);
            if (skyOwnerLimitPoint == null) {
                throw new BadRequestException("point_limit_not_found", null);
            }

            SkyOwnerLimitPointRS skyOwnerLimitPointRS = new SkyOwnerLimitPointRS();
            skyOwnerLimitPointRS.setSkyOwnerLimitPoint(skyOwnerLimitPoint);
            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, skyOwnerLimitPoint);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("unexpected_error", null);
        }
    }

    @Override
    public StructureRS listLimitPoint() {
        try {
            List<SkyOwnerLimitPointEntity> skyOwnerLimitPointList = skyOwnerLimitPointRP.findAllByStatus(true);
            SkyOwnerLimitPointListRS skyOwnerLimitPointRS = new SkyOwnerLimitPointListRS();
            skyOwnerLimitPointRS.setSkyOwnerLimitPointList(skyOwnerLimitPointList);
            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, skyOwnerLimitPointRS);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("unexpected_error", null);
        }
    }

    @Override
    public StructureRS skyStaffPointAvailable(AvailablePointRQ availablePointRQ) {
        try {
            Integer stakeholderCompanyId = this.validateCompanyId();
            SkyOwnerLimitPointEntity skyOwnerLimitPoint = skyOwnerLimitPointRP.findByStakeholderUserIdAndStatus(
                    availablePointRQ.getStakeholderUserId(), true);
            if (skyOwnerLimitPoint == null) {
                throw new BadRequestException("point_limit_not_found", null);
            }

            TransactionAmountTO transactionAmountTO = transactionAmountNQ.getAmountRedeemForCurrentDateByAccount(availablePointRQ.getStakeholderUserId(),
                    stakeholderCompanyId).orElse(new TransactionAmountTO());

            AvailablePointRS availablePointRS = new AvailablePointRS();
            availablePointRS.setAvailablePoint(skyOwnerLimitPoint.getValue().subtract(transactionAmountTO.getAmount()));
            availablePointRS.setUsedPoint(transactionAmountTO.getAmount());
            availablePointRS.setLimitPoint(skyOwnerLimitPoint.getValue());
            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, availablePointRS);
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("unexpected_error", null);
        }
    }

    private Integer validateCompanyId() {
        if (httpServletRequest.getHeader("X-CompanyId") == null) {
            throw new BadRequestException("x_company_id_not_null", null);
        }

        return Integer.valueOf(httpServletRequest.getHeader("X-CompanyId"));
    }
}
