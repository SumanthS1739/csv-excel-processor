package com.training.codingstandards;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.HexFormat;

public class SecurityUtil {

    private static final SecureRandom RANDOM = new SecureRandom();

    public static String hashIdentifier(String value) {
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256")
                    .digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is not available", e);
        }
    }

    public static String sessionToken() {
        byte[] token = new byte[32];
        RANDOM.nextBytes(token);
        return HexFormat.of().formatHex(token);
    }

    public static boolean isAdmin(String password) {
        String configuredPassword = System.getenv("APP_ADMIN_PASSWORD");
        return configuredPassword != null && configuredPassword.equals(password);
    }
}
