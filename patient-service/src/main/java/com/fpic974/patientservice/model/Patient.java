package com.fpic974.patientservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.*;

@Entity
@Table(name = "patients")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @NotEmpty(message = "lastName should not be empty")
    private String lastName;

    @NotEmpty(message = "firstName should not be empty")
    private String firstName;

    @NotNull(message = "birthDate should not be null")
    private LocalDate birthDate;

    @NotNull(message = "gender should not be null")
    private Character gender;

    private String address;

    private String phone;
}
