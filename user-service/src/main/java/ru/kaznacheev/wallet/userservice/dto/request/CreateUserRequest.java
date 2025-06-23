package ru.kaznacheev.wallet.userservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.kaznacheev.wallet.userservice.validation.constraint.ConfigurableSize;

@AllArgsConstructor
@Getter
public class CreateUserRequest {

    @NotBlank(message = "{validation.user.username.not-blank}")
    @ConfigurableSize.List({
            @ConfigurableSize(min = "user.username.min-length", message = "{validation.user.username.min-length}"),
            @ConfigurableSize(max = "user.username.max-length", message = "{validation.user.username.max-length}")
    })
    private final String username;

    @NotEmpty(message = "{validation.user.password.not-blank}")
    @ConfigurableSize.List({
            @ConfigurableSize(min = "user.password.min-length", message = "{validation.user.password.min-length}"),
            @ConfigurableSize(max = "user.password.max-length", message = "{validation.user.password.max-length}")
    })
    private final char[] password;

}
