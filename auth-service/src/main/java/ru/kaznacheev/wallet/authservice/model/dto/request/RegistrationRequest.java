package ru.kaznacheev.wallet.authservice.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.kaznacheev.wallet.common.validation.constraint.ConfigurablePattern;
import ru.kaznacheev.wallet.common.validation.constraint.ConfigurableSize;

@AllArgsConstructor
@Getter
public class RegistrationRequest {

    @NotBlank(message = "{validation.credential.email.not-blank}")
    @ConfigurableSize(max = "credential.email.max-size", message = "{validation.credential.email.max-size}")
    @ConfigurablePattern(regexp = "credential.email.pattern", message = "{validation.credential.email.pattern}")
    private final String email;

    @NotEmpty(message = "{validation.credential.password.not-empty}")
    @ConfigurableSize.List({
            @ConfigurableSize(min = "credential.password.min-size", message = "{validation.credential.password.min-size}"),
            @ConfigurableSize(max = "credential.password.max-size", message = "{validation.credential.password.max-size}")
    })
    private final char[] password;

}
