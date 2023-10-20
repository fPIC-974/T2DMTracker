package org.fpic974.webservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fpic974.webservice.config.CustomProperties;
import org.fpic974.webservice.dto.PatientRequest;
import org.fpic974.webservice.dto.PatientResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientService {

    private final CustomProperties customProperties;
    private final WebClient.Builder webClientBuilder;

    private final UserDetailsService userDetailsService;

    private final UserService userService;

    public List<PatientResponse> getAllPatients() {
        log.debug("Service Call : getAllPatients()");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String token = userService.createToken(userDetailsService.loadUserByUsername(auth.getName()));

        log.info("AUTH : " + auth.getName());

        return webClientBuilder
                .build().get()
                .uri(customProperties.getPatientApiUrl() + "/list")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<PatientResponse>>() {})
                .block();
    }

    public PatientResponse getPatientById(Integer id) {
        log.debug("Service Call : getPatientById({})", id);

        return webClientBuilder.build().get()
                .uri(customProperties.getPatientApiUrl(), uriBuilder -> uriBuilder.queryParam("id", id).build())
                .retrieve()
                .bodyToMono(PatientResponse.class)
                .block();
    }

    public PatientResponse createPatient(PatientRequest patientRequest) {
        log.debug("Service Call : createPatient({})", patientRequest);

        return webClientBuilder.build().post()
                .uri(customProperties.getPatientApiUrl())
                .body(Mono.just(patientRequest), PatientRequest.class)
                .retrieve()
                .bodyToMono(PatientResponse.class)
                .block();
    }

    public void deletePatientById(Integer id) {
        log.debug("Service Call : deletePatientById({})", id);

        webClientBuilder.build().delete()
                .uri(customProperties.getPatientApiUrl(), uriBuilder -> uriBuilder.queryParam("id", id).build())
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public PatientResponse updatePatientById(Integer id, PatientRequest patientRequest) {
        log.debug("Service Call : updatePatientById({}, {})", id, patientRequest);

        return webClientBuilder.build().put()
                .uri(customProperties.getPatientApiUrl(), uriBuilder -> uriBuilder.queryParam("id", id).build())
                .body(Mono.just(patientRequest), PatientRequest.class)
                .retrieve()
                .bodyToMono(PatientResponse.class)
                .block();
    }
}
