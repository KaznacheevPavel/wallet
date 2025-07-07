package ru.kaznacheev.wallet.operationservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.kaznacheev.wallet.operationservice.model.dto.response.OperationResponse;
import ru.kaznacheev.wallet.operationservice.model.entity.Operation;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OperationMapper {

    OperationResponse toOperationResponse(Operation operation);

}
