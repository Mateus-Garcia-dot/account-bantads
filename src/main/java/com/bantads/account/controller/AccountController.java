package com.bantads.account.controller;

import com.bantads.account.model.AccountModel;
import com.bantads.account.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController()
@RequestMapping("/account")
public class AccountController {

    private final AccountRepository accountRepository;

    @GetMapping
    public List<AccountModel> getAllAccounts() {
        return this.accountRepository.findAll();
    }

    @PostMapping
    public AccountModel createAccount(AccountModel accountModel) {
        return this.accountRepository.save(accountModel);
    }

    @PutMapping("/{id}")
    public AccountModel updateAccount(@PathVariable Long id, @RequestBody AccountModel accountModel) {
        AccountModel account = this.accountRepository.findById(id).orElseThrow();
        account.setCliente(accountModel.getCliente());
        account.setGerente(accountModel.getGerente());
        account.setLimite(accountModel.getLimite());
        account.setSaldo(accountModel.getSaldo());
        return this.accountRepository.save(account);
    }
}
