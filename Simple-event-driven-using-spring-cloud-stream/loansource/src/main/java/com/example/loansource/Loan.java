package com.example.loansource;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Loan {
    private String uuid;
    private String name;
    private String status;
    private long amount;

    public Loan(String uuid, String name, long amount) {
        this.uuid = uuid;
        this.name = name;
        this.amount = amount;
        this.setStatus(Statuses.PENDING.name());
    }
}