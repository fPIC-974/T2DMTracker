package org.fpic974.riskservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.fpic974.riskservice.config.CustomProperties;
import org.fpic974.riskservice.dto.NoteResponse;
import org.fpic974.riskservice.dto.PatientResponse;
import org.fpic974.riskservice.model.RiskAssessment;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiskService {

    private final CustomProperties customProperties;

    private final WebClient.Builder webClientBuilder;

    List<String> triggerValues = Arrays.asList(
            "Hémoglobine A1C",
            "Microalbumine",
            "Taille",
            "Poids",
            "Fume",
            "Anormal",
            "Cholestérol",
            "Vertiges",
            "Rechute",
            "Réaction",
            "Anticorps");

    public List<String> getTriggersByPatientId(Integer id, String token) {
        List<String> triggerList = new ArrayList<>();

        List<NoteResponse> notes = webClientBuilder.build().get()
                .uri(customProperties.getNoteApiUrl(), uriBuilder -> uriBuilder.queryParam("id", id).build())
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<NoteResponse>>() { })
                .block();

        if (notes != null) {
            notes.forEach(note -> {
                triggerValues.forEach(trigger -> {
                    if (trigger.equals("Poids")) {
                        Pattern pattern = Pattern.compile("Poids.*inf.*recommand");
                        Matcher matcher = pattern.matcher(note.getNote());

                        if (findTriggerInNote(trigger, note)) {
                            if (!matcher.find()) {
                                triggerList.add(trigger);
                            }
                        }
                    } else {
                        if (findTriggerInNote(trigger, note)) {
                            triggerList.add(trigger);
                        }
                    }
                });
            });
        }

        log.debug(id + ":" + triggerList.toString());

        return triggerList;
    }

    public boolean findTriggerInNote(String trigger, NoteResponse noteResponse) {
        return StringUtils.containsIgnoreCase(noteResponse.getNote(), trigger);
    }

    public String getRiskAssessmentByPatientId(Integer id, String token) {
        PatientResponse patient = webClientBuilder.build().get()
                .uri(customProperties.getPatientApiUrl(), uriBuilder -> uriBuilder.queryParam("id", id).build())
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .bodyToMono(PatientResponse.class)
                .block();

        RiskAssessment riskAssessment = RiskAssessment.builder()
                .age(LocalDate.now().compareTo(patient.getBirthDate()))
                .gender(patient.getGender())
                .risks(getTriggersByPatientId(id, token))
                .build();

        if (riskAssessment.isEarlyOnSet()) {
            log.debug(id + ": EarlyOnSet");
            return "EarlyOnSet";
        }

        if (riskAssessment.isBorderLine()) {
            log.debug(id + ": BorderLine");
            return "BorderLine";
        }

        if (riskAssessment.isDanger()) {
            log.debug(id + ": Danger");
            return "Danger";
        }

        if (riskAssessment.isNone()) {
            log.debug(id + ": None");
            return "None";
        }

        return null;
    }
}
