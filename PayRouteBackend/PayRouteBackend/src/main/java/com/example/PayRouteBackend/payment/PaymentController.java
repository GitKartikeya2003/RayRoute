package com.example.PayRouteBackend.payment;


import com.example.PayRouteBackend.merchant.MerchantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final MerchantService merchantService;


    public PaymentController(PaymentService paymentService, MerchantService merchantService) {
        this.paymentService = paymentService;
        this.merchantService = merchantService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Payment create(@RequestHeader("X-Api-Key") String key,
                          @RequestHeader("Idempotency-Key") String IK,
                          @Valid @RequestBody PaymentDtos.Create r) {

        return paymentService.create(merchantService.authenticate(key).id, IK, r);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Payment get(@RequestHeader("X-Api-Key") String key,
                       @RequestHeader("Idempotency-Key") String IK) {

        return paymentService.get(merchantService.authenticate(key).id, IK);

    }

    @PostMapping("/{id}/process")
    public Payment process(@RequestHeader("X-Api-Key") String key,
                           @PathVariable String id) {

        return paymentService.process(merchantService.authenticate(key).id, id);

    }

    @PostMapping("/{id}/check")
    public Payment check(@RequestHeader("X-Api-Key") String key, @PathVariable String id) {
        return paymentService.reconcile(merchantService.authenticate(key).id, id);
    }


}
