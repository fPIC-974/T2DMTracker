package org.fpic974.webservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fpic974.webservice.config.CustomProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiskService {

    private final CustomProperties customProperties;

    private final WebClient.Builder webClientBuilder;

    public String getRiskAssessmentByPatientId(Integer id) {
        log.debug("Service Call : getRiskAssessmentByPatientId({})", id);

        return webClientBuilder.build().get()
                .uri(customProperties.getRiskApiUrl(), uriBuilder -> uriBuilder.queryParam("id", id).build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
