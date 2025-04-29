package ru.kaznacheev.wallet.operation.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kaznacheev.wallet.common.dto.CursorPage;
import ru.kaznacheev.wallet.common.exception.ExceptionTitle;
import ru.kaznacheev.wallet.common.exception.NotFoundException;
import ru.kaznacheev.wallet.operation.dto.request.CreateOperationRequest;
import ru.kaznacheev.wallet.common.dto.CursorPageable;
import ru.kaznacheev.wallet.operation.dto.response.OperationResponse;
import ru.kaznacheev.wallet.operation.dto.response.OperationShortResponse;
import ru.kaznacheev.wallet.operation.entity.Operation;
import ru.kaznacheev.wallet.operation.entity.OperationType;
import ru.kaznacheev.wallet.operation.mapper.OperationMapper;
import ru.kaznacheev.wallet.operation.repository.OperationRepository;
import ru.kaznacheev.wallet.operation.service.OperationService;

import java.util.List;
import java.util.Optional;

/**
 * Реализация интерфейса {@link OperationService}.
 */
@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;
    private final OperationMapper operationMapper;

    @Override
    public OperationResponse createOperation(CreateOperationRequest request) {
        Operation operation = Operation.builder()
                .type(OperationType.valueOf(request.getType()))
                .amount(request.getAmount())
                .comment(request.getComment())
                .build();
        operationRepository.save(operation);
        return operationMapper.toOperationResponse(operation);
    }

    @Transactional(readOnly = true)
    @Override
    public OperationResponse getOperationById(Long id) {
        Optional<OperationResponse> operation = operationRepository.findOperationResponseById(id);
        return operation.orElseThrow(() -> new NotFoundException(ExceptionTitle.OPERATION_NOT_FOUND_BY_ID.format(id)));
    }

    @Transactional(readOnly = true)
    @Override
    public CursorPage<List<OperationShortResponse>> getAllOperationsByCursorPageable(CursorPageable cursorPageable) {
        List<OperationShortResponse> operations =
                operationRepository.findAllOperationShortResponseByCursorPageable(cursorPageable.getLimit() + 1,
                        cursorPageable.getCursor());
        Long nextCursor = null;
        boolean isLastPage = operations.size() <= cursorPageable.getLimit();
        if (!isLastPage && !operations.isEmpty()) {
            operations = operations.subList(0, cursorPageable.getLimit());
            nextCursor = operations.getLast().getId();
        }
        return new CursorPage<>(operations, nextCursor);
    }

}
