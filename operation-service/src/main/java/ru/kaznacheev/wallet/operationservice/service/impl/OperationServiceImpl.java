package ru.kaznacheev.wallet.operationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kaznacheev.wallet.common.exception.AccessDeniedException;
import ru.kaznacheev.wallet.common.exception.NotFoundException;
import ru.kaznacheev.wallet.operationservice.model.dto.request.NewOperationRequest;
import ru.kaznacheev.wallet.operationservice.model.dto.response.OperationResponse;
import ru.kaznacheev.wallet.operationservice.model.entity.Operation;
import ru.kaznacheev.wallet.operationservice.repository.OperationRepository;
import ru.kaznacheev.wallet.operationservice.service.OperationService;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;

    @Transactional
    @Override
    public OperationResponse createOperation(UUID userId, NewOperationRequest request) {
        Operation operation = Operation.builder()
                .userId(userId)
                .type(request.getType())
                .amount(request.getAmount())
                .build();
        operationRepository.save(operation);
        return new OperationResponse(operation.getId(), operation.getType(), operation.getAmount());
    }

    @Transactional(readOnly = true)
    @Override
    public OperationResponse getOperationById(UUID userId, UUID operationId) {
        Optional<Operation> operation = operationRepository.findById(operationId);
        if (operation.isEmpty()) {
            throw new NotFoundException("Операция не найдена");
        }
        if (!operation.get().getUserId().equals(userId)) {
            throw new AccessDeniedException("Нет доступа");
        }
        return new OperationResponse(operation.get().getId(), operation.get().getType(), operation.get().getAmount());
    }

    @Transactional
    @Override
    public void deleteOperationById(UUID userId, UUID operationId) {
        Optional<UUID> operationOwner = operationRepository.findOperationOwnerById(operationId);
        if (operationOwner.isEmpty()) {
            throw new NotFoundException("Операция не найдена");
        }
        if (!operationOwner.get().equals(userId)) {
            throw new AccessDeniedException("Нет доступа");
        }
        operationRepository.deleteById(operationId);
    }

}
