package com.example.PayRouteBackend.refund;


import com.example.PayRouteBackend.merchant.MerchantService;
import com.example.PayRouteBackend.payment.PaymentService;
import com.example.PayRouteBackend.transaction.LedgerService;
import org.springframework.stereotype.Service;

@Service
public class RefundService {


    private final RefundRepository refundRepository;
    private final MerchantService merchantService;
    private final PaymentService paymentService;
    private final LedgerService ledgerService;

    public RefundService(RefundRepository refundRepository, MerchantService merchantService, PaymentService paymentService, LedgerService ledgerService) {
        this.refundRepository = refundRepository;
        this.merchantService = merchantService;
        this.paymentService = paymentService;
        this.ledgerService = ledgerService;
    }



}
