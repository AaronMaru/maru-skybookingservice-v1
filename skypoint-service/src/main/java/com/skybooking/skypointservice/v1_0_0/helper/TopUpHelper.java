package com.skybooking.skypointservice.v1_0_0.helper;

import com.skybooking.skypointservice.constant.ConfigTopUpTypeConstant;
import com.skybooking.skypointservice.constant.TransactionTypeConstant;
import com.skybooking.skypointservice.httpstatus.BadRequestException;
import com.skybooking.skypointservice.v1_0_0.io.entity.account.AccountEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.config.ConfigTopUpEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.config.ConfigUpgradeLevelEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.topUp.TopUpDocumentEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.upgradeLevel.UpgradeLevelHistoryEntity;
import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.transaction.AmountOnlineTopUpTO;
import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.transaction.TransactionNQ;
import com.skybooking.skypointservice.v1_0_0.io.repository.account.AccountRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.config.ConfigTopUpRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.config.ConfigUpgradeLevelRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.topUp.TopUpDocumentRP;
import com.skybooking.skypointservice.v1_0_0.util.aws.AwsUploadCM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class TopUpHelper {
    @Autowired
    private AccountRP accountRP;

    @Autowired
    private AccountHelper accountHelper;

    @Autowired
    private ConfigUpgradeLevelRP configUpgradeLevelRP;

    @Autowired
    private ConfigTopUpRP configTopUpRP;

    @Autowired
    private UpgradeLevelHistoryHelper upgradeLevelHistoryHelper;

    @Autowired
    private TransactionValueHelper transactionValueHelper;

    @Autowired
    private TopUpDocumentRP topUpDocumentRP;

    @Autowired
    private AwsUploadCM awsUploadCM;

    @Autowired
    private TransactionNQ transactionNQ;

    public AccountEntity createAccountIfNotExist(String userCode, String userType) {
        //======== Get configUpgradeLevel by value & userType
        ConfigUpgradeLevelEntity configUpgradeLevel;

        //========== Check account is exits
        AccountEntity account;
        account = accountRP.findAccountEntityByUserCodeAndType(userCode, userType).orElse(null);

        //========= Create account if account not exist
        if (account == null) {
            account = accountHelper.createNewAccount(new AccountEntity(), userCode, userType);

            configUpgradeLevel = configUpgradeLevelRP.getRecordByFromValueAndToValue(account.getSavedPoint(),
                    userType);

            //======== Save upgrade level history
            upgradeLevelHistoryHelper.saveOrUpdateUpgradeLevelHistory(new UpgradeLevelHistoryEntity(),
                    configUpgradeLevel,
                    account.getId(), new Date());
        }

        return account;
    }

    public void saveTransactionValueAndUpgradeAccount(TransactionEntity transaction, AccountEntity account,
                                                      ConfigTopUpEntity configTopUp, String userType) {
        BigDecimal extraEarning = transaction.getAmount().multiply(configTopUp.getValue());

        //======== Save transactionValue
        this.saveTransactionValueForTopUp(transaction, configTopUp, extraEarning);

        //========== Update account balance and save upgrade level history
        this.updateAccountAndSaveUpgradeLevelHistory(account, transaction, extraEarning,
                configTopUp.getValue(), userType);
    }

    public void validateForOnlineTopUp(BigDecimal amount, String userType, Integer accountId) {
        //========== Get config extra rate by userType
        ConfigTopUpEntity configTopUp = configTopUpRP.findByTypeAndTopupKeyAndStatus(userType.toUpperCase(),
                ConfigTopUpTypeConstant.LIMIT_AMOUNT, true);
        
        //=========== Get all amount user top up by online per day (current date)
        AmountOnlineTopUpTO amountOnlineTopUpTO = transactionNQ.getAmountOnlineTopUpForCurrentDateByAccount(accountId)
                .orElse(new AmountOnlineTopUpTO());

        if (amountOnlineTopUpTO.getAmount() == null) {
            amountOnlineTopUpTO.setAmount(BigDecimal.valueOf(0));
        }
        
        if (configTopUp.getValue().compareTo(amount.add(amountOnlineTopUpTO.getAmount())) < 0) {
            throw new BadRequestException("You can't top up over " + configTopUp.getValue().toString() + " per day.", null);
        }
    }

    public void saveFileForTopUpOffline(MultipartFile file, TransactionEntity transaction, TopUpDocumentEntity topUpDocument) {
        if (file != null) {
            if (!file.isEmpty()) {
                String fileName = awsUploadCM.uploadFileForm(file);
                topUpDocument.setFile(fileName);
                topUpDocument.setTransactionId(transaction.getId());
                topUpDocumentRP.save(topUpDocument);
            }
        }
    }

    private void updateAccountAndSaveUpgradeLevelHistory(AccountEntity account, TransactionEntity transaction,
                                                         BigDecimal extraEarning, BigDecimal extraRate, String userType) {

        BigDecimal savedPoint = account.getSavedPoint().add(transaction.getAmount().multiply(extraRate));
        //========== Get config level upgrade
        ConfigUpgradeLevelEntity configUpgradeLevel = configUpgradeLevelRP.getRecordByFromValueAndToValue(
                savedPoint, userType);

        if (!account.getLevelName().equalsIgnoreCase(configUpgradeLevel.getLevelName())) {
            //======== Save upgrade level history
            upgradeLevelHistoryHelper.saveOrUpdateUpgradeLevelHistory(new UpgradeLevelHistoryEntity(),
                    configUpgradeLevel, account.getId(), new Date());
        }

        //======== Update account
        accountHelper.updateAccountForTopUpSuccess(account, transaction, extraEarning,
                configUpgradeLevel.getLevelName());
    }

    private void saveTransactionValueForTopUp(TransactionEntity transaction, ConfigTopUpEntity configTopUp,
                                              BigDecimal extraEarning) {
        transactionValueHelper.saveTransactionValue(transaction, configTopUp, new BigDecimal(0),
                TransactionTypeConstant.TOP_UP);
        transactionValueHelper.saveTransactionValue(transaction, configTopUp, extraEarning,
                TransactionTypeConstant.EARNING_EXTRA);
    }

}
