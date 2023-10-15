package org.fpic974.patientservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientRequest {

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String firstName;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private Character gender;

    private String address;

    private String phone;
}
