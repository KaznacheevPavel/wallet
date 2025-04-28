package ru.kaznacheev.wallet.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Перечисление возможных подробных описаний для исключений.
 */
@AllArgsConstructor
@Getter
public enum ExceptionTitle {

    OPERATION_NOT_FOUND_BY_ID("Операция с идентификатором %d не найдена");

    private final String template;

    /**
     * Форматирует сообщение об исключении.
     *
     * @param params Параметры для форматирования
     * @return Форматированное сообщение
     */
    public String format(Object... params) {
        return String.format(template, params);
    }

}
