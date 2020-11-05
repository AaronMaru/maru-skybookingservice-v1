package com.skybooking.backofficeservice.v1_0_0.service.account;

import com.skybooking.backofficeservice.v1_0_0.client.action.account.AccountAction;
import com.skybooking.backofficeservice.v1_0_0.client.model.response.account.AccountStructureServiceRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountIP implements AccountSV {

    @Autowired
    private AccountAction accountAction;

    public AccountStructureServiceRS profileDetail(){
        try {
            AccountStructureServiceRS accountDetail =  accountAction.getAccountDetail();

            return accountDetail;

        }catch (Exception exception){
            throw  exception;
        }
    }
}
