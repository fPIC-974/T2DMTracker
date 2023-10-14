package com.fpic974.patientservice.controller;

import com.fpic974.patientservice.dto.PatientRequest;
import com.fpic974.patientservice.dto.PatientResponse;
import com.fpic974.patientservice.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patient")
public class PatientController {

    private final PatientService patientService;

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<PatientResponse> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PatientResponse getPatientById(@RequestParam Integer id) {
        return patientService.getPatientById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientResponse createPatient(@Valid @RequestBody PatientRequest patientRequest) {
        return patientService.createPatient(patientRequest);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deletePatientById(Integer id) {
        patientService.deletePatientById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public PatientResponse updatePatientById(@RequestParam Integer id, @Valid @RequestBody PatientRequest patientRequest) {
        return patientService.updatePatientById(id, patientRequest);
    }
}
