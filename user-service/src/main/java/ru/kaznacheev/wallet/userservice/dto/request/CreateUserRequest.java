package ru.kaznacheev.wallet.userservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateUserRequest {

    private final String username;

    private final char[] password;

}
