package ru.kaznacheev.wallet.operationservice.repository;

import org.springframework.data.jpa.domain.Specification;
import ru.kaznacheev.wallet.operationservice.model.dto.response.OperationResponse;
import ru.kaznacheev.wallet.operationservice.model.entity.Operation;

import java.util.List;

public interface OperationSpecificationRepository {

    List<OperationResponse> findAllBySpecification(Specification<Operation> specification);

}
