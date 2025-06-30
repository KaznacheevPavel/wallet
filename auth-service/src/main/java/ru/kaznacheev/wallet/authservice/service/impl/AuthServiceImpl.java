package ru.kaznacheev.wallet.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kaznacheev.wallet.authservice.model.dto.request.LoginRequest;
import ru.kaznacheev.wallet.authservice.model.dto.request.RegistrationRequest;
import ru.kaznacheev.wallet.authservice.model.dto.response.LoginResponse;
import ru.kaznacheev.wallet.authservice.model.dto.response.RegistrationResponse;
import ru.kaznacheev.wallet.authservice.model.entity.Credential;
import ru.kaznacheev.wallet.authservice.repository.CredentialRepository;
import ru.kaznacheev.wallet.authservice.service.AuthService;
import ru.kaznacheev.wallet.authservice.service.JwtService;
import ru.kaznacheev.wallet.common.exception.ConflictException;
import ru.kaznacheev.wallet.common.exception.UnauthorizedException;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final CredentialRepository credentialRepository;
    private final PasswordEncoder passwordEncoder;
    @Qualifier("authMessageSource")
    private final MessageSource messageSource;
    private final JwtService jwtService;

    @Transactional
    @Override
    public RegistrationResponse register(RegistrationRequest request) {
        if (credentialRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException(messageSource.getMessage("exception.credential.email-already-use",
                    new Object[]{}, Locale.getDefault()));
        }
        Credential credential = Credential.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(CharBuffer.wrap(request.getPassword())))
                .build();
        credentialRepository.save(credential);
        Arrays.fill(request.getPassword(), '\0');
        return new RegistrationResponse(credential.getId());
    }

    @Transactional(readOnly = true)
    @Override
    public LoginResponse login(LoginRequest request) {
        Optional<Credential> credential = credentialRepository.findByEmail(request.getEmail());
        if (credential.isEmpty() ||
                !passwordEncoder.matches(CharBuffer.wrap(request.getPassword()), credential.get().getPassword())) {
            Arrays.fill(request.getPassword(), '\0');
            throw new UnauthorizedException(messageSource.getMessage("exception.login.invalid-credential",
                    new Object[]{}, Locale.getDefault()));
        }
        Arrays.fill(request.getPassword(), '\0');
        UUID id = credential.get().getId();
        return new LoginResponse(jwtService.generateAccessToken(id), jwtService.generateRefreshToken(id));
    }

}
