package ru.kaznacheev.wallet.authservice.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.kaznacheev.wallet.authservice.model.dto.response.LoginResponse;
import ru.kaznacheev.wallet.authservice.service.JwtService;
import ru.kaznacheev.wallet.common.exception.UnauthorizedException;

import java.time.Instant;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private final String secret;
    @Value("${jwt.lifetime.access}")
    private final Long accessTokenLifetime;
    @Value("${jwt.lifetime.refresh}")
    private final Long refreshTokenLifetime;
    @Qualifier("authMessageSource")
    private final MessageSource messageSource;
    private final RedisTemplate<String, String> redisTemplate;
    private Algorithm algorithm;

    @PostConstruct
    public void init() {
        algorithm = Algorithm.HMAC256(secret);
    }

    @Override
    public String generateAccessToken(String userId) {
        Instant instantNow = Instant.now();
        return JWT.create()
                .withSubject(userId)
                .withExpiresAt(instantNow.plusSeconds(accessTokenLifetime))
                .withIssuedAt(instantNow)
                .sign(algorithm);
    }

    @Override
    public String generateRefreshToken(String userId) {
        Instant instantNow = Instant.now();
        String token = JWT.create()
                .withSubject(userId)
                .withExpiresAt(instantNow.plusSeconds(refreshTokenLifetime))
                .withIssuedAt(instantNow)
                .sign(algorithm);
        redisTemplate.opsForValue().set("refresh:" + userId, token, refreshTokenLifetime, TimeUnit.SECONDS);
        return token;
    }

    @Override
    public LoginResponse refresh(String refreshToken) {
        String userId = verify(refreshToken).getSubject();
        String key = "refresh" + userId;
        String token = redisTemplate.opsForValue().get(key);
        if (token == null || !token.equals(refreshToken)) {
            throw new UnauthorizedException(messageSource.getMessage("exception.auth.invalid-token",
                    new Object[]{}, Locale.getDefault()));
        }
        redisTemplate.delete(key);
        return new LoginResponse(generateAccessToken(userId), generateRefreshToken(userId));
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


}
