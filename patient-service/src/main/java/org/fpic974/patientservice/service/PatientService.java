package org.fpic974.patientservice.service;

import org.fpic974.patientservice.dto.PatientRequest;
import org.fpic974.patientservice.dto.PatientResponse;
import org.fpic974.patientservice.model.Patient;
import org.fpic974.patientservice.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientService implements IPatientService {

    private final PatientRepository patientRepository;

    @Override
    public List<PatientResponse> getAllPatients() {
        log.debug("Service Call : getAllPatients()");
        List<Patient> patients = patientRepository.findAll();

        log.debug("Result : " + patients);

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

    @Override
    public PatientResponse getPatientById(Integer id) {
        log.debug("Service Call : getPatientById({})", id);

        Patient patient = patientRepository.findById(id)
                .orElseThrow();

        log.debug("Result : " + patient);

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

    @Override
    public PatientResponse createPatient(PatientRequest patientRequest) {
        log.debug("Service Call : createPatient({})", patientRequest);

        Patient patient = Patient.builder()
                .lastName(patientRequest.getLastName())
                .firstName(patientRequest.getFirstName())
                .birthDate(patientRequest.getBirthDate())
                .gender(patientRequest.getGender())
                .address(patientRequest.getAddress())
                .phone(patientRequest.getPhone())
                .build();

        patientRepository.save(patient);

        log.debug("Result : Patient with id {} created", patient.getId());

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

    @Override
    public void deletePatientById(Integer id) {
        log.debug("Service Call : deletePatientById({})", id);

        patientRepository.deleteById(id);

        log.debug("Result : Patient with id {} deleted", id);
    }

    @Override
    public PatientResponse updatePatientById(Integer id, PatientRequest patientRequest) {
        log.debug("Service Call : updatePatientById({}, {})", id, patientRequest);

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

        log.debug("Result : Patient with id {} updated", patient.getId());

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
