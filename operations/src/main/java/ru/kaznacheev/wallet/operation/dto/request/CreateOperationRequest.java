package ru.kaznacheev.wallet.operation.dto.request;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.kaznacheev.wallet.common.constraint.ValidEnum;
import ru.kaznacheev.wallet.common.constraint.group.AdvancedGroup;
import ru.kaznacheev.wallet.operation.entity.OperationType;

import java.math.BigDecimal;

/**
 * Информация для запроса на создание новой операции.
 */
@AllArgsConstructor
@Getter
@GroupSequence({CreateOperationRequest.class, AdvancedGroup.class})
public class CreateOperationRequest {

    /**
     * Тип операции.
     * <p>
     * Возможные значения - {@link OperationType}
     */
    @NotBlank(message = "Тип операции обязателен")
    @ValidEnum(message = "Неверный тип операции", enumClass = OperationType.class, groups = AdvancedGroup.class)
    private final String type;

    /**
     * Сумма операции.
     */
    @NotNull(message = "Сумма операции обязательна")
    @Positive(message = "Сумма операции должна быть больше нуля", groups = AdvancedGroup.class)
    @Digits(message = "Сумма операции должна содержать до 9 целых и 2 дробных знаков", integer = 9, fraction = 2,
            groups = AdvancedGroup.class)
    private final BigDecimal amount;

    /**
     * Комментарий к операции.
     */
    @Size(message = "Комментарий не должен быть больше 255 символов", max = 255, groups = AdvancedGroup.class)
    private final String comment;

}
