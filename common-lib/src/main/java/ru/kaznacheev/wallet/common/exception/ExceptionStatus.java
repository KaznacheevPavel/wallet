package ru.kaznacheev.wallet.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Перечисление возможных названий и статус-кодов для исключений.
 */
@AllArgsConstructor
@Getter
public enum ExceptionStatus {

    BAD_REQUEST(400, "BAD_REQUEST"),
    VALIDATION_ERROR(400, "VALIDATION_ERROR"),
    NOT_FOUND(404, "NOT_FOUND"),
    CONFLICT(409, "CONFLICT");

    private final int status;
    private final String title;

}
