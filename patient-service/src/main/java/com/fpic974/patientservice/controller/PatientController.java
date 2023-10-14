package com.fpic974.patientservice.controller;

import com.fpic974.patientservice.dto.PatientResponse;
import com.fpic974.patientservice.service.PatientService;
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
}
