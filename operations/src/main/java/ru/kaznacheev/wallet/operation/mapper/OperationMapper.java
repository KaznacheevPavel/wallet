package ru.kaznacheev.wallet.operation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.kaznacheev.wallet.operation.dto.response.OperationResponse;
import ru.kaznacheev.wallet.operation.entity.Operation;

/**
 * Маппер для преобразования операций.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OperationMapper {

    /**
     * Преобразует {@link Operation} в {@link OperationResponse}.
     *
     * @param operation Сущность операции
     * @return {@link OperationResponse} с информацией об операции
     */
    OperationResponse toOperationResponse(Operation operation);

}
