package ru.kaznacheev.wallet.authservice.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.kaznacheev.wallet.authservice.service.JwtService;
import ru.kaznacheev.wallet.common.exception.UnauthorizedException;

import java.time.Instant;
import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.lifetime.access}")
    private final Long accessTokenLifetime;
    @Value("${jwt.lifetime.refresh}")
    private final Long refreshTokenLifetime;
    @Qualifier("authMessageSource")
    private final MessageSource messageSource;
    private final Algorithm algorithm;

    @Override
    public String generateAccessToken(String userId) {
        Instant instantNow = Instant.now();
        return JWT.create()
                .withSubject(userId)
                .withExpiresAt(instantNow.plusSeconds(accessTokenLifetime))
                .withIssuedAt(instantNow)
                .withJWTId(UUID.randomUUID().toString())
                .sign(algorithm);
    }

    @Override
    public String generateRefreshToken(String userId) {
        Instant instantNow = Instant.now();
        return JWT.create()
                .withSubject(userId)
                .withExpiresAt(instantNow.plusSeconds(refreshTokenLifetime))
                .withIssuedAt(instantNow)
                .withJWTId(UUID.randomUUID().toString())
                .sign(algorithm);
    }

    @Override
    public DecodedJWT verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            return verifier.verify(token);
        } catch (TokenExpiredException exception) {
            throw new UnauthorizedException(messageSource.getMessage("exception.auth.expired-token",
                    new Object[]{}, Locale.getDefault()));
        } catch (JWTVerificationException exception) {
            throw new UnauthorizedException(messageSource.getMessage("exception.auth.invalid-token",
                    new Object[]{}, Locale.getDefault()));
        }
    }

    @Override
    public String extractSubject(String token) {
        return JWT.decode(token).getSubject();
    }


}
