package ru.kaznacheev.wallet.operation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kaznacheev.wallet.operation.dto.response.OperationResponse;
import ru.kaznacheev.wallet.operation.dto.response.OperationShortResponse;
import ru.kaznacheev.wallet.operation.entity.Operation;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для {@link Operation}.
 */
@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

    /**
     * Возвращает информацию об операции по идентификатору.
     *
     * @param id Идентификатор операции
     * @return {@link Optional} {@link OperationResponse} с информацией об операции
     */
    @Query("""
        SELECT new ru.kaznacheev.wallet.operation.dto.response.OperationResponse(
            o.id,
            o.type,
            o.amount,
            o.comment
            )
        FROM Operation o
        WHERE o.id = :id
    """)
    Optional<OperationResponse> findOperationResponseById(Long id);

    @Query("""
        SELECT new ru.kaznacheev.wallet.operation.dto.response.OperationShortResponse(
            o.id,
            o.type,
            o.amount
            )
        FROM Operation o
    """)
    List<OperationShortResponse> findAllOperationShortResponse();

}
