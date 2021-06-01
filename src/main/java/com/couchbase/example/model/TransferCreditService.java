package com.couchbase.example.model;

public interface TransferCreditService {

    void transferCredit(String sourceUser, String targetUser, int creditsToTransfer);
}
