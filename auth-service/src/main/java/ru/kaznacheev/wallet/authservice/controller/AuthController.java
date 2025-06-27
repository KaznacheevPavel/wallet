package ru.kaznacheev.wallet.authservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kaznacheev.wallet.authservice.model.dto.request.RegistrationRequest;
import ru.kaznacheev.wallet.authservice.model.dto.response.RegistrationResponse;
import ru.kaznacheev.wallet.authservice.service.AuthService;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public RegistrationResponse register(@Valid @RequestBody RegistrationRequest request) {
        return authService.register(request);
    }

}
