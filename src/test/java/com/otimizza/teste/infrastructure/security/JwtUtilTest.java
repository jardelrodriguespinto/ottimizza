package com.otimizza.teste.infrastructure.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private static final String SECRET = "TestingJWTSecretKeyForHmacSHA256Minimum32Chars";
    private static final long EXPIRATION = 3_600_000L;

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil(SECRET, EXPIRATION);
    }

    @Test
    @DisplayName("Should generate a valid token and extract username")
    void shouldGenerateTokenAndExtractUsername() {
        String token = jwtUtil.generateToken("admin");
        assertEquals("admin", jwtUtil.extractUsername(token));
    }

    @Test
    @DisplayName("Should validate a token for the correct user")
    void shouldValidateTokenForCorrectUser() {
        String token = jwtUtil.generateToken("admin");
        assertTrue(jwtUtil.isTokenValid(token, "admin"));
    }

    @Test
    @DisplayName("Should reject token for wrong username")
    void shouldRejectTokenForWrongUsername() {
        String token = jwtUtil.generateToken("admin");
        assertFalse(jwtUtil.isTokenValid(token, "other"));
    }

    @Test
    @DisplayName("Should return false for malformed token")
    void shouldReturnFalseForMalformedToken() {
        assertFalse(jwtUtil.isTokenValid("not.a.valid.jwt", "admin"));
    }
}
