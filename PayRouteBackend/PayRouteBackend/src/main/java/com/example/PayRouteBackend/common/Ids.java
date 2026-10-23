package com.example.PayRouteBackend.common;


import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
public class Ids {

    public static String next(String prefix) {

        return prefix + "_" + UUID.randomUUID().toString().replace("-", "");
    }
}
