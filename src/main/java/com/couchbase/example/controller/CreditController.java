package com.couchbase.example.controller;

import com.couchbase.example.model.TransferCreditService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/credit")
public class CreditController {

    @Autowired
    private TransferCreditService transferCreditService;

    @RequestMapping("/transfer")
    public boolean transferCredit(@RequestParam("sourceUser") String sourceUser,
                                  @RequestParam("targetUser") String targetUser,
                                  @RequestParam("amount") int amount) {

        try {
            transferCreditService.transferCredit(sourceUser, targetUser, amount);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
