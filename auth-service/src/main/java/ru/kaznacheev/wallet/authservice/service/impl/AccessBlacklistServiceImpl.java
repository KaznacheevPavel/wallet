package ru.kaznacheev.wallet.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.kaznacheev.wallet.authservice.service.AccessBlacklistService;
import ru.kaznacheev.wallet.common.exception.UnauthorizedException;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AccessBlacklistServiceImpl implements AccessBlacklistService {

    private final RedisTemplate<String, String> redisTemplate;
    @Value("${jwt.redis.keys.blacklist}")
    private final String keyPattern;
    @Value("${jwt.lifetime.access}")
    private final long accessTokenLifetime;
    @Qualifier("authMessageSource")
    private final MessageSource messageSource;

    @Override
    public void addToBlacklist(String jti) {
        String key = createKey(jti);
        redisTemplate.opsForValue().set(key, "", accessTokenLifetime, TimeUnit.SECONDS);
    }

    @Override
    public void verify(String jti) {
        String key = createKey(jti);
        if (redisTemplate.hasKey(key)) {
            throw new UnauthorizedException(messageSource.getMessage("exception.auth.invalid-token",
                    new Object[]{}, Locale.getDefault()));
        }
    }

    private String createKey(String jti) {
        return keyPattern.replace("{jti}", jti);
    }

}
