package com.smallworld.data;

import lombok.Data;

@Data
public class Transaction {
    // Represent your transaction data here.
    int mtn;
    double amount;
    String senderFullName;
    int senderAge;
    String beneficiaryFullName;
    int beneficiaryAge;
    int issueId;
    boolean issueSolved;
    String issueMessage;
}
