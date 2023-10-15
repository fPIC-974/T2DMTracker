package org.fpic974.riskservice.controller;

import lombok.RequiredArgsConstructor;
import org.fpic974.riskservice.service.RiskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RiskController {

    private final RiskService riskService;

    @GetMapping("/api/risk")
    public String getRiskAssessmentByPatientId(@RequestParam Integer id) {
        return riskService.getRiskAssessmentByPatientId(id);
    }
}
