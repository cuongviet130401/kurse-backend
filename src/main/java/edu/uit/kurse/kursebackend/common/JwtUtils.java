package edu.uit.kurse.kursebackend.common;

import edu.uit.kurse.kursebackend.model.persistence.AccountPersistenceEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;

import java.util.Date;

public class JwtUtils {

    private static final long EXPIRE_DURATION_1_HOUR = 60 * 60 * 1000;

    public static String issueAuthenticatedAccessToken(AccountPersistenceEntity account) {
        return Jwts.builder()
                .setSubject(account.getId() + "~" + account.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION_1_HOUR))
                .compact();
    }

    public static DefaultClaims decodeJwtToken(String jwtToken) {
        return (DefaultClaims) Jwts.parserBuilder()
                .build()
                .parse(jwtToken)
                .getBody();
    }

}
