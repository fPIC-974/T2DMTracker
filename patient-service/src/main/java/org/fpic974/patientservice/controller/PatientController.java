package org.fpic974.patientservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.fpic974.patientservice.dto.PatientRequest;
import org.fpic974.patientservice.dto.PatientResponse;
import org.fpic974.patientservice.exception.CustomException;
import org.fpic974.patientservice.service.IPatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patient")
@Slf4j
public class PatientController {

    private final IPatientService patientService;

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<PatientResponse> getAllPatients() {
        log.debug(">> Controller GET : /api/patient/list");

        return patientService.getAllPatients();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PatientResponse getPatientById(@RequestParam Integer id) {
        log.debug(">> Controller GET : /api/patient?id={}", id);

        PatientResponse patientResponse;

        try {
            patientResponse = patientService.getPatientById(id);
        } catch (IllegalArgumentException iae) {
            throw new CustomException("Invalid parameter", HttpStatus.BAD_REQUEST);
        }

        return patientResponse;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientResponse createPatient(@Valid @RequestBody PatientRequest patientRequest) {
        log.debug(">> Controller POST : /api/patient/{}", patientRequest);

        return patientService.createPatient(patientRequest);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deletePatientById(Integer id) {
        log.debug(">> Controller DELETE : /api/patient?id={}", id);

        try {
            patientService.deletePatientById(id);
        } catch (IllegalArgumentException iae) {
            throw new CustomException("Invalid parameter", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public PatientResponse updatePatientById(@RequestParam Integer id, @Valid @RequestBody PatientRequest patientRequest) {
        log.debug(">> Controller PUT : /api/patient?id={}/{}", id, patientRequest);

        PatientResponse patientResponse;

        try {
            patientResponse = patientService.updatePatientById(id, patientRequest);
        } catch (IllegalArgumentException iae) {
            throw new CustomException("Invalid parameter", HttpStatus.BAD_REQUEST);
        }

        return patientResponse;
    }
}
