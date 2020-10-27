package com.skybooking.skypointservice.v1_0_0.service.account;

import com.skybooking.skypointservice.constant.ResponseConstant;
import com.skybooking.skypointservice.constant.UserTypeConstant;
import com.skybooking.skypointservice.httpstatus.BadRequestException;
import com.skybooking.skypointservice.httpstatus.InternalServerError;
import com.skybooking.skypointservice.util.HeaderDataUtil;
import com.skybooking.skypointservice.v1_0_0.client.stakeholder.model.response.UserReferenceRS;
import com.skybooking.skypointservice.v1_0_0.helper.AccountHelper;
import com.skybooking.skypointservice.v1_0_0.io.entity.account.AccountEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.config.ConfigUpgradeLevelEntity;
import com.skybooking.skypointservice.v1_0_0.io.repository.account.AccountRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.config.ConfigUpgradeLevelRP;
import com.skybooking.skypointservice.v1_0_0.service.BaseServiceIP;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.account.AccountBalanceInfoRS;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.account.BalanceRS;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class AccountIP extends BaseServiceIP implements AccountSV {

    @Autowired
    private AccountRP accountRP;

    @Autowired
    private AccountHelper accountHelper;

    @Autowired
    private ConfigUpgradeLevelRP configUpgradeLevelRP;

    @Autowired
    private HeaderDataUtil headerDataUtil;

    @Override
    public StructureRS getBalance(HttpServletRequest httpServletRequest) {
        try {
            BalanceRS balanceRS = new BalanceRS();
            String languageCode = headerDataUtil.languageCode(httpServletRequest);
            UserReferenceRS userReferenceRS = accountHelper.getUserReference(httpServletRequest);
            String userCode = userReferenceRS.getUserCode();

            AccountEntity account = accountRP.findAccountEntityByUserCode(userCode)
                    .orElse(new AccountEntity("LEVEL1"));
            ConfigUpgradeLevelEntity configUpgradeLevel = configUpgradeLevelRP.findByCodeAndTypeAndLanguageCode(
                    account.getLevelCode(), UserTypeConstant.SKYOWNER, languageCode);

            BeanUtils.copyProperties(account, balanceRS);
            balanceRS.setLevelName(configUpgradeLevel.getLevelName());

            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, new BalanceRS(balanceRS));
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("unexpected_error", null);
        }
    }

    @Override
    public StructureRS getSkyOwnerAccountBalanceInfo(HttpServletRequest httpServletRequest, String userCode) {
        try {
            String languageCode = headerDataUtil.languageCode(httpServletRequest);
            AccountEntity account = accountRP.findAccountEntityByUserCode(userCode).orElse(new AccountEntity("LEVEL1"));
            //======== Return response
            AccountBalanceInfoRS accountBalanceInfoRS = new AccountBalanceInfoRS();
            BeanUtils.copyProperties(account, accountBalanceInfoRS);
            //====== Get config level upgrade
            ConfigUpgradeLevelEntity configUpgradeLevel = configUpgradeLevelRP.getRecordByFromValueAndToValueAndTypeAndLanguageCode(
                    account.getSavedPoint(),
                    UserTypeConstant.SKYOWNER,
                    languageCode
            );
            accountBalanceInfoRS.setLevelName(configUpgradeLevel.getLevelName());

            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, new AccountBalanceInfoRS(accountBalanceInfoRS));
        } catch (BadRequestException e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError("unexpected_error", null);
        }
    }
}
