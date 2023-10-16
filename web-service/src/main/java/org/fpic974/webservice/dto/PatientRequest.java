package org.fpic974.webservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PatientRequest {

    @NotBlank(message = "Last Name is mandatory")
    private String lastName;

    @NotBlank(message = "First Name is mandatory")
    private String firstName;

    @NotNull(message = "Birth Date is mandatory")
    private LocalDate birthDate;

    @NotNull(message = "Gender is mandatory")
    private Character gender;

    private String address;

    private String phone;
}
