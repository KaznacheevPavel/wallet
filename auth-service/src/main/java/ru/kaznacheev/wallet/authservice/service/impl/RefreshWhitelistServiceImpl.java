package ru.kaznacheev.wallet.authservice.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.kaznacheev.wallet.authservice.service.RefreshWhitelistService;
import ru.kaznacheev.wallet.common.exception.UnauthorizedException;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RefreshWhitelistServiceImpl implements RefreshWhitelistService {

    private final RedisTemplate<String, String> redisTemplate;
    @Value("${jwt.redis.keys.refresh}")
    private final String keyPattern;
    @Value("${jwt.lifetime.refresh}")
    private final long refreshTokenLifetime;
    @Qualifier("authMessageSource")
    private final MessageSource messageSource;


    @Override
    public void saveToken(String accessToken, String refreshToken) {
        DecodedJWT decodedJWT = JWT.decode(refreshToken);
        String userId = decodedJWT.getSubject();
        String refreshJti = decodedJWT.getClaim("jti").asString();
        String accessJti = JWT.decode(accessToken).getClaim("jti").asString();
        String key = createKey(userId, refreshJti);
        redisTemplate.opsForValue().set(key, accessJti, refreshTokenLifetime, TimeUnit.SECONDS);
    }

    @Override
    public String revokeToken(String refreshToken) {
        DecodedJWT decodedJWT = JWT.decode(refreshToken);
        String userId = decodedJWT.getSubject();
        String jti = decodedJWT.getClaim("jti").asString();
        String key = createKey(userId, jti);
        String accessJti = redisTemplate.opsForValue().getAndDelete(key);
        if (accessJti == null) {
            throw new UnauthorizedException(messageSource.getMessage("exception.auth.invalid-token",
                    new Object[]{}, Locale.getDefault()));
        }
        return accessJti;
    }

    private String createKey(String userId, String jti) {
        return keyPattern
                .replace("{userId}", userId)
                .replace("{jti}", jti);
    }

}
