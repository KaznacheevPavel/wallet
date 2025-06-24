package ru.kaznacheev.wallet.userservice.service;

import ru.kaznacheev.wallet.userservice.dto.request.CreateUserRequest;
import ru.kaznacheev.wallet.userservice.dto.request.CursorPageableUserInfoRequest;
import ru.kaznacheev.wallet.userservice.dto.response.CursorPage;
import ru.kaznacheev.wallet.userservice.dto.response.UserInfoResponse;

public interface UserService {

    UserInfoResponse createUser(CreateUserRequest request);

    UserInfoResponse getUserById(Long id);

    CursorPage<UserInfoResponse, Long> getAllUsers(CursorPageableUserInfoRequest request);

}
