package ru.kaznacheev.wallet.gateway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.kaznacheev.wallet.gateway.dto.VerifyResponse;

@Component
public class PreAuthGatewayFilterFactory extends AbstractGatewayFilterFactory<PreAuthGatewayFilterFactory.Config> {

    private final String TOKEN_PREFIX = "Bearer ";

    @Value("${headers.user-id}")
    private String userIdHeader;
    @Value("${uri.verify-token}")
    private String verifyUri;
    private final WebClient webClient = WebClient.create();

    public PreAuthGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith(TOKEN_PREFIX)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            return webClient.post()
                    .uri(verifyUri)
                    .header(HttpHeaders.AUTHORIZATION, authHeader)
                    .exchangeToMono(clientResponse -> {
                        if (clientResponse.statusCode().is2xxSuccessful()) {
                            return clientResponse.bodyToMono(VerifyResponse.class)
                                    .flatMap(verifyResponse -> {
                                        ServerHttpRequest request = exchange.getRequest().mutate()
                                                .headers(httpHeaders -> {
                                                    httpHeaders.remove(HttpHeaders.AUTHORIZATION);
                                                    httpHeaders.remove(userIdHeader);
                                                    httpHeaders.add(userIdHeader, verifyResponse.getUserId());
                                                })
                                                .build();
                                        return chain.filter(exchange.mutate().request(request).build());
                                    });
                        } else {
                            exchange.getResponse().setStatusCode(clientResponse.statusCode());
                            exchange.getResponse().getHeaders().putAll(clientResponse.headers().asHttpHeaders());
                            return clientResponse.bodyToMono(byte[].class)
                                    .defaultIfEmpty(new byte[0])
                                    .flatMap(bytes -> exchange.getResponse().writeWith(
                                            Mono.just(exchange.getResponse().bufferFactory().wrap(bytes))
                                    ));
                        }
                    });
        };
    }

    public static class Config {}

}
