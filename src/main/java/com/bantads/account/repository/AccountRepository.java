package com.bantads.account.repository;

import com.bantads.account.model.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountModel, String> {

}
