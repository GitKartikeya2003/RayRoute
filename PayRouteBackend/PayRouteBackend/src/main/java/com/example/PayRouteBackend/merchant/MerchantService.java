package com.example.PayRouteBackend.merchant;


import com.example.PayRouteBackend.common.ApiException;
import com.example.PayRouteBackend.common.Ids;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Set;

@Service
public class MerchantService {


    private final MerchantRepository merchantRepository;

    private final Set<String> allowedHosts;

    public MerchantService(MerchantRepository merchantRepository, Set<String> allowedHosts) {
        this.merchantRepository = merchantRepository;
        this.allowedHosts = allowedHosts;

    }

    public static String hash(String value) {

        try {

            return HexFormat.of()
                    .formatHex(MessageDigest.getInstance("SHA-256")
                            .digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }

    }

    @Transactional
    public MerchantDtos.Registered register(MerchantDtos.Register r) {

        Merchant merchant = new Merchant();
        merchant.id = Ids.next("mer");

        merchant.name = r.name();
        merchant.email = r.email();

        String key = Ids.next("fp_test");
        merchant.apiKeyHash = hash(key);
        merchant.webhookSecret = Ids.next("whsec");

        merchantRepository.save(merchant);

        return new MerchantDtos.Registered(merchant, key, merchant.webhookSecret);
    }

    public Merchant authenticate(String apiKey) {

        if (apiKey == null || apiKey.isBlank()) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Valid X-API Key is required");
        }


        return merchantRepository.findByApiKeyHash(hash(apiKey)).orElseThrow(
                () -> new ApiException(HttpStatus.UNAUTHORIZED, "Invalid X-API Key")
        );
    }


    public Merchant lock(String id) {

        return merchantRepository.lockById(id).orElseThrow(ApiException::missing);

    }


    public void validateWebHookUrl(String value) {

        if (value == null || value.isBlank()) return;

        try {
            URI uri = URI.create(value);

            if (!Set.of("http", "https").contains(uri.getScheme())
                    || !allowedHosts.contains(uri.getHost())
                    || uri.getUserInfo() != null
                    || uri.getFragment() != null) {

                throw new IllegalStateException();

            }


        }catch (Exception e) {
            throw ApiException.bad("Webhook URL must be HTTP(s) and host in Webhook_hosts");
        }
    }


    @Transactional
    public Merchant update(String id, MerchantDtos.Update r) {

        Merchant merchant = lock(id);
        validateWebHookUrl(r.webhookUrl());
        merchant.name = r.name();
        merchant.email = r.email();
        merchant.webhookUrl = r.webhookUrl();
        return merchant;

    }


}
