package ru.kaznacheev.wallet.authservice.service;

import ru.kaznacheev.wallet.authservice.model.dto.request.RegistrationRequest;
import ru.kaznacheev.wallet.authservice.model.dto.response.RegistrationResponse;

public interface AuthService {

    RegistrationResponse register(RegistrationRequest request);

}
