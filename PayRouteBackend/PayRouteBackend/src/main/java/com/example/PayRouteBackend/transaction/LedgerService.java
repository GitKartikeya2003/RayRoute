package com.example.PayRouteBackend.transaction;


import com.example.PayRouteBackend.common.Ids;
import com.example.PayRouteBackend.merchant.Merchant;
import org.springframework.stereotype.Service;

@Service
public class LedgerService {


    private final LedgerEntryRepository repository;


    public LedgerService(LedgerEntryRepository repository) {
        this.repository = repository;
    }


    public void record(Merchant m, String reference, String type, long delta) {

        m.balance = Math.addExact(m.balance, delta);

        LedgerEntry t = new LedgerEntry();
        t.id = Ids.next("txn");
        t.merchantId = m.id;
        t.referenceId = reference;
        t.type = type;
        t.amount = delta;
        t.balanceAfter = m.balance;
        repository.save(t);
    }
}
