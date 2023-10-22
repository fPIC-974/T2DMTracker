package org.fpic974.riskservice.service;

import org.fpic974.riskservice.dto.NoteResponse;

import java.util.List;

public interface IRiskService {

    /**
     * Retrieves a list of triggered thresholds by its patient id.
     * <p>
     * The {@literal token} is passed through the WebClient communication link
     * to ensure security checks are successful.
     *
     * @param id must not be null.
     * @param token must not be null.
     * @return the list of triggered thresholds.
     * @throws IllegalArgumentException if given {@literal id} is not found.
     */
    List<String> getTriggersByPatientId(Integer id, String token);

    /**
     * Checks if a single Note entity contains a trigger.
     *
     * @param trigger must not be null.
     * @param noteResponse must not be null.
     * @return true if trigger is found in Note, false otherwise.
     */
    boolean findTriggerInNote(String trigger, NoteResponse noteResponse);

    /**
     * Retrieves the risk assessment for Patient with given id.
     * <p>
     * The {@literal token} is passed through the WebClient communication link
     * to ensure security checks are successful.
     *
     * @param id must not be null.
     * @param token must not be null.
     * @return the risk assessment.
     * @throws IllegalArgumentException if given {@literal id} is not found.
     */
    String getRiskAssessmentByPatientId(Integer id, String token);
}
