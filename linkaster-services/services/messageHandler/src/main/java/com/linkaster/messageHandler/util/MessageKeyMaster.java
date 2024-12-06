package com.linkaster.messageHandler.util;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/*
 * This is a utility class that provides methods to generate RSA key pairs.
 */

  /*
 *  Title: MessageKeyMaster.java
 *  Author: Ortega Mendoza, Javier
 *  Date: 2024
 *  Code Version: 1.0
 *  Availability: https://github.com/javsort/Linkaster
 */
@Slf4j
@Component
public class MessageKeyMaster {

    private KeyPair applicationKeyPair;

    private static final String RSA_TRANSFORMATION = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
    private static final String AES_TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int AES_KEY_SIZE = 256;
    private static final int RSA_KEY_SIZE = 2048;
    private static final int GCM_IV_LENGTH = 12;

    private final String log_header = "MessageKeyMaster --- ";

    /*
     * Key Master methods
     */
    // Constructor -> generate application key pair on every startup
    public MessageKeyMaster() {
        // Generate key pair
        KeyPairGenerator keyGenerator;
        log.info(log_header + "Generating application key pair");
        try {
            keyGenerator = KeyPairGenerator.getInstance("RSA");
            keyGenerator.initialize(RSA_KEY_SIZE);
            this.applicationKeyPair = keyGenerator.generateKeyPair();

        } catch (NoSuchAlgorithmException e) {
            log.error("Error generating application key pair", e);
        
        }
        
        if (this.applicationKeyPair != null) {
            log.info("Application Key Pair generated successfully");
        } else {
            log.error("Application Key Pair generation failed");
        }
    }

    // Encode the key pair
    public String encodePublic(PublicKey pubKey) {
        return Base64.getEncoder().encodeToString(pubKey.getEncoded());
    }

    public String encodePrivate(PrivateKey privKey) {
        return Base64.getEncoder().encodeToString(privKey.getEncoded());
    }

    /* 
    * Private Chat Ops -> RSA Encryption
    */
    public KeyPair decodePair(String publicKey, String privateKey) throws Exception {
        PublicKey pubKey = KeyFactory.getInstance("RSA")
            .generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey)));
        PrivateKey privKey = KeyFactory.getInstance("RSA")
            .generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
        return new KeyPair(pubKey, privKey);
    }
    
    private PublicKey decodePublic(String publicKey) throws Exception {
        return KeyFactory.getInstance("RSA")
            .generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey)));
    }

    private PrivateKey decodePrivate(String privateKey) throws Exception {
        return KeyFactory.getInstance("RSA")
            .generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
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
     * Group Chat extra functionality -> Hybrid Encryption
     */
    public String encryptAESKeyWithAppPublicKey() throws Exception {
        // Get the application public key
        PublicKey publicKey = this.applicationKeyPair.getPublic();

        // Generate a new AES Key
        SecretKey moduleAESKey = generateGroupChatKey();

        // Encrypt the AES Key with the application public key
        Cipher cipher = Cipher.getInstance(RSA_TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedAESKey = cipher.doFinal(moduleAESKey.getEncoded());
        
        return Base64.getEncoder().encodeToString(encryptedAESKey);
    }

    public String encryptMessageWithAESKey(String message, String encryptedKey) throws Exception {
        log.info(log_header + "Encrypting message with AES Key");
        // Get key out of byte array -> decrypt with app private key
        PrivateKey privateKey = this.applicationKeyPair.getPrivate();

        // Decrypt the AES key
        log.info(log_header + "Decrypting AES Key");
        Cipher cipher = Cipher.getInstance(RSA_TRANSFORMATION);
        log.info(log_header + "Got cipher instance");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        log.info(log_header + "Initialized cipher");
        byte[] aesKeyBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedKey));
        log.info(log_header + "AES Key decrypted successfully: " + aesKeyBytes);

        // Reconstruct the key
        SecretKey aesKey = new SecretKeySpec(aesKeyBytes, "AES");

        log.info(log_header + "AES Key decrypted successfully: " + aesKey + "key to str: " + aesKey.toString());

        // Encrypt the message with the AES key
        Cipher aesCipher = Cipher.getInstance(AES_TRANSFORMATION);
        aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);
        byte[] encryptedMessage = aesCipher.doFinal(message.getBytes());

        log.info(log_header + "Message encrypted successfully" + encryptedMessage);
        return Base64.getEncoder().encodeToString(encryptedMessage);
    }

    public SecretKey generateGroupChatKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(AES_KEY_SIZE); 

        return keyGen.generateKey();
    }
}