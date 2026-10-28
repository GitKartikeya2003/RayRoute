package com.example.PayRouteBackend.transaction;


import com.example.PayRouteBackend.common.ApiException;
import com.example.PayRouteBackend.payment.Payment;
import com.example.PayRouteBackend.payment.PaymentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class TransactionService {

    private final PaymentService paymentService;
    private final LedgerEntryRepository ledgerEntryRepository;


    public TransactionService(PaymentService paymentService, LedgerEntryRepository ledgerEntryRepository) {
        this.paymentService = paymentService;
        this.ledgerEntryRepository = ledgerEntryRepository;
    }


    public List<Payment> list(String merchantId, String status, LocalDate from, LocalDate to) {


        if (from != null && to != null && from.isAfter(to)) {

            throw ApiException.bad("from cannot be after to date");
        }

        return paymentService.list(merchantId).stream()
                .filter(p -> status == null || status.isBlank() || status.equals(p.status))
                .filter(p -> from == null || !p.createdAt.isBefore(from.atStartOfDay(ZoneOffset.UTC).toInstant()))
                .filter(p -> to == null || p.createdAt.isBefore(to.plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant()))
                .toList();

    }


    public List<LedgerEntry> ledger(String merchantId) {

        return ledgerEntryRepository.findByMerchantIdOrderByCreatedAtDesc(merchantId);


    }
}
