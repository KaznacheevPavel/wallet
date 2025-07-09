package ru.kaznacheev.wallet.operationservice.model.dto.request;

import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.kaznacheev.wallet.common.validation.constraint.ConfigurableDigits;
import ru.kaznacheev.wallet.common.validation.constraint.EnumType;
import ru.kaznacheev.wallet.operationservice.model.entity.OperationType;

import java.time.Instant;

@AllArgsConstructor
@Getter
public class NewOperationRequest {

    @EnumType(enumClass = OperationType.class, message = "{validation.operation.type.enum-type}")
    private final String type;

    @Positive(message = "{validation.operation.amount.positive}")
    @ConfigurableDigits(integer = "operation.amount.max-integer-length",
            fraction = "operation.amount.max-fraction-length", message = "{validation.operation.amount.digits}")
    private final String amount;

    @PastOrPresent(message = "{validation.operation.timestamp.past}")
    private final Instant timestamp;

}
