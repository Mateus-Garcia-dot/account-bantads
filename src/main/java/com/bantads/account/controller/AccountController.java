package com.bantads.account.controller;

import com.bantads.account.model.AccountModel;
import com.bantads.account.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.EmptyStackException;
import java.util.List;

@AllArgsConstructor
@RestController()
@RequestMapping("/account")
public class AccountController {

    private final AccountRepository accountRepository;

    @GetMapping
    public ResponseEntity<List<AccountModel>> getAllAccounts() {
        List<AccountModel> accountModelList = this.accountRepository.findAll();
        if (accountModelList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        accountModelList.sort((a, b) -> a.getId().compareTo(b.getId()));
        return ResponseEntity.ok(accountModelList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountModel> getAccountById(@PathVariable Long id) {
        AccountModel accountModel = this.accountRepository.findById(id).orElse(null);
        if (accountModel == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(accountModel);
    }

    @PostMapping
    public ResponseEntity<AccountModel> createAccount(@RequestBody AccountModel accountModel) {
       AccountModel addedAccountModel = this.accountRepository.save(accountModel);
       return ResponseEntity.ok(addedAccountModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountModel> updateAccount(@PathVariable Long id, @RequestBody AccountModel accountModel) {
        AccountModel account = this.accountRepository.findById(id).orElse(null);
        System.out.println(account);
        if (account == null) {
            return ResponseEntity.notFound().build();
        }
        account.setCliente(accountModel.getCliente());
        account.setGerente(accountModel.getGerente());
        account.setLimite(accountModel.getLimite());
        account.setSaldo(accountModel.getSaldo());
        return ResponseEntity.ok(this.accountRepository.save(account));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        try {
            this.accountRepository.deleteById(id);
        } catch(Exception e) {
            if (e instanceof EmptyResultDataAccessException) {
                return ResponseEntity.notFound().build();
            }
            throw e;
        }
        return ResponseEntity.ok("Deleted");
    }

}
