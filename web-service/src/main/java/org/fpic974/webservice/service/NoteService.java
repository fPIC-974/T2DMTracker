package org.fpic974.webservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fpic974.webservice.config.CustomProperties;
import org.fpic974.webservice.dto.NoteRequest;
import org.fpic974.webservice.dto.NoteResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteService {

    private final CustomProperties customProperties;

    private final WebClient.Builder webClientBuilder;
    private final UserService userService;

    private final UserDetailsService userDetailsService;

    public List<NoteResponse> getNotesByPatientId(Integer id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String token = userService.createToken(userDetailsService.loadUserByUsername(auth.getName()));

        return webClientBuilder.build().get()
                .uri(customProperties.getNoteApiUrl(), uriBuilder -> uriBuilder.queryParam("id", id).build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<NoteResponse>>() { })
                .block();
    }

    public NoteResponse createNote(NoteRequest noteRequest) {
        log.debug("Service Call : createNote({})", noteRequest);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String token = userService.createToken(userDetailsService.loadUserByUsername(auth.getName()));

        return webClientBuilder.build().post()
                .uri(customProperties.getNoteApiUrl())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(Mono.just(noteRequest), NoteRequest.class)
                .retrieve()
                .bodyToMono(NoteResponse.class)
                .block();
    }

    public void deleteNotesByPatientId(Integer id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String token = userService.createToken(userDetailsService.loadUserByUsername(auth.getName()));

        webClientBuilder.build().delete()
                .uri(customProperties.getNoteApiUrl(), uriBuilder -> uriBuilder.queryParam("id", id).build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
