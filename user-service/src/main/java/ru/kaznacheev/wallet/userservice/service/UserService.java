package ru.kaznacheev.wallet.userservice.service;

import ru.kaznacheev.wallet.userservice.dto.CreateUserRequest;
import ru.kaznacheev.wallet.userservice.dto.UserInfoResponse;

import java.util.List;

public interface UserService {

    UserInfoResponse createUser(CreateUserRequest request);

    UserInfoResponse getUserById(Long id);

    List<UserInfoResponse> getAllUsers();

}
