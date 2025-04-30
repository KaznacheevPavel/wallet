package ru.kaznacheev.wallet.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Информация об ошибке валидации поля
 */
@AllArgsConstructor
@Getter
@Builder
public class FieldIssue {

    /**
     * Поле на котором возникло исключение
     */
    private final String field;

    /**
     * Сообщение об ошибке
     */
    private final String issue;

    /**
     * Значение из-за которого возникло исключение
     */
    private final Object rejectedValue;

}
