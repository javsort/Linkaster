package com.linkaster.userService.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

import lombok.extern.slf4j.Slf4j;

/*
 * This is a utility class that provides methods to generate RSA key pairs.
 */
@Slf4j
public class KeyMaster {

    // Generate RSA key pair for the new user
    public KeyPair keyGenerator() throws Exception {
        // Generate key pair
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(2048);

        return keyGenerator.generateKeyPair();
    }
}
