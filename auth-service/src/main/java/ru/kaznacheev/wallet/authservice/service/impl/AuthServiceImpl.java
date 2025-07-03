package ru.kaznacheev.wallet.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kaznacheev.wallet.authservice.model.dto.request.LoginRequest;
import ru.kaznacheev.wallet.authservice.model.dto.request.RefreshTokenRequest;
import ru.kaznacheev.wallet.authservice.model.dto.request.RegistrationRequest;
import ru.kaznacheev.wallet.authservice.model.dto.response.RegistrationResponse;
import ru.kaznacheev.wallet.authservice.model.dto.response.TokenPairResponse;
import ru.kaznacheev.wallet.authservice.model.entity.Credential;
import ru.kaznacheev.wallet.authservice.repository.CredentialRepository;
import ru.kaznacheev.wallet.authservice.service.AuthService;
import ru.kaznacheev.wallet.authservice.service.AccessBlacklistService;
import ru.kaznacheev.wallet.authservice.service.JwtService;
import ru.kaznacheev.wallet.authservice.service.RefreshWhitelistService;
import ru.kaznacheev.wallet.common.exception.ConflictException;
import ru.kaznacheev.wallet.common.exception.UnauthorizedException;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final CredentialRepository credentialRepository;
    private final PasswordEncoder passwordEncoder;
    @Qualifier("authMessageSource")
    private final MessageSource messageSource;
    private final JwtService jwtService;
    private final RefreshWhitelistService refreshWhitelistService;
    private final AccessBlacklistService accessBlacklistService;

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
    public TokenPairResponse login(LoginRequest request) {
        Optional<Credential> credential = credentialRepository.findByEmail(request.getEmail());
        if (credential.isEmpty() ||
                !passwordEncoder.matches(CharBuffer.wrap(request.getPassword()), credential.get().getPassword())) {
            Arrays.fill(request.getPassword(), '\0');
            throw new UnauthorizedException(messageSource.getMessage("exception.login.invalid-credential",
                    new Object[]{}, Locale.getDefault()));
        }
        Arrays.fill(request.getPassword(), '\0');
        String id = credential.get().getId().toString();
        String accessToken = jwtService.generateAccessToken(id);
        String refreshToken = jwtService.generateRefreshToken(id);
        refreshWhitelistService.saveToken(accessToken, refreshToken);
        return new TokenPairResponse(accessToken, refreshToken);
    }

    @Override
    public TokenPairResponse refresh(RefreshTokenRequest request) {
        jwtService.verify(request.getRefreshToken());
        String accessJti = refreshWhitelistService.revokeToken(request.getRefreshToken());
        accessBlacklistService.addToBlacklist(accessJti);
        String userid = jwtService.extractSubject(request.getRefreshToken());
        String accessToken = jwtService.generateAccessToken(userid);
        String refreshToken = jwtService.generateRefreshToken(userid);
        refreshWhitelistService.saveToken(accessToken, refreshToken);
        return new TokenPairResponse(accessToken, refreshToken);
    }

}
