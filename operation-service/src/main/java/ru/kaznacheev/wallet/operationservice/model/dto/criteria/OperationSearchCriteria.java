package ru.kaznacheev.wallet.operationservice.model.dto.criteria;

import lombok.Builder;
import lombok.Getter;
import ru.kaznacheev.wallet.operationservice.model.entity.OperationType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class OperationSearchCriteria {

    private final UUID userId;

    private final OperationType type;

    private final Instant fromDate;

    private final Instant toDate;

    private final BigDecimal greaterAmount;

    private final BigDecimal lessAmount;

}
