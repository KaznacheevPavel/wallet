package ru.kaznacheev.wallet.userservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserInfoResponse {

    private final Long id;

    private final String username;

}
