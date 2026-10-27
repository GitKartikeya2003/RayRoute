package com.example.PayRouteBackend.order;


import com.example.PayRouteBackend.common.ApiException;
import com.example.PayRouteBackend.common.Ids;
import com.example.PayRouteBackend.merchant.MerchantService;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final PaymentOrderRepository paymentOrderRepository;

    private final MerchantService merchantService;


    public OrderService(PaymentOrderRepository paymentOrderRepository, MerchantService merchantService) {
        this.paymentOrderRepository = paymentOrderRepository;
        this.merchantService = merchantService;
    }

    @Transactional
    public PaymentOrder create(String merchantId, OrderDtos.Create createRequest) {

        merchantService.lock(merchantId);
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.id = Ids.next("order");
        paymentOrder.merchantId = merchantId;
        paymentOrder.amount = createRequest.amount();
        paymentOrder.receipt = createRequest.receipt();
        return paymentOrderRepository.save(paymentOrder);


    }

    public PaymentOrder get(String merchantId, String id) {

        PaymentOrder order = paymentOrderRepository.findById(id).orElseThrow(ApiException::missing);
        if (!order.merchantId.equals(merchantId)) throw ApiException.missing();
        return order;


    }

    public List<PaymentOrder> getPayments(String merchantId) {

        return paymentOrderRepository.findByMerchantIdOrderByCreatedAtDesc(merchantId);

    }


}
