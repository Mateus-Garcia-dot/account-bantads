package com.bantads.account.consumer;


import com.bantads.account.config.RabbitMQConfiguration;
import com.bantads.account.model.AccountModel;
import com.bantads.account.repository.AccountRepository;
import lombok.Data;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@Service
public class AccountConsumer {

    private final AccountRepository accountRepository;

    @RabbitListener(queues = "${rabbitmq.create}")
    public void createAccount(AccountModel accountModel) {
        this.accountRepository.save(accountModel);
    }

    @RabbitListener(queues = "${rabbitmq.update}")
    public void updateAccount(String id, AccountModel accountModel) {
        AccountModel account = this.accountRepository.findById(id).orElseThrow();
        account.setCustomer(accountModel.getCustomer());
        account.setManager(accountModel.getManager());
        account.setLimitAmount(accountModel.getLimitAmount());
        account.setBalance(accountModel.getBalance());
    }

    @RabbitListener(queues = "${rabbitmq.delete}")
    public void deleteAccount(String id) {
        this.accountRepository.deleteById(id);
    }

    @RabbitListener(queues = "${rabbitmq.patch.consumer}")
    public void patchAccountConsumer(AccountModel accountModel) {
        List<AccountModel> accounts = this.accountRepository.findByConsumerId(accountModel.getCustomer());
        if (accounts.isEmpty()) {
            return;
        }
        for (AccountModel account : accounts) {
            if (accountModel.getManager() != null) {
                account.setManager(accountModel.getManager());
            }
            if (accountModel.getLimitAmount() != null) {
                account.setLimitAmount(accountModel.getLimitAmount());
            }
            if (accountModel.getBalance() != null) {
                account.setBalance(accountModel.getBalance());
            }
            this.accountRepository.save(account);
        }
    }
}
