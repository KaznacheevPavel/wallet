package ru.kaznacheev.wallet.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Перечисление возможных названий поля для передачи дополнительной информации об исключении.
 */
@AllArgsConstructor
@Getter
public enum ExceptionData {

    VALIDATION_ERROR_MESSAGES("Список ошибок");

    private final String title;

}
