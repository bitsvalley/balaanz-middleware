package com.bitsvalley.micro.utils;

import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.jwt.consumer.JwtContext;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Optional;
import java.util.regex.Matcher;


public class SignatureGenerator {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy HH:mm:ss");

    public static String generateSignature(String FOLELOGIX_HMAC_SIGNATURE, String jwt) throws NoSuchAlgorithmException, InvalidKeyException {
        String valueToDigest = getMessageDigest(jwt);
        String messageDigest = generateHmacSignature(valueToDigest, getKey(FOLELOGIX_HMAC_SIGNATURE));
        return messageDigest;
    }

    private static String generateHmacSignature(String message, byte[] key) throws InvalidKeyException, NoSuchAlgorithmException {
        byte[] bytes = hmac("HmacSHA256", key, message.getBytes());
        return "signature=" + Base64.getEncoder().encodeToString(bytes);
    }

    private static byte[] hmac(String algorithm, byte[] key, byte[] message) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(key, algorithm));
        return mac.doFinal(message);
    }

    private static byte[] getKey(String FOLELOGIX_HMAC_SIGNATURE) {
        return FOLELOGIX_HMAC_SIGNATURE.getBytes(StandardCharsets.UTF_8);
    }


    private static String getMessageDigest(String jwt) {
        long tokenTimestamp = getTokenTimestamp(jwt);
        LocalDateTime localDateTime = convertMillisToDateTimeUTC(tokenTimestamp);
        String formattedDateTime = localDateTime.format(formatter);
        return formattedDateTime;
    }

    private static LocalDateTime convertMillisToDateTimeUTC(long milliseconds) {
        Instant instant = Instant.ofEpochMilli(milliseconds);
        return LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
    }

    public static long getTokenTimestamp(String jwt) {
        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setSkipSignatureVerification()
                .build();
        try {
            JwtContext jwtContext = jwtConsumer.process(jwt);
            JwtClaims jwtClaims = jwtContext.getJwtClaims();
            return jwtClaims.getClaimValue("timestamp", Long.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0l;
    }
}