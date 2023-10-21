package org.fpic974.webservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fpic974.webservice.config.CustomProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiskService {

    private final CustomProperties customProperties;

    private final WebClient.Builder webClientBuilder;

    private final UserDetailsService userDetailsService;

    private final UserService userService;

    public String getRiskAssessmentByPatientId(Integer id) {
        log.debug("Service Call : getRiskAssessmentByPatientId({})", id);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String token = userService.createToken(userDetailsService.loadUserByUsername(auth.getName()));

        return webClientBuilder.build().get()
                .uri(customProperties.getRiskApiUrl(), uriBuilder -> uriBuilder.queryParam("id", id).build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
