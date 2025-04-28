package ru.kaznacheev.wallet.operation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.kaznacheev.wallet.operation.entity.OperationType;

import java.math.BigDecimal;

/**
 * Информация об операции.
 */
@AllArgsConstructor
@Getter
public class OperationResponse {

    /**
     * Идентификатор операции.
     */
    private Long id;

    /**
     * Тип операции.
     * <p>
     * Возможные значения - {@link OperationType}
     */
    private OperationType type;

    /**
     * Сумма операции.
     */
    private BigDecimal amount;

    /**
     * Комментарий к операции.
     */
    private String comment;

}
