package ru.kaznacheev.wallet.authservice.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import ru.kaznacheev.wallet.authservice.model.dto.response.LoginResponse;

public interface JwtService {

    String generateAccessToken(String userId);

    String generateRefreshToken(String userId);

    LoginResponse refresh(String refreshToken);

    DecodedJWT verify(String token);

}
