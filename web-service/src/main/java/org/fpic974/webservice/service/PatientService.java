package org.fpic974.webservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fpic974.webservice.config.CustomProperties;
import org.fpic974.webservice.dto.PatientRequest;
import org.fpic974.webservice.dto.PatientResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientService {

    private final CustomProperties customProperties;
    private final WebClient.Builder webClientBuilder;

    public List<PatientResponse> getAllPatients() {

        return webClientBuilder.build().get()
                .uri(customProperties.getPatientApiUrl() + "/list")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<PatientResponse>>() {})
                .block();
    }

    public PatientResponse getPatientById(Integer id) {
        return webClientBuilder.build().get()
                .uri(customProperties.getPatientApiUrl(), uriBuilder -> uriBuilder.queryParam("id", id).build())
                .retrieve()
                .bodyToMono(PatientResponse.class)
                .block();
    }

    public PatientResponse createPatient(PatientRequest patientRequest) {
        return webClientBuilder.build().post()
                .uri(customProperties.getPatientApiUrl())
                .body(Mono.just(patientRequest), PatientRequest.class)
                .retrieve()
                .bodyToMono(PatientResponse.class)
                .block();
    }

    public void deletePatientById(Integer id) {
        webClientBuilder.build().delete()
                .uri(customProperties.getPatientApiUrl(), uriBuilder -> uriBuilder.queryParam("id", id).build())
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public PatientResponse updatePatientById(Integer id, PatientRequest patientRequest) {
        return webClientBuilder.build().put()
                .uri(customProperties.getPatientApiUrl(), uriBuilder -> uriBuilder.queryParam("id", id).build())
                .body(Mono.just(patientRequest), PatientRequest.class)
                .retrieve()
                .bodyToMono(PatientResponse.class)
                .block();
    }
}
