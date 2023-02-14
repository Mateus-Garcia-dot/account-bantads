package com.bantads.account.repository;

import com.bantads.account.model.AccountByManager;
import com.bantads.account.model.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<AccountModel, String> {
    @Query(value = "SELECT new com.bantads.account.model.AccountByManager(manager, COUNT(*)) FROM AccountModel WHERE manager != null GROUP BY manager ORDER BY count(*) ASC")
    public List<AccountByManager> countManager();
    // get all accounts where manager is the same as null not native query
    @Query("SELECT a FROM AccountModel a WHERE a.manager IS NULL")
    public List<AccountModel> getAccountByManagerIsNull();
}
