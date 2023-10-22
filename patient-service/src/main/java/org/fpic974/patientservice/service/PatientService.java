package org.fpic974.patientservice.service;

import org.fpic974.patientservice.dto.PatientRequest;
import org.fpic974.patientservice.dto.PatientResponse;
import org.fpic974.patientservice.model.Patient;
import org.fpic974.patientservice.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientService implements IPatientService {

    private final PatientRepository patientRepository;

    /**
     * Returns all instances of the type {@code PatientResponse}.
     *
     * @return the list of entities retrieved.
     */
    @Override
    public List<PatientResponse> getAllPatients() {
        log.debug(">> Calling method : getAllPatients()");
        List<Patient> patients = patientRepository.findAll();

        log.debug(">> Result         : {} object(s)", patients.size());

        return patients.stream()
                .map(patient -> PatientResponse.builder()
                        .id(patient.getId())
                        .lastName(patient.getLastName())
                        .firstName(patient.getFirstName())
                        .birthDate(patient.getBirthDate())
                        .gender(patient.getGender())
                        .address(patient.getAddress())
                        .phone(patient.getPhone())
                        .build()).toList();
    }

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be null.
     * @return the entity with the given id.
     * @throws IllegalArgumentException if entity with {@literal id} is not found.
     */
    @Override
    public PatientResponse getPatientById(Integer id) {
        log.debug(">> Calling method : getPatientById({})", id);

        Optional<Patient> patient = patientRepository.findById(id);

        if (patient.isEmpty()) {
            log.debug("<< No object found with id {}", id);
            throw new IllegalArgumentException("Invalid id");
        }

        Patient patientFound = patient.get();

        log.debug("<< Result         : " + patientFound.getId());

        return PatientResponse.builder()
                .id(patientFound.getId())
                .lastName(patientFound.getLastName())
                .firstName(patientFound.getFirstName())
                .birthDate(patientFound.getBirthDate())
                .gender(patientFound.getGender())
                .address(patientFound.getAddress())
                .phone(patientFound.getPhone())
                .build();
    }

    /**
     * Saves a given entity.
     *
     * @param patientRequest must not be {@literal null}.
     * @return the saved entity; will never be {@literal null}.
     * @throws IllegalArgumentException in case the given {@literal entity} is {@literal null}.
     */
    @Override
    public PatientResponse createPatient(PatientRequest patientRequest) {
        log.debug(">> Calling method : createPatient({})", patientRequest);

        Patient patient = Patient.builder()
                .lastName(patientRequest.getLastName())
                .firstName(patientRequest.getFirstName())
                .birthDate(patientRequest.getBirthDate())
                .gender(patientRequest.getGender())
                .address(patientRequest.getAddress())
                .phone(patientRequest.getPhone())
                .build();

        Patient patientSaved = patientRepository.save(patient);

        log.debug("<< Result         : object with id {} created", patient.getId());

        return PatientResponse.builder()
                .id(patientSaved.getId())
                .lastName(patientSaved.getLastName())
                .firstName(patientSaved.getFirstName())
                .birthDate(patientSaved.getBirthDate())
                .gender(patientSaved.getGender())
                .address(patientSaved.getAddress())
                .phone(patientSaved.getPhone())
                .build();
    }


    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException if entity with {@literal id} is not found.
     */
    @Override
    public void deletePatientById(Integer id) {
        log.debug(">> Calling method : deletePatientById({})", id);

        if (!patientRepository.existsById(id)) {
            log.debug("<< No object found with id {}", id);
            throw new IllegalArgumentException("Invalid id");
        }

        patientRepository.deleteById(id);

        log.debug("<< Result         : object with id {} deleted", id);
    }

    /**
     * Updates a given entity.
     *
     * @param id must not be {@literal null}.
     * @param patientRequest must not be {@literal null}.
     * @return the saved entity.
     * @throws IllegalArgumentException if entity with {@literal id} is not found.
     */
    @Override
    public PatientResponse updatePatientById(Integer id, PatientRequest patientRequest) {
        log.debug(">> Calling method : updatePatientById({}, {})", id, patientRequest);

        if (!patientRepository.existsById(id)) {
            log.debug("<< No object found with id {}", id);
            throw new IllegalArgumentException("Invalid id");
        }

        Patient patient = Patient.builder()
                .id(id)
                .lastName(patientRequest.getLastName())
                .firstName(patientRequest.getFirstName())
                .birthDate(patientRequest.getBirthDate())
                .gender(patientRequest.getGender())
                .address(patientRequest.getAddress())
                .phone(patientRequest.getPhone())
                .build();

        patientRepository.save(patient);

        log.debug(">> Result         : object with id {} updated", patient.getId());

        return PatientResponse.builder()
                .id(patient.getId())
                .lastName(patient.getLastName())
                .firstName(patient.getFirstName())
                .birthDate(patient.getBirthDate())
                .gender(patient.getGender())
                .address(patient.getAddress())
                .phone(patient.getPhone())
                .build();
    }
}
