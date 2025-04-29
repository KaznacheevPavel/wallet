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
            o.comment,
            o.createdAt
            )
        FROM Operation o
        WHERE o.id = :id
    """)
    Optional<OperationResponse> findOperationResponseById(Long id);

    /**
     * Возвращает краткую информацию об операциях с курсорной пагинацией.
     *
     * @param limit Количество возвращаемых операций
     * @param cursor Идентификатор записи, с которой начинается выборка (не включительно)
     * @return {@link List} {@link OperationShortResponse} с краткой информацией об операциях
     */
    @Query(value = """
        SELECT new ru.kaznacheev.wallet.operation.dto.response.OperationShortResponse(
            o.id,
            o.type,
            o.amount,
            o.createdAt
            )
        FROM Operation o
        WHERE (:cursor IS NULL OR o.id < :cursor)
        ORDER BY o.id DESC
        LIMIT :limit
    """)
    List<OperationShortResponse> findAllOperationShortResponseByCursorPageable(Integer limit, Long cursor);

}
