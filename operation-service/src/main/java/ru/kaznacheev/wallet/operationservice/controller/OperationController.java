package ru.kaznacheev.wallet.operationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kaznacheev.wallet.operationservice.model.dto.request.NewOperationRequest;
import ru.kaznacheev.wallet.operationservice.model.dto.response.OperationResponse;
import ru.kaznacheev.wallet.operationservice.service.OperationService;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/operations")
@RequiredArgsConstructor
public class OperationController {

    private final OperationService operationService;

    @PostMapping
    public OperationResponse createOperation(@RequestHeader("Wallet-User-Id") UUID userId,
                                             @RequestBody NewOperationRequest request) {
        return operationService.createOperation(userId, request);
    }

    @GetMapping("/{operationId}")
    public OperationResponse getOperationById(@RequestHeader("Wallet-User-Id") UUID userId,
                                              @PathVariable UUID operationId) {
        return operationService.getOperationById(userId, operationId);
    }

    @DeleteMapping("/{operationId}")
    public void deleteOperationById(@RequestHeader("Wallet-User-Id") UUID userId,
                                                 @PathVariable UUID operationId) {
        operationService.deleteOperationById(userId, operationId);
    }

}
