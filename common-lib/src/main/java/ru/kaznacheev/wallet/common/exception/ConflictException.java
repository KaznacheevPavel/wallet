package ru.kaznacheev.wallet.common.exception;

import java.util.Map;

/**
 * Исключение для CONFLICT (409).
 */
public class ConflictException extends BaseException {

    /**
     * Конструктор.
     *
     * @param detail Подробное описание исключения
     */
    public ConflictException(String detail) {
        super(ExceptionStatus.CONFLICT.getStatus(), ExceptionStatus.CONFLICT.getTitle(), detail, null);
    }

    /**
     * Конструктор.
     *
     * @param detail Подробное описание исключения
     * @param data Дополнительная информация об исключении
     */
    public ConflictException(String detail, Map<String, ?> data) {
        super(ExceptionStatus.CONFLICT.getStatus(), ExceptionStatus.CONFLICT.getTitle(), detail, data);
    }

}
