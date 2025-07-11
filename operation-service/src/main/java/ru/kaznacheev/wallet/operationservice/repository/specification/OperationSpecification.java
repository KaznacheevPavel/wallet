package ru.kaznacheev.wallet.operationservice.repository.specification;

import jakarta.persistence.criteria.Predicate;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.kaznacheev.wallet.operationservice.model.entity.Operation;
import ru.kaznacheev.wallet.operationservice.model.entity.OperationType;
import ru.kaznacheev.wallet.operationservice.model.entity.Operation_;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@UtilityClass
public class OperationSpecification {

    private static Specification<Operation> operationOwnerId(UUID userId) {
        return (root, query, criteriaBuilder) -> {
            Predicate ownerIdPredicate = null;
            if (userId != null) {
                ownerIdPredicate = criteriaBuilder.equal(root.get(Operation_.userId), userId);
            }
            return ownerIdPredicate;
        };
    }

    private static Specification<Operation> operationType(OperationType type) {
        return (root, query, criteriaBuilder) -> {
            Predicate typePredicate = null;
            if (type != null) {
                typePredicate = criteriaBuilder.equal(root.get(Operation_.type), type);
            }
            return typePredicate;
        };
    }

    private static Specification<Operation> operationFromDate(Instant date) {
        return (root, query, criteriaBuilder) -> {
            Predicate fromDatePredicate = null;
            if (date != null) {
                fromDatePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get(Operation_.timestamp), date);
            }
            return fromDatePredicate;
        };
    }

    private static Specification<Operation> operationGreaterAmount(BigDecimal amount) {
        return (root, query, criteriaBuilder) -> {
            Predicate greaterAmountPredicate = null;
            if (amount != null) {
                greaterAmountPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get(Operation_.amount), amount);
            }
            return greaterAmountPredicate;
        };
    }

    private static Specification<Operation> operationLessAmount(BigDecimal amount) {
        return (root, query, criteriaBuilder) -> {
            Predicate lessAmountPredicate = null;
            if (amount != null) {
                lessAmountPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get(Operation_.amount), amount);
            }
            return lessAmountPredicate;
        };
    }

    private static Specification<Operation> operationToDate(Instant date) {
        return (root, query, criteriaBuilder) -> {
            Predicate toDatePredicate = null;
            if (date != null) {
                toDatePredicate = criteriaBuilder.lessThanOrEqualTo(root.get(Operation_.timestamp), date);
            }
            return toDatePredicate;
        };
    }

    public static Specification<Operation> build(OperationSpecificationCriteria criteria) {
        return operationOwnerId(criteria.getUserId())
                .and(operationType(criteria.getType()))
                .and(operationFromDate(criteria.getFromDate()))
                .and(operationToDate(criteria.getToDate()))
                .and(operationGreaterAmount(criteria.getGreaterAmount()))
                .and(operationLessAmount(criteria.getLessAmount()));
    }

}
