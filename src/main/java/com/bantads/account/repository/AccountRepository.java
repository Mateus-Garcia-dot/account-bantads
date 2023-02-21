package com.bantads.account.repository;

import com.bantads.account.model.AccountByManager;
import com.bantads.account.model.AccountModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Transactional
public interface AccountRepository extends JpaRepository<AccountModel, String> {
    @Query(value = "SELECT new com.bantads.account.model.AccountByManager(manager, COUNT(*)) FROM AccountModel WHERE manager != null GROUP BY manager ORDER BY count(*) ASC")
    public List<AccountByManager> countManager();

    @Query("SELECT a FROM AccountModel a WHERE a.manager IS NULL")
    public List<AccountModel> getAccountByManagerIsNull();

    @Query("SELECT a FROM AccountModel a WHERE a.customer = ?1")
    public List<AccountModel> findByCustomerId(String consumerId);

    @Modifying
    @Query("UPDATE AccountModel a SET a.manager = null WHERE a.manager = ?1")
    public void nullifyManager(String manager);

    @Query("SELECT a FROM AccountModel a WHERE a.manager = ?1")
    public List<AccountModel> findByManager(String manager);
}
