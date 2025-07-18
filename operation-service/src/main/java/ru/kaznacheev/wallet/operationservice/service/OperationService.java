package ru.kaznacheev.wallet.operationservice.service;

import ru.kaznacheev.wallet.operationservice.model.dto.request.NewOperationRequest;
import ru.kaznacheev.wallet.operationservice.model.dto.request.SearchOperationRequest;
import ru.kaznacheev.wallet.operationservice.model.dto.response.OperationResponse;

import java.util.List;
import java.util.UUID;

public interface OperationService {

    OperationResponse createOperation(UUID userId, NewOperationRequest request);

    OperationResponse getOperationById(UUID userId, UUID operationId);

    List<OperationResponse> getOperations(UUID userId, SearchOperationRequest request);

    void deleteOperationById(UUID userId, UUID operationId);

}
