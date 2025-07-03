package ru.kaznacheev.wallet.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
public class ValidationExceptionResponse extends ExceptionResponse {

    private final List<ValidationIssue> validationIssues;

    @Builder
    @Getter
    public static class ValidationIssue {

        private final String field;

        private final String detail;

    }

}
