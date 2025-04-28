package ru.kaznacheev.wallet.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * Базовое исключение.
 */
@AllArgsConstructor
@Getter
public class BaseException extends RuntimeException {

    /**
     * Статус-код исключения.
     */
    private final int status;

    /**
     * Название исключения.
     */
    private final String title;

    /**
     * Подробное описание исключения.
     */
    private final String detail;

    /**
     * Дополнительная информация об исключении.
     * <p>
     * Ключ - название поля при сериализации в ответ, значение - значение поля.
     */
    private final Map<String, ?> data;

}
