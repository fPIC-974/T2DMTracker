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
public class Patient {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

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
