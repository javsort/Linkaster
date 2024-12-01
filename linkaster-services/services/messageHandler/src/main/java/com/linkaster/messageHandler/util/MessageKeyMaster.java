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
@Slf4j
@Component
public class MessageKeyMaster {

    private KeyPair applicationKeyPair;

    /*
     * Key Master methods
     */
    // Constructor -> generate application key pair on every startup
    public MessageKeyMaster() {
        // Generate key pair
        KeyPairGenerator keyGenerator;
        try {
            keyGenerator = KeyPairGenerator.getInstance("RSA");
            keyGenerator.initialize(2048);
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
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedAESKey = cipher.doFinal(moduleAESKey.getEncoded());
        
        return Base64.getEncoder().encodeToString(encryptedAESKey);
    }

    public String encryptMessageWithAESKey(String message, String encryptedKey) throws Exception {
        // Get key out of byte array -> decrypt with app private key
        PrivateKey privateKey = this.applicationKeyPair.getPrivate();

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decodedKey = cipher.doFinal(Base64.getDecoder().decode(encryptedKey));

        // Reconstruct the key
        SecretKey moduleAESKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

        // Encrypt the message with the AES key
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.ENCRYPT_MODE, moduleAESKey);
        byte[] encryptedMessage = aesCipher.doFinal(message.getBytes());

        return Base64.getEncoder().encodeToString(encryptedMessage);
    }

    public SecretKey generateGroupChatKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); 

        return keyGen.generateKey();
    }
}