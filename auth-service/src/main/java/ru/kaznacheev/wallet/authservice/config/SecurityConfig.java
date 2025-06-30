package ru.kaznacheev.wallet.authservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.kaznacheev.wallet.common.exception.AccessDeniedException;
import ru.kaznacheev.wallet.common.exception.UnauthorizedException;

import java.util.Locale;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${security.password.encoder-strength}")
    private final int passwordEncoderStrength;
    @Qualifier("authMessageSource")
    private final MessageSource messageSource;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final ExceptionHandlerFilter exceptionHandlerFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.sessionManagement(configurer ->
                configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterBefore(exceptionHandlerFilter, JwtAuthenticationFilter.class);

        httpSecurity.exceptionHandling(customizer -> {
            customizer.authenticationEntryPoint(((request, response, authException) -> {
                throw new UnauthorizedException(messageSource.getMessage("exception.auth.unauthorized",
                        new Object[]{}, Locale.getDefault()));
            }));
            customizer.accessDeniedHandler((request, response, accessDeniedException) -> {
                throw new AccessDeniedException(messageSource.getMessage("exception.auth.access-denied",
                        new Object[]{}, Locale.getDefault()));
            });
        });

        httpSecurity.authorizeHttpRequests(customizer -> {
            customizer.requestMatchers(HttpMethod.POST, "/api/v1/auth/login", "/api/v1/auth/register")
                    .not().authenticated();
            customizer.requestMatchers(HttpMethod.POST, "/api/v1/auth/refresh").permitAll();
        });
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2B, passwordEncoderStrength);
    }

}
