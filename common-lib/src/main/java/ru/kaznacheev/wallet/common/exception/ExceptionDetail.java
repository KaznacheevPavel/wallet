package ru.kaznacheev.wallet.common.exception;

import lombok.AllArgsConstructor;

/**
 * Перечисление возможных подробных описаний для исключений.
 */
@AllArgsConstructor
public enum ExceptionDetail {

    OPERATION_NOT_FOUND_BY_ID("Операция с идентификатором %d не найдена"),
    VALIDATION_ERROR("Ошибка валидации данных");

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
