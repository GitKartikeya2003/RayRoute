package com.example.PayRouteBackend.common;

public class Idempotency {

    private Idempotency() {

    }

    private static void validate(String key) {

        if (key == null || !key.matches("[a-zA-Z0-9_-]{8,100}")) {

            throw ApiException.bad("Idempotency key not Valid . " +
                    "it must contain at least 8 to 100 chars letters,digits,underscores or hyphens");
        }

    }

}
