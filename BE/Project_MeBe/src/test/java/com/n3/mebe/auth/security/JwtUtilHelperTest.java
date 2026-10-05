package com.n3.mebe.auth.security;

import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

class JwtUtilHelperTest {

    private JwtUtilHelper jwtUtilHelper;

    @BeforeEach
    void setUp() {
        jwtUtilHelper = new JwtUtilHelper();
        String key = Encoders.BASE64.encode(Jwts.SIG.HS256.key().build().getEncoded());
        ReflectionTestUtils.setField(jwtUtilHelper, "privateKey", key);
    }

    @Test
    void generatedToken_isValid() {
        String token = jwtUtilHelper.generateToken("admin");

        assertThat(jwtUtilHelper.verifyToken(token)).isTrue();
    }

    @Test
    void tamperedToken_isInvalid() {
        String token = jwtUtilHelper.generateToken("admin");
        String tampered = token.substring(0, token.length() - 2) + (token.endsWith("A") ? "BB" : "AA");

        assertThat(jwtUtilHelper.verifyToken(tampered)).isFalse();
    }

    @Test
    void garbage_isInvalid() {
        assertThat(jwtUtilHelper.verifyToken("not-a-jwt")).isFalse();
    }
}
