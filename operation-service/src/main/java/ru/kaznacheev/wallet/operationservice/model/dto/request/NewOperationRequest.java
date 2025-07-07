package ru.kaznacheev.wallet.operationservice.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.kaznacheev.wallet.operationservice.model.entity.OperationType;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class NewOperationRequest {

    private final OperationType type;

    private final BigDecimal amount;

}
