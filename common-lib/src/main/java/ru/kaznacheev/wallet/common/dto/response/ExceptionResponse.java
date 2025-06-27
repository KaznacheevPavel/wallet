package ru.kaznacheev.wallet.common.dto.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class ExceptionResponse {

    private final String title;

    private final int status;

    private final String detail;

    private final String instance;

}
