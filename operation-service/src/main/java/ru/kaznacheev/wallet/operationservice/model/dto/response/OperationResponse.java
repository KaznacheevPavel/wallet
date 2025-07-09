package ru.kaznacheev.wallet.operationservice.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.kaznacheev.wallet.operationservice.model.entity.OperationType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class OperationResponse {

    private final UUID id;

    private final OperationType type;

    private final BigDecimal amount;

    private final Instant timestamp;

}
