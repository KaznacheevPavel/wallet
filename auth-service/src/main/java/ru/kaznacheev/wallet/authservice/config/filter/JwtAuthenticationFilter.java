package ru.kaznacheev.wallet.authservice.config.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.kaznacheev.wallet.authservice.service.AccessBlacklistService;
import ru.kaznacheev.wallet.authservice.service.JwtService;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final String HEADER_TITLE = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";

    private final JwtService jwtService;
    private final AccessBlacklistService accessBlacklistService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HEADER_TITLE);
        if (authHeader == null || !authHeader.startsWith(TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authHeader.replace(TOKEN_PREFIX, "");
        DecodedJWT decodedJWT = jwtService.verify(token);
        accessBlacklistService.verify(decodedJWT.getClaim("jti").asString());
        Authentication authentication = new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), null,
                Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

}
