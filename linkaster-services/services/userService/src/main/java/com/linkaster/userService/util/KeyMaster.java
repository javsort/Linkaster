package com.linkaster.userService.util;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/*
 * This is a utility class that provides methods to generate RSA key pairs.
 */
@Slf4j
@Component
public class KeyMaster {

    /*
     * Key related methods
     */
    // Generate RSA key pair for the new user
    public KeyPair keyGenerator() throws Exception {
        // Generate key pair
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(2048);

        return keyGenerator.generateKeyPair();
    }

    // Encode the key pair
    public String encodePublic(PublicKey pubKey) {
        String publicKey = Base64.getEncoder().encodeToString(pubKey.getEncoded());
        return publicKey;
    }

    public String encodePrivate(PrivateKey privKey) {
        String privateKey = Base64.getEncoder().encodeToString(privKey.getEncoded());
        return privateKey;
    }

    // Decode the key pair
    public KeyPair decodePair(String publicKey, String privateKey) throws Exception {
        PublicKey pubKey = KeyFactory.getInstance("RSA")
            .generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey)));
        PrivateKey privKey = KeyFactory.getInstance("RSA")
            .generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
        return new KeyPair(pubKey, privKey);
    }
    
    private PublicKey decodePublic(String publicKey) throws Exception {
        PublicKey pubKey = KeyFactory.getInstance("RSA")
            .generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey)));
        return pubKey;
    }

    private PrivateKey decodePrivate(String privateKey) throws Exception {
        PrivateKey privKey = KeyFactory.getInstance("RSA")
            .generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
        return privKey;
    }


    // Encryption + Decryption
    public String encryptMessage(String message, String codedPublic) throws Exception {
        PublicKey publicKey = decodePublic(codedPublic);

        Cipher cipher = Cipher.getInstance("RSA");

        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        
        byte[] encryptedMessage = cipher.doFinal(message.getBytes());
        
        return Base64.getEncoder().encodeToString(encryptedMessage);
    }

    public String decryptMessage(String encryptedMessage, String codedPrivate) throws Exception {
        PrivateKey privateKey = decodePrivate(codedPrivate);

        Cipher cipher = Cipher.getInstance("RSA");

        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        
        byte[] decryptedMessage = cipher.doFinal(Base64.getDecoder().decode(encryptedMessage));
        
        return new String(decryptedMessage);
    }


    /*
     * Password related methods
     */
    // Hash the password
    public String receivePasswordToHash(String password){
        return convertPassword(password);
    }

    private String convertPassword(String password){
        // Encrypt password
        int encryptionStrength = 10;

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(encryptionStrength, new SecureRandom());

        return bCryptPasswordEncoder.encode(password);
    }
}
