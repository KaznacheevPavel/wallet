package ru.kaznacheev.wallet.operation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.kaznacheev.wallet.operation.entity.OperationType;

import java.math.BigDecimal;

/**
 * Информация для запроса на создание новой операции.
 */
@AllArgsConstructor
@Getter
public class CreateOperationRequest {

    /**
     * Тип операции.
     * <p>
     * Возможные значения - {@link OperationType}
     */
    private final String type;

    /**
     * Сумма операции.
     */
    private final BigDecimal amount;

    /**
     * Комментарий к операции.
     */
    private final String comment;

}
