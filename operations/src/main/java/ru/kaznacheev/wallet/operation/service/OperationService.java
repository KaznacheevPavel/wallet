package ru.kaznacheev.wallet.operation.service;

import ru.kaznacheev.wallet.common.dto.request.CursorPageable;
import ru.kaznacheev.wallet.common.dto.response.CursorPage;
import ru.kaznacheev.wallet.operation.dto.request.CreateOperationRequest;
import ru.kaznacheev.wallet.operation.dto.response.OperationResponse;
import ru.kaznacheev.wallet.operation.dto.response.OperationShortResponse;

import java.util.List;

/**
 * Интерфейс сервиса для работы с операциями.
 */
public interface OperationService {

    /**
     * Создает новую операцию.
     *
     * @param request Информация о новой операции
     * @return {@link OperationResponse} с информацией о созданной операции
     */
    OperationResponse createOperation(CreateOperationRequest request);

    /**
     * Возвращает информацию об операции по идентификатору.
     *
     * @param id Идентификатор операции
     * @return {@link OperationResponse} с информацией об операции
     */
    OperationResponse getOperationById(Long id);

    /**
     * Возвращает краткую информацию об операциях.
     *
     * @param cursorPageable Параметры курсорной пагинации
     * @return {@link CursorPage} {@link List} {@link OperationShortResponse} с краткой информацией об операции
     */
    CursorPage<List<OperationShortResponse>> getAllOperationsByCursorPageable(CursorPageable cursorPageable);

}
