package ru.kaznacheev.wallet.authservice.service;

import ru.kaznacheev.wallet.authservice.model.dto.request.LoginRequest;
import ru.kaznacheev.wallet.authservice.model.dto.request.RegistrationRequest;
import ru.kaznacheev.wallet.authservice.model.dto.response.LoginResponse;
import ru.kaznacheev.wallet.authservice.model.dto.response.RegistrationResponse;

public interface AuthService {

    RegistrationResponse register(RegistrationRequest request);

    LoginResponse login(LoginRequest request);

}
