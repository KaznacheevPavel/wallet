package ru.kaznacheev.wallet.common.exception;

import java.util.Map;

/**
 * Исключение для NOT_FOUND (404).
 */
public class NotFoundException extends BaseException {

    /**
     * Конструктор.
     *
     * @param detail Подробное описание исключения
     */
    public NotFoundException(String detail) {
        super(ExceptionStatus.NOT_FOUND.getStatus(), ExceptionStatus.NOT_FOUND.getTitle(), detail, null);
    }

    /**
     * Конструктор.
     *
     * @param detail Подробное описание исключения
     * @param data Дополнительная информация об исключении
     */
    public NotFoundException(String detail, Map<String, ?> data) {
        super(ExceptionStatus.NOT_FOUND.getStatus(), ExceptionStatus.NOT_FOUND.getTitle(), detail, data);
    }

}
