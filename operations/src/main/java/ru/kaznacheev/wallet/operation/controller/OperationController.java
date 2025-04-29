package ru.kaznacheev.wallet.operation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kaznacheev.wallet.common.dto.CursorPage;
import ru.kaznacheev.wallet.common.dto.CursorPageable;
import ru.kaznacheev.wallet.operation.dto.request.CreateOperationRequest;
import ru.kaznacheev.wallet.operation.dto.response.OperationResponse;
import ru.kaznacheev.wallet.operation.dto.response.OperationShortResponse;
import ru.kaznacheev.wallet.operation.service.OperationService;

import java.util.List;

/**
 * Контроллер для обработки запросов, связанных с операциями.
 */
@RestController
@RequestMapping("/api/v1/operations")
@RequiredArgsConstructor
class OperationController {

    private final OperationService operationService;

    /**
     * Обрабатывает запрос на создание новой операции.
     *
     * @param request Информация о новой операции
     * @return {@link OperationResponse} с информацией о созданной операции
     */
    @PostMapping
    public OperationResponse createOperation(@RequestBody CreateOperationRequest request) {
        return operationService.createOperation(request);
    }

    /**
     * Обрабатывает запрос на получение информации об операции по идентификатору.
     *
     * @param id Идентификатор операции
     * @return {@link OperationResponse} с информацией об операции
     */
    @GetMapping("/{id}")
    public OperationResponse getOperationById(@PathVariable("id") Long id) {
        return operationService.getOperationById(id);
    }

    /**
     * Обрабатывает запрос на получение краткой информации об операциях.
     *
     * @param cursorPageable Параметры курсорной пагинации
     * @return {@link CursorPage} {@link List} {@link OperationShortResponse} с краткой информацией об операции
     */
    @GetMapping
    public CursorPage<List<OperationShortResponse>> getAllOperations(@ModelAttribute CursorPageable cursorPageable) {
        return operationService.getAllOperationsByCursorPageable(cursorPageable);
    }

}
