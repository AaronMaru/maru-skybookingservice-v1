package com.skybooking.skypointservice.v1_0_0.helper;

import com.skybooking.skypointservice.constant.AccountStatusConstant;
import com.skybooking.skypointservice.httpstatus.BadRequestException;
import com.skybooking.skypointservice.v1_0_0.client.ClientResponse;
import com.skybooking.skypointservice.v1_0_0.client.stakeholder.action.StakeholderAction;
import com.skybooking.skypointservice.v1_0_0.client.stakeholder.model.response.BasicAccountInfoRS;
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
        account.setLevelName("Blue");
        account.setStatus(AccountStatusConstant.ACTIVE);
        account.setCreatedAt(new Date());
        account.setUpdatedAt(new Date());
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
        Integer companyId = httpServletRequest.getHeader("X-CompanyId") != null &&
                !httpServletRequest.getHeader("X-CompanyId").isEmpty() ?
                Integer.valueOf(httpServletRequest.getHeader("X-CompanyId")) : 0;

        //========== Get value from jwt header
        Integer stakeholderUserId = 0;
        if (httpServletRequest.getHeader("Authorization") != null) {
            stakeholderUserId = jwtUtils.getClaim("stakeholderId", Integer.class);
        }
        ClientResponse clientResponse = stakeholderAction.getUserReference(stakeholderUserId, companyId);

        Map<String, Object> data = clientResponse.getData();
        String referenceCode = data.get("referenceCode").toString();
        String userType = data.get("type").toString();
        String userRole = data.get("userRole") != null ? data.get("userRole").toString() : null;

        UserReferenceRS userReferenceRS = new UserReferenceRS();
        userReferenceRS.setStakeholderUserId(stakeholderUserId);
        userReferenceRS.setStakeholderCompanyId(companyId == 0 ? null : companyId);
        userReferenceRS.setUserCode(referenceCode);
        userReferenceRS.setType(userType.toUpperCase());
        userReferenceRS.setUserRole(userRole);

        return userReferenceRS;
    }

    public BasicAccountInfoRS getBasicAccountInfo(String referenceCode, HttpServletRequest httpServletRequest) {
        ClientResponse clientResponse = stakeholderAction.getCompanyDetail(referenceCode, httpServletRequest);
        Map<String, Object> data = clientResponse.getData();
        if (data == null) {
            throw new BadRequestException("Company account not found", null);
        }
        BasicAccountInfoRS basicAccountInfoRS = new BasicAccountInfoRS();
        basicAccountInfoRS.setUserCode(data.get("companyCode").toString());
        basicAccountInfoRS.setEmail(data.get("email").toString());
        basicAccountInfoRS.setPhoneNumber(data.get("phone").toString());
        basicAccountInfoRS.setName(data.get("companyName").toString());
        return basicAccountInfoRS;
    }

    private AccountEntity updateAccount(AccountEntity account, BigDecimal topUp, BigDecimal earningExtra,
                                        BigDecimal balance, BigDecimal savedPoint, String levelName) {
        account.setLevelName(levelName);
        account.setTopup(topUp);
        account.setEarningExtra(earningExtra);
        account.setBalance(balance);
        account.setSavedPoint(savedPoint);
        account.setUpdatedAt(new Date());
        return accountRP.save(account);
    }
}
