package com.training.codingstandards;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SecurityUtilTest {

    @Test
    void hashesIdentifiersWithStableSha256Output() {
        String hash = SecurityUtil.hashIdentifier("1007grace@example.com");

        assertEquals(64, hash.length());
        assertEquals(hash, SecurityUtil.hashIdentifier("1007grace@example.com"));
    }

    @Test
    void generatesDistinctTokens() {
        String first = SecurityUtil.sessionToken();
        String second = SecurityUtil.sessionToken();

        assertEquals(64, first.length());
        assertNotEquals(first, second);
        assertTrue(first.matches("[0-9a-f]+"));
    }
}