package ru.kaznacheev.wallet.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kaznacheev.wallet.authservice.model.dto.request.RegistrationRequest;
import ru.kaznacheev.wallet.authservice.model.dto.response.RegistrationResponse;
import ru.kaznacheev.wallet.authservice.model.entity.Credential;
import ru.kaznacheev.wallet.authservice.repository.CredentialRepository;
import ru.kaznacheev.wallet.authservice.service.AuthService;
import ru.kaznacheev.wallet.common.exception.ConflictException;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final CredentialRepository credentialRepository;
    private final PasswordEncoder passwordEncoder;
    @Qualifier("authMessageSource")
    private final MessageSource messageSource;

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

}
