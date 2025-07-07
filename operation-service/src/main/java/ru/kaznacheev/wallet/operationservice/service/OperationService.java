package ru.kaznacheev.wallet.operationservice.service;

import ru.kaznacheev.wallet.operationservice.model.dto.request.NewOperationRequest;
import ru.kaznacheev.wallet.operationservice.model.dto.response.OperationResponse;

import java.util.UUID;

public interface OperationService {

    OperationResponse createOperation(UUID userId, NewOperationRequest request);

    OperationResponse getOperationById(UUID userId, UUID operationId);

    void deleteOperationById(UUID userId, UUID operationId);

}
