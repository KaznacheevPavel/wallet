package ru.kaznacheev.wallet.userservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
public class ValidationExceptionResponse extends ExceptionResponse {

    private final List<ValidationError> validationErrors;

    @AllArgsConstructor
    private class ValidationError {

        private final String field;

        private final String detail;

    }

}
