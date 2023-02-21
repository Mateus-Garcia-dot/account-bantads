package com.bantads.account.controller;

import com.bantads.account.model.AccountByManager;
import com.bantads.account.model.AccountModel;
import com.bantads.account.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController()
@RequestMapping("/account")
public class AccountController {

    private AccountRepository accountRepository;

    @GetMapping("/customer/{id}")
    public ResponseEntity<AccountModel> getAccountByCustomer(@PathVariable String id) {
        List<AccountModel> accountModelList = this.accountRepository.findByCustomerId(id);
        return ResponseEntity.ok(accountModelList.get(0));
    }

    @GetMapping
    public ResponseEntity<List<AccountModel>> getAllAccounts() {
        List<AccountModel> accountModelList = this.accountRepository.findAll();
        return ResponseEntity.ok(accountModelList);
    }

    @GetMapping("/count-manager")
    public ResponseEntity<List<AccountByManager>> countManager() {
       return ResponseEntity.ok(this.accountRepository.countManager());
    }

    @GetMapping("/no-managers")
    public ResponseEntity<List<AccountModel>> getNullAccounts() {
        return ResponseEntity.ok(this.accountRepository.getAccountByManagerIsNull());
    }

    @GetMapping("/manager/{id}")
    public ResponseEntity<List<AccountModel>> getAccountByManager(@PathVariable String id) {
        return ResponseEntity.ok(this.accountRepository.findByManager(id));
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

    @PatchMapping("/{id}")
    public ResponseEntity<AccountModel> patchAccount(@PathVariable String id, @RequestBody AccountModel accountModel) {
        System.out.println(accountModel);
        List<AccountModel> accounts = this.accountRepository.findByCustomerId(id);
        System.out.println(accounts);
        if (accounts.isEmpty()) {
            return ResponseEntity.notFound().build();
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
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/manager/{id}")
    public ResponseEntity<String> deleteManager(@PathVariable String id) {
        this.accountRepository.nullifyManager(id);
        return ResponseEntity.ok("Deleted");
    }
}
