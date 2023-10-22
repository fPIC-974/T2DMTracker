package org.fpic974.patientservice.service;

import org.fpic974.patientservice.dto.PatientRequest;
import org.fpic974.patientservice.dto.PatientResponse;

import java.util.List;

public interface IPatientService {

    /**
     * Returns all instances of the type {@code PatientResponse}.
     *
     * @return the list of entities retrieved.
     */
    List<PatientResponse> getAllPatients();


    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be null.
     * @return the entity with the given id.
     * @throws IllegalArgumentException if entity with {@literal id} is not found.
     */
    PatientResponse getPatientById(Integer id);

    /**
     * Saves a given entity.
     *
     * @param patientRequest must not be {@literal null}.
     * @return the saved entity; will never be {@literal null}.
     * @throws IllegalArgumentException in case the given {@literal entity} is {@literal null}.
     */
    PatientResponse createPatient(PatientRequest patientRequest);

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException if entity with {@literal id} is not found.
     */
    void deletePatientById(Integer id);

    /**
     * Updates a given entity.
     *
     * @param id must not be {@literal null}.
     * @param patientRequest must not be {@literal null}.
     * @return the saved entity.
     * @throws IllegalArgumentException if entity with {@literal id} is not found.
     */
    PatientResponse updatePatientById(Integer id, PatientRequest patientRequest);
}
