package ru.kaznacheev.wallet.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kaznacheev.wallet.userservice.dto.UserInfoResponse;
import ru.kaznacheev.wallet.userservice.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
        SELECT new ru.kaznacheev.wallet.userservice.dto.UserInfoResponse(u.id, u.username)
        FROM User u
        WHERE u.id = :id
    """)
    Optional<UserInfoResponse> findUserInfoById(Long id);

    @Query("""
        SELECT new ru.kaznacheev.wallet.userservice.dto.UserInfoResponse(u.id, u.username)
        FROM User u
    """)
    List<UserInfoResponse> findAllUserInfo();

}
