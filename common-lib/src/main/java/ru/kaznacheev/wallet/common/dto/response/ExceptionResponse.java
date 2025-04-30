package ru.kaznacheev.wallet.common.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
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
     * Временная метка возникновения исключения.
     */
    private final Instant timestamp;

    /**
     * URI по которому возникло исключение.
     */
    private final String path;

    /**
     * Дополнительная информация об исключении.
     * <p>
     * Ключ - название поля при сериализации в ответ, значение - значение поля.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Map<String, ?> data;

}
