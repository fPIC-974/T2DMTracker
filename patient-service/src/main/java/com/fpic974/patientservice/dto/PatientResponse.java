package com.fpic974.patientservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientResponse {

    private Integer id;

    private String lastName;

    private String firstName;

    private LocalDate birthDate;

    private Character gender;

    private String address;

    private String phone;
}
