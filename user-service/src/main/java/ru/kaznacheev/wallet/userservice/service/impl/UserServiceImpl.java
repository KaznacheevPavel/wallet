package ru.kaznacheev.wallet.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kaznacheev.wallet.userservice.dto.request.CreateUserRequest;
import ru.kaznacheev.wallet.userservice.dto.request.CursorPageableUserInfoRequest;
import ru.kaznacheev.wallet.userservice.dto.response.CursorPage;
import ru.kaznacheev.wallet.userservice.dto.response.UserInfoResponse;
import ru.kaznacheev.wallet.userservice.entity.User;
import ru.kaznacheev.wallet.userservice.exception.NotFoundException;
import ru.kaznacheev.wallet.userservice.repository.UserRepository;
import ru.kaznacheev.wallet.userservice.service.UserService;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageSource messageSource;

    @Transactional
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

    @Transactional(readOnly = true)
    @Override
    public UserInfoResponse getUserById(Long id) {
        Optional<UserInfoResponse> userInfo = userRepository.findUserInfoById(id);
        return userInfo.orElseThrow(() ->
                new NotFoundException(messageSource.getMessage("exception.not-found.user-by-id",
                        new Object[]{id}, Locale.getDefault())));
    }

    @Transactional(readOnly = true)
    @Override
    public CursorPage<UserInfoResponse, Long> getAllUsers(CursorPageableUserInfoRequest request) {
        List<UserInfoResponse> users = userRepository.findAllUserInfo(request.getCursor(), request.getLimit() + 1);
        Long nextCursor = null;
        boolean isLastPage = users.size() <= request.getLimit();
        if (!isLastPage && !users.isEmpty()) {
            users = users.subList(0, request.getLimit());
            nextCursor = users.getLast().getId();
        }
        return new CursorPage<>(users, nextCursor);
    }

}
