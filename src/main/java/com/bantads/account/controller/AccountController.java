package com.bantads.account.controller;

import com.bantads.account.config.RabbitMQConfiguration;
import com.bantads.account.model.AccountByManager;
import com.bantads.account.model.AccountModel;
import com.bantads.account.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController()
@RequestMapping("/account")
public class AccountController {

    private final AccountRepository accountRepository;

    @GetMapping
    public ResponseEntity<List<AccountModel>> getAllAccounts() {
        List<AccountModel> accountModelList = this.accountRepository.findAll();
        return ResponseEntity.ok(accountModelList);
    }

    @GetMapping("/count-manager")
    public ResponseEntity<List<AccountByManager>> countManager() {
       System.out.println(this.accountRepository.countManager());
       return null;
    }
    @GetMapping("/{id}")
    public ResponseEntity<AccountModel> getAccountById(@PathVariable String id) {
        AccountModel accountModel = this.accountRepository.findById(id).orElseThrow();
        return ResponseEntity.ok(accountModel);
    }

    @PostMapping
    public ResponseEntity<AccountModel> createAccount(@RequestBody AccountModel accountModel) {
       AccountModel addedAccountModel = this.accountRepository.save(accountModel);
       return ResponseEntity.ok(addedAccountModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountModel> updateAccount(@PathVariable String id, @RequestBody AccountModel accountModel) {
        AccountModel account = this.accountRepository.findById(id).orElseThrow();
        account.setCustomer(accountModel.getCustomer());
        account.setManager(accountModel.getManager());
        account.setLimitAmount(accountModel.getLimitAmount());
        account.setBalance(accountModel.getBalance());
        return ResponseEntity.ok(this.accountRepository.save(account));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable String id) {
        this.accountRepository.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }

}
