package org.fpic974.webservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fpic974.webservice.config.CustomProperties;
import org.fpic974.webservice.dto.NoteRequest;
import org.fpic974.webservice.dto.NoteResponse;
import org.springframework.core.ParameterizedTypeReference;
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

    public List<NoteResponse> getNotesByPatientId(Integer id) {
        return webClientBuilder.build().get()
                .uri(customProperties.getNoteApiUrl(), uriBuilder -> uriBuilder.queryParam("id", id).build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<NoteResponse>>() { })
                .block();
    }

    public NoteResponse createNote(NoteRequest noteRequest) {
        log.debug("Service Call : createNote({})", noteRequest);

        return webClientBuilder.build().post()
                .uri(customProperties.getNoteApiUrl())
                .body(Mono.just(noteRequest), NoteRequest.class)
                .retrieve()
                .bodyToMono(NoteResponse.class)
                .block();
    }

    public void deleteNotesByPatientId(Integer id) {
        webClientBuilder.build().delete()
                .uri(customProperties.getNoteApiUrl(), uriBuilder -> uriBuilder.queryParam("id", id).build())
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
