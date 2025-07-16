package ru.kaznacheev.wallet.operationservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.kaznacheev.wallet.operationservice.config.TimeZoneContextHolder;
import ru.kaznacheev.wallet.operationservice.model.dto.criteria.OperationSearchCriteria;
import ru.kaznacheev.wallet.operationservice.model.dto.request.SearchOperationRequest;
import ru.kaznacheev.wallet.operationservice.model.dto.response.OperationResponse;
import ru.kaznacheev.wallet.operationservice.model.entity.Operation;
import ru.kaznacheev.wallet.operationservice.model.entity.OperationType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OperationMapper {

    OperationResponse toOperationResponse(Operation operation);

    default OperationSearchCriteria toOperationSearchCriteria(UUID userId, SearchOperationRequest request) {
        OperationType type = Objects.isNull(request.getType()) ? null : OperationType.valueOf(request.getType());
        Instant fromDate = Objects.isNull(request.getFromDate())
                ? null
                : request.getFromDate().atStartOfDay(TimeZoneContextHolder.getTimeZone()).toInstant();
        Instant toDate = Objects.isNull(request.getToDate())
                ? null
                : request.getToDate().atStartOfDay(TimeZoneContextHolder.getTimeZone()).toInstant();
        BigDecimal greaterAmount = Objects.isNull(request.getGreaterAmount()) ? null : new BigDecimal(request.getGreaterAmount());
        BigDecimal lessAmount = Objects.isNull(request.getLessAmount()) ? null : new BigDecimal(request.getLessAmount());
        return OperationSearchCriteria.builder()
                .userId(userId)
                .type(type)
                .fromDate(fromDate)
                .toDate(toDate)
                .greaterAmount(greaterAmount)
                .lessAmount(lessAmount)
                .build();
    }

}
