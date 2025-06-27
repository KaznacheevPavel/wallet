package ru.kaznacheev.wallet.authservice.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class RegistrationResponse {

    private final UUID id;

}
