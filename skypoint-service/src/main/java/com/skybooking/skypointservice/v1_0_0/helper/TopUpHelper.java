package com.skybooking.skypointservice.v1_0_0.helper;

import com.skybooking.skypointservice.constant.ConfigTopUpTypeConstant;
import com.skybooking.skypointservice.constant.TransactionTypeConstant;
import com.skybooking.skypointservice.httpstatus.BadRequestException;
import com.skybooking.skypointservice.v1_0_0.io.entity.account.AccountEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.config.ConfigTopUpEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.config.ConfigUpgradeLevelEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.topUp.TopUpDocumentEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionValueEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.upgradeLevel.UpgradeLevelHistoryEntity;
import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.transaction.AmountOnlineTopUpTO;
import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.transaction.TransactionNQ;
import com.skybooking.skypointservice.v1_0_0.io.repository.account.AccountRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.config.ConfigTopUpRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.config.ConfigUpgradeLevelRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.topUp.TopUpDocumentRP;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.transaction.TopUpInfo;
import com.skybooking.skypointservice.v1_0_0.util.aws.AwsPathCM;
import com.skybooking.skypointservice.v1_0_0.util.aws.AwsUploadCM;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Autowired
    private AwsPathCM awsPathCM;

    public AccountEntity createAccountIfNotExist(String userCode, String userType) {
        //======== Get configUpgradeLevel by value & userType
        ConfigUpgradeLevelEntity configUpgradeLevel;

        //========== Check account is exits
        AccountEntity account;
        account = accountRP.findAccountEntityByUserCode(userCode).orElse(null);

        //========= Create account if account not exist
        if (account == null) {
            account = accountHelper.createNewAccount(new AccountEntity(), userCode, userType);

            configUpgradeLevel = configUpgradeLevelRP.getRecordByFromValueAndTypeAndToValue(account.getSavedPoint(),
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
        ConfigTopUpEntity configTopUp = configTopUpRP.findByTypeAndTopUpKeyAndStatus(userType.toUpperCase(),
                ConfigTopUpTypeConstant.LIMIT_AMOUNT, true);

        //=========== Get all amount user top up by online per day (current date)
        AmountOnlineTopUpTO amountOnlineTopUpTO = transactionNQ.getAmountOnlineTopUpForCurrentDateByAccount(accountId)
                .orElse(new AmountOnlineTopUpTO());

        if (amountOnlineTopUpTO.getAmount() == null) {
            amountOnlineTopUpTO.setAmount(BigDecimal.valueOf(0));
        }

        if (configTopUp.getValue().compareTo(amount.add(amountOnlineTopUpTO.getAmount())) < 0) {
            throw new BadRequestException("over_limit_top_up_amount", null);
        }
    }

    public void saveFileForTopUpOffline(List<MultipartFile> file, TransactionEntity transaction) {
        if (file != null) {
            if (!file.isEmpty()) {
                for (MultipartFile file1 : file) {
                    if (file1.getSize() > 0) {
                        TopUpDocumentEntity topUpDocument = new TopUpDocumentEntity();
                        String fileName = awsUploadCM.uploadFileForm(file1);
                        topUpDocument.setFile(fileName);
                        topUpDocument.setTransactionId(transaction.getId());
                        topUpDocument.setType("topup_document");
                        topUpDocumentRP.save(topUpDocument);
                    }
                }
            }
        }
    }

    public void updateAccountAndSaveUpgradeLevelHistory(AccountEntity account, TransactionEntity transaction,
                                                        BigDecimal extraEarning, BigDecimal extraRate, String userType) {

        BigDecimal savedPoint = account.getSavedPoint().add(transaction.getAmount().multiply(extraRate));
        //========== Get config level upgrade
        ConfigUpgradeLevelEntity configUpgradeLevel = configUpgradeLevelRP.getRecordByFromValueAndTypeAndToValue(
                savedPoint, userType);

        if (!account.getLevelCode().equalsIgnoreCase(configUpgradeLevel.getCode())) {
            //======== Save upgrade level history
            upgradeLevelHistoryHelper.saveOrUpdateUpgradeLevelHistory(new UpgradeLevelHistoryEntity(),
                    configUpgradeLevel, account.getId(), new Date());
        }

        //======== Update account
        accountHelper.updateAccountForTopUpSuccess(account, transaction, extraEarning,
                configUpgradeLevel.getCode());
    }

    public void saveTransactionValueForTopUp(TransactionEntity transaction, ConfigTopUpEntity configTopUp,
                                             BigDecimal extraEarning) {
        transactionValueHelper.saveTransactionValue(transaction, configTopUp, new BigDecimal(0),
                TransactionTypeConstant.TOP_UP);
        transactionValueHelper.saveTransactionValue(transaction, configTopUp, extraEarning,
                TransactionTypeConstant.EARNED_EXTRA);
    }

    public TopUpInfo topUpInfo(TransactionEntity transaction, TransactionValueEntity transactionValue) {
        TopUpInfo topUpInfo = new TopUpInfo();
        BeanUtils.copyProperties(transaction, topUpInfo);
        topUpInfo.setTransactionCode(transactionValue.getCode());
        topUpInfo.setId(transaction.getId());
        topUpInfo.setReferenceCode(transaction.getReferenceCode());
        topUpInfo.setRemark(transaction.getRemark());
        //======== Get file
        List<String> files = new ArrayList<>();
        List<TopUpDocumentEntity> topTopUpDocumentList = topUpDocumentRP.findAllByTransactionIdAndType(
                transaction.getId(), "topup_document");
        if (topTopUpDocumentList != null) {
            topTopUpDocumentList.forEach(topUpDocument -> {
                files.add(awsPathCM.partUrl(topUpDocument.getFile()));
            });
        }
        topUpInfo.setFiles(files);

        return topUpInfo;
    }


}
