package org.fpic974.gatewayservice.config;

import lombok.extern.slf4j.Slf4j;
import org.fpic974.gatewayservice.dto.UserDto;
import org.springframework.boot.web.server.Cookie;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Component
@Slf4j
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private final WebClient.Builder webClientBuilder;

    public AuthFilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new RuntimeException("Missing authorization information");
            }

            String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

            /*String[] parts = authHeader.split(" ");

            if (parts.length != 2 || !"Bearer".equals(parts[0])) {
                throw new RuntimeException("Incorrect authorization structure");
            }*/

            String[] parts = null;

            if (exchange.getRequest().getCookies().get("Authorization") != null) {

                parts = exchange.getRequest().getCookies().get("Authorization").get(0).toString().split("=");

                if (parts.length != 2 || !"Authorization".equals(parts[0])) {
                    throw new RuntimeException("Incorrect authorization structure");
                }
            } else {
                parts = authHeader.split(" ");

                if (parts.length != 2 || !"Bearer".equals(parts[0])) {
                    throw new RuntimeException("Incorrect authorization structure");
                }
            }

            return webClientBuilder.build()
                    .post()
                    .uri("http://localhost:8080/validateToken?token=" + parts[1])
                    .retrieve().bodyToMono(UserDto.class)
                    .map(userDto -> {
                        exchange.getRequest()
                                .mutate()
//                                .header("X-auth-user-id", String.valueOf(userDto.getId()));
                                .header("X-auth-user-id", String.valueOf(userDto.getUsername()));
                        return exchange;
                    }).flatMap(chain::filter);
        };
    }

    public static class Config {
        // empty class as I don't need any particular configuration
    }
}