package org.fpic974.patientservice.service;

import org.fpic974.patientservice.dto.PatientRequest;
import org.fpic974.patientservice.dto.PatientResponse;

import java.util.List;

public interface IPatientService {
    List<PatientResponse> getAllPatients();

    PatientResponse getPatientById(Integer id);

    PatientResponse createPatient(PatientRequest patientRequest);

    void deletePatientById(Integer id);

    PatientResponse updatePatientById(Integer id, PatientRequest patientRequest);
}
