package org.fpic974.riskservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fpic974.riskservice.exception.CustomException;
import org.fpic974.riskservice.service.IRiskService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RiskController {

    private final IRiskService riskService;

    @GetMapping("/api/risk")
    @ResponseStatus(HttpStatus.OK)
    public String getRiskAssessmentByPatientId(@RequestParam Integer id, HttpServletRequest request) {
        log.debug(">> Controller GET : /api/risk?id={}", id);

        String token = request.getHeader("Authorization");

        if (token == null) {
            throw new CustomException("No authorization token found", HttpStatus.BAD_REQUEST);
        }

        return riskService.getRiskAssessmentByPatientId(id, token);
    }
}
