package com.bantads.account.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountModel {
    @Id
    private String uuid = java.util.UUID.randomUUID().toString();

    private String customer;
    private String manager;
    private Double limitAmount;
    private Double balance;

}
