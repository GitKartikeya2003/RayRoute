package com.example.PayRouteBackend.refund;


import com.example.PayRouteBackend.merchant.MerchantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/refunds")
public class RefundController {


    private final RefundService refundService;

    private final MerchantService merchantService;


    public RefundController(RefundService refundService, MerchantService merchantService) {
        this.refundService = refundService;
        this.merchantService = merchantService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Refund create(@RequestHeader("X-Api-Key") String key,
                         @RequestHeader("Idempotency-Key") String ik,
                         @Valid @RequestBody RefundDtos.Create r
    ) {
        return refundService.create(merchantService.authenticate(key).id, ik, r);
    }

    @GetMapping
    public List<Refund> list(@RequestHeader("X-Api-Key") String key) {
        return refundService.list(merchantService.authenticate(key).id);
    }


}
