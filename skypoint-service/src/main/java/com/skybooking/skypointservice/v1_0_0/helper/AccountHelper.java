package com.skybooking.skypointservice.v1_0_0.helper;

import com.skybooking.skypointservice.constant.AccountStatusConstant;
import com.skybooking.skypointservice.httpstatus.BadRequestException;
import com.skybooking.skypointservice.v1_0_0.client.ClientResponse;
import com.skybooking.skypointservice.v1_0_0.client.stakeholder.action.StakeholderAction;
import com.skybooking.skypointservice.v1_0_0.client.stakeholder.model.response.BasicCompanyAccountInfoRS;
import com.skybooking.skypointservice.v1_0_0.client.stakeholder.model.response.UserAccountInfoRS;
import com.skybooking.skypointservice.v1_0_0.client.stakeholder.model.response.UserReferenceRS;
import com.skybooking.skypointservice.v1_0_0.io.entity.account.AccountEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionEntity;
import com.skybooking.skypointservice.v1_0_0.io.repository.account.AccountRP;
import com.skybooking.skypointservice.v1_0_0.util.auth.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Component
public class AccountHelper {
    @Autowired
    private AccountRP accountRP;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private StakeholderAction stakeholderAction;

    public AccountEntity createNewAccount(AccountEntity account, String userCode, String userType) {
        account.setUserCode(userCode);
        account.setType(userType);
        account.setLevelCode("LEVEL1");
        account.setStatus(AccountStatusConstant.ACTIVE);
        account = accountRP.save(account);
        return account;
    }

    public AccountEntity updateAccountForTopUpSuccess(AccountEntity account, TransactionEntity transaction,
                                                      BigDecimal extraEarning, String levelName) {

        return this.updateAccount(account, account.getTopup().add(transaction.getAmount()),
                account.getEarningExtra().add(extraEarning),
                account.getBalance().add(transaction.getAmount().add(extraEarning)),
                account.getSavedPoint().add(extraEarning), levelName);
    }

    public UserReferenceRS getUserReference(HttpServletRequest httpServletRequest) {
        Integer stakeholderCompanyId = httpServletRequest.getHeader("X-CompanyId") != null &&
                !httpServletRequest.getHeader("X-CompanyId").isEmpty() ?
                Integer.valueOf(httpServletRequest.getHeader("X-CompanyId")) : 0;

        //========== Get value from jwt header
        Integer stakeholderUserId = 0;
        if (httpServletRequest.getHeader("Authorization") != null) {
            stakeholderUserId = jwtUtils.getClaim("stakeholderId", Integer.class);
        }

        return this.getUserReference(stakeholderUserId, stakeholderCompanyId);
    }

    public UserReferenceRS getUserReference(Integer stakeholderUserId, Integer stakeholderCompanyId) {
        ClientResponse clientResponse = stakeholderAction.getUserReference(stakeholderUserId, stakeholderCompanyId);

        Map<String, Object> data = clientResponse.getData();
        if (data.get("referenceCode") == null) {
            throw new BadRequestException("user_not_found", null);
        }
        String referenceCode = data.get("referenceCode").toString();
        String userType = data.get("type").toString();
        String userRole = data.get("userRole") != null ? data.get("userRole").toString() : null;

        UserReferenceRS userReferenceRS = new UserReferenceRS();
        userReferenceRS.setStakeholderUserId(stakeholderUserId);
        userReferenceRS.setStakeholderCompanyId(stakeholderCompanyId == 0 ? null : stakeholderCompanyId);
        userReferenceRS.setUserCode(referenceCode);
        userReferenceRS.setType(userType.toUpperCase());
        userReferenceRS.setUserRole(userRole);

        return userReferenceRS;
    }

    public BasicCompanyAccountInfoRS getBasicCompanyAccountInfo(String referenceCode) {
        ClientResponse clientResponse = stakeholderAction.getCompanyDetail(referenceCode);
        Map<String, Object> data = clientResponse.getData();
        if (data == null) {
            throw new BadRequestException("company_not_found", null);
        }
        BasicCompanyAccountInfoRS basicCompanyAccountInfoRS = new BasicCompanyAccountInfoRS();
        basicCompanyAccountInfoRS.setUserCode(data.get("companyCode").toString());
        basicCompanyAccountInfoRS.setEmail(data.get("email") == null ? "" : data.get("email").toString());
        basicCompanyAccountInfoRS.setPhoneNumber(data.get("phone") == null ? "" : data.get("phone").toString());
        basicCompanyAccountInfoRS.setName(data.get("companyName").toString());
        return basicCompanyAccountInfoRS;
    }

    public UserAccountInfoRS getUserOrCompanyDetailByUserCode(String referenceCode) {
        ClientResponse clientResponse = stakeholderAction.getUserOrCompanyDetailByUserCode(referenceCode);
        Map<String, Object> data = clientResponse.getData();
        if (data == null) {
            throw new BadRequestException("user_not_found", null);
        }
        UserAccountInfoRS userAccountInfoRS = new UserAccountInfoRS();
        userAccountInfoRS.setCode(data.get("code").toString());
        userAccountInfoRS.setEmail(data.get("email") == null ? "" : data.get("email").toString());
        userAccountInfoRS.setPhone(data.get("phone") == null ? "" : data.get("phone").toString());
        userAccountInfoRS.setName(data.get("name").toString());
        userAccountInfoRS.setThumbnail(data.get("thumbnail") == null ? "" : String.valueOf(data.get("thumbnail")));
        return userAccountInfoRS;
    }

    private AccountEntity updateAccount(AccountEntity account, BigDecimal topUp, BigDecimal earningExtra,
                                        BigDecimal balance, BigDecimal savedPoint, String levelCode) {
        account.setLevelCode(levelCode);
        account.setTopup(topUp);
        account.setEarningExtra(earningExtra);
        account.setBalance(balance);
        account.setSavedPoint(savedPoint);
        account.setUpdatedAt(new Date());
        return accountRP.save(account);
    }
}
