package ru.kaznacheev.wallet.authservice.service;

import ru.kaznacheev.wallet.authservice.model.dto.request.LoginRequest;
import ru.kaznacheev.wallet.authservice.model.dto.request.RefreshTokenRequest;
import ru.kaznacheev.wallet.authservice.model.dto.request.RegistrationRequest;
import ru.kaznacheev.wallet.authservice.model.dto.response.TokenPairResponse;
import ru.kaznacheev.wallet.authservice.model.dto.response.RegistrationResponse;

public interface AuthService {

    RegistrationResponse register(RegistrationRequest request);

    TokenPairResponse login(LoginRequest request);

    TokenPairResponse refresh(RefreshTokenRequest request);

}
