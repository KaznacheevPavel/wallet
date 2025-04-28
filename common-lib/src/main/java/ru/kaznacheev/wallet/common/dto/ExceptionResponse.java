package ru.kaznacheev.wallet.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * Ответ с информацией о возникшем исключении.
 */
@AllArgsConstructor
@Getter
@Builder
public class ExceptionResponse {

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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Map<String, ?> data;

}
