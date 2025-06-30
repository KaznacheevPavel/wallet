package ru.kaznacheev.wallet.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kaznacheev.wallet.authservice.model.entity.Credential;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, UUID> {

    Optional<Credential> findByEmail(String email);

    boolean existsByEmail(String email);
    
}
