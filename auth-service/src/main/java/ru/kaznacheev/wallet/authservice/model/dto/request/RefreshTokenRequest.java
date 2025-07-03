package ru.kaznacheev.wallet.authservice.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RefreshTokenRequest {

    @NotBlank(message = "{validation.refresh-token.not-blank}")
    private final String refreshToken;

}
