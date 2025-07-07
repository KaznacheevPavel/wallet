package ru.kaznacheev.wallet.operationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kaznacheev.wallet.operationservice.model.entity.Operation;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OperationRepository extends JpaRepository<Operation, UUID> {

    @Query("""
        SELECT o.userId
        FROM Operation o
        WHERE o.id = :id
    """)
    Optional<UUID> findOperationOwnerById(UUID id);

}
