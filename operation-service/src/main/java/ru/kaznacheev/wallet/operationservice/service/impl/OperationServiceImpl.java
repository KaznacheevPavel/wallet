package ru.kaznacheev.wallet.operationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kaznacheev.wallet.common.exception.AccessDeniedException;
import ru.kaznacheev.wallet.common.exception.BadRequestException;
import ru.kaznacheev.wallet.common.exception.NotFoundException;
import ru.kaznacheev.wallet.operationservice.mapper.OperationMapper;
import ru.kaznacheev.wallet.operationservice.model.dto.criteria.OperationSearchCriteria;
import ru.kaznacheev.wallet.operationservice.model.dto.request.NewOperationRequest;
import ru.kaznacheev.wallet.operationservice.model.dto.request.SearchOperationRequest;
import ru.kaznacheev.wallet.operationservice.model.dto.response.OperationResponse;
import ru.kaznacheev.wallet.operationservice.model.entity.Operation;
import ru.kaznacheev.wallet.operationservice.model.entity.OperationType;
import ru.kaznacheev.wallet.operationservice.repository.OperationRepository;
import ru.kaznacheev.wallet.operationservice.service.MessageSourceService;
import ru.kaznacheev.wallet.operationservice.service.OperationService;
import ru.kaznacheev.wallet.operationservice.util.OperationSpecificationUtil;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;
    private final OperationMapper operationMapper;
    private final MessageSourceService messageSourceService;
    private final Clock clock;

    @Transactional
    @Override
    public OperationResponse createOperation(UUID userId, NewOperationRequest request) {
        Operation operation = Operation.builder()
                .userId(userId)
                .type(OperationType.valueOf(request.getType()))
                .amount(new BigDecimal(request.getAmount()))
                .timestamp(request.getTimestamp() != null ? request.getTimestamp() : Instant.now(clock))
                .build();
        operationRepository.save(operation);
        return operationMapper.toOperationResponse(operation);
    }

    @Transactional(readOnly = true)
    @Override
    public OperationResponse getOperationById(UUID userId, UUID operationId) {
        Optional<Operation> operation = operationRepository.findById(operationId);
        if (operation.isEmpty()) {
            throw new NotFoundException(messageSourceService.getMessage("exception.operation.not-found"));
        }
        if (!operation.get().getUserId().equals(userId)) {
            throw new AccessDeniedException(messageSourceService.getMessage("exception.access-denied"));
        }
        return operationMapper.toOperationResponse(operation.get());
    }

    @Transactional(readOnly = true)
    @Override
    public List<OperationResponse> getOperations(UUID userId, SearchOperationRequest request) {
        if (Objects.nonNull(request.getFromDate()) && Objects.nonNull(request.getToDate())
                && request.getFromDate().isAfter(request.getToDate())) {
            throw new BadRequestException(messageSourceService.getMessage("exception.specification.date-from-after-to"));
        }
        if (Objects.nonNull(request.getGreaterAmount()) && Objects.nonNull(request.getLessAmount())
                && new BigDecimal(request.getGreaterAmount()).compareTo(new BigDecimal(request.getLessAmount())) > 0) {
            throw new BadRequestException(messageSourceService.getMessage("exception.specification.amount-from-greater-to"));
        }
        OperationSearchCriteria searchCriteria = operationMapper.toOperationSearchCriteria(userId, request);
        Specification<Operation> specification = OperationSpecificationUtil.buildOperationSearchSpecification(searchCriteria);
        return operationRepository.findAllBySpecification(specification);
    }

    @Transactional
    @Override
    public void deleteOperationById(UUID userId, UUID operationId) {
        Optional<UUID> operationOwner = operationRepository.findOperationOwnerById(operationId);
        if (operationOwner.isEmpty()) {
            throw new NotFoundException(messageSourceService.getMessage("exception.operation.not-found"));
        }
        if (!operationOwner.get().equals(userId)) {
            throw new AccessDeniedException(messageSourceService.getMessage("exception.access-denied"));
        }
        operationRepository.deleteById(operationId);
    }

}
