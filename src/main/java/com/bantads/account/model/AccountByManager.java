package com.bantads.account.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountByManager {
    private String manager;
    private long count;
}
