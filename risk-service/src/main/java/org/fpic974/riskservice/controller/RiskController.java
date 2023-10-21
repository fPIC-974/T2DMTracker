package org.fpic974.riskservice.controller;

import jakarta.servlet.http.HttpServletRequest;
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
    public String getRiskAssessmentByPatientId(@RequestParam Integer id, HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        return riskService.getRiskAssessmentByPatientId(id, token);
    }
}
