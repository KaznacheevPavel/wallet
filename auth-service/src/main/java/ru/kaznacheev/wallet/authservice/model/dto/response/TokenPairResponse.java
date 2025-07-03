package ru.kaznacheev.wallet.authservice.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenPairResponse {

    private final String accessToken;

    private final String refreshToken;

}
