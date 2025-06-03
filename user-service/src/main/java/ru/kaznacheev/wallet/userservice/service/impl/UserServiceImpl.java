package ru.kaznacheev.wallet.userservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kaznacheev.wallet.userservice.dto.CreateUserRequest;
import ru.kaznacheev.wallet.userservice.dto.UserInfoResponse;
import ru.kaznacheev.wallet.userservice.entity.User;
import ru.kaznacheev.wallet.userservice.repository.UserRepository;
import ru.kaznacheev.wallet.userservice.service.UserService;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserInfoResponse createUser(CreateUserRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(CharBuffer.wrap(request.getPassword())))
                .build();
        userRepository.save(user);
        Arrays.fill(request.getPassword(), '\0');
        return new UserInfoResponse(user.getId(), user.getUsername());
    }

    @Override
    public UserInfoResponse getUserById(Long id) {
        Optional<UserInfoResponse> userInfo = userRepository.findUserInfoById(id);
        return userInfo.orElseThrow(() -> new RuntimeException("Неверный id"));
    }

    @Override
    public List<UserInfoResponse> getAllUsers() {
        return userRepository.findAllUserInfo();
    }

}
