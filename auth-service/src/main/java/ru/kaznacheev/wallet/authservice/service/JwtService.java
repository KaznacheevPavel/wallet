package ru.kaznacheev.wallet.authservice.service;

import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.UUID;

public interface JwtService {

    String generateAccessToken(UUID id);

    String generateRefreshToken(UUID id);

    DecodedJWT decode(String token);

}
