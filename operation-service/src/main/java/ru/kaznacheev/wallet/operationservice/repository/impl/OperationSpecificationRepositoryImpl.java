package ru.kaznacheev.wallet.operationservice.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import ru.kaznacheev.wallet.operationservice.model.dto.response.OperationResponse;
import ru.kaznacheev.wallet.operationservice.model.entity.Operation;
import ru.kaznacheev.wallet.operationservice.model.entity.Operation_;
import ru.kaznacheev.wallet.operationservice.repository.OperationSpecificationRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OperationSpecificationRepositoryImpl implements OperationSpecificationRepository {

    private final EntityManager entityManager;

    @Override
    public List<OperationResponse> findAllBySpecification(Specification<Operation> specification) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<OperationResponse> criteriaQuery = criteriaBuilder.createQuery(OperationResponse.class);
        Root<Operation> root = criteriaQuery.from(Operation.class);
        Predicate predicate = specification.toPredicate(root, criteriaQuery, criteriaBuilder);
        if (predicate != null) {
            criteriaQuery.where(predicate);
        } else {
            criteriaQuery.where();
        }
        criteriaQuery.select(criteriaBuilder.construct(OperationResponse.class,
                root.get(Operation_.id),
                root.get(Operation_.type),
                root.get(Operation_.amount),
                root.get(Operation_.timestamp)));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

}
