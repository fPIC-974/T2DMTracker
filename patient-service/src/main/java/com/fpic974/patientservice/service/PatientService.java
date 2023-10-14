package com.fpic974.patientservice.service;

import com.fpic974.patientservice.dto.PatientResponse;
import com.fpic974.patientservice.model.Patient;
import com.fpic974.patientservice.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientService {

    private final PatientRepository patientRepository;

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
}
