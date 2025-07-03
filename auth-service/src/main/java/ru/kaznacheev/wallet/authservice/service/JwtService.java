package ru.kaznacheev.wallet.authservice.service;

import com.auth0.jwt.interfaces.DecodedJWT;

public interface JwtService {

    String generateAccessToken(String userId);

    String generateRefreshToken(String userId);

    DecodedJWT verify(String token);

    String extractSubject(String token);

}
