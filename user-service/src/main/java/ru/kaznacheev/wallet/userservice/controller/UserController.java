package ru.kaznacheev.wallet.userservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kaznacheev.wallet.userservice.dto.request.CreateUserRequest;
import ru.kaznacheev.wallet.userservice.dto.request.CursorPageableUserInfoRequest;
import ru.kaznacheev.wallet.userservice.dto.response.CursorPage;
import ru.kaznacheev.wallet.userservice.dto.response.UserInfoResponse;
import ru.kaznacheev.wallet.userservice.service.UserService;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserInfoResponse createUser(@Valid @RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @GetMapping("/{id}")
    public UserInfoResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public CursorPage<UserInfoResponse, Long> getAllUser(@Valid @ModelAttribute CursorPageableUserInfoRequest request) {
        return userService.getAllUsers(request);
    }

}
