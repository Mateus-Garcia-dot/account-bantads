package com.bantads.account.consumer;


import com.bantads.account.config.RabbitMQConfiguration;
import com.bantads.account.model.AccountModel;
import com.bantads.account.repository.AccountRepository;
import lombok.Data;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Data
@Service
public class AccountConsumer {

    private final AccountRepository accountRepository;

    @RabbitListener(queues = "${rabbitmq.create}")
    public void createAccount(@RequestBody AccountModel accountModel) {
        this.accountRepository.save(accountModel);
    }

    @RabbitListener(queues = "${rabbitmq.update}")
    public void updateAccount(@PathVariable String id, @RequestBody AccountModel accountModel) {
        AccountModel account = this.accountRepository.findById(id).orElseThrow();
        account.setCustomer(accountModel.getCustomer());
        account.setManager(accountModel.getManager());
        account.setLimitAmount(accountModel.getLimitAmount());
        account.setBalance(accountModel.getBalance());
    }

    @RabbitListener(queues = "${rabbitmq.delete}")
    public void deleteAccount(@PathVariable String id) {
        this.accountRepository.deleteById(id);
    }
}
