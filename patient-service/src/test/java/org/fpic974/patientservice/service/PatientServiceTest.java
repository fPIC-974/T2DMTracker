package org.fpic974.patientservice.service;

import org.fpic974.patientservice.dto.PatientRequest;
import org.fpic974.patientservice.dto.PatientResponse;
import org.fpic974.patientservice.model.Patient;
import org.fpic974.patientservice.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    @Test
    public void shouldGetAllPatients() {
        Patient patient = Patient.builder()
                .id(100)
                .lastName("Doe")
                .firstName("John")
                .gender('M')
                .birthDate(LocalDate.now())
                .build();

        List<Patient> patientList = new ArrayList<>();

        patientList.add(patient);

        when(patientRepository.findAll()).thenReturn(patientList);

        List<PatientResponse> patients = patientService.getAllPatients();

        assertNotNull(patients);
        assertEquals(1, patients.size());
        assertEquals("Doe", patients.get(0).getLastName());
        assertEquals("John", patients.get(0).getFirstName());
        assertEquals('M', patients.get(0).getGender());
    }

    @Test
    public void shouldGetPatientById() {
        Patient patient = Patient.builder()
                .id(100)
                .lastName("Doe")
                .firstName("John")
                .birthDate(LocalDate.now())
                .gender('M')
                .build();

        when(patientRepository.findById(anyInt())).thenReturn(Optional.ofNullable(patient));

        PatientResponse patientResponse = patientService.getPatientById(100);

        assertDoesNotThrow(() -> {});
        assertNotNull(patientResponse);
        assertEquals("Doe", patientResponse.getLastName());
        assertEquals("John", patientResponse.getFirstName());
        assertEquals('M', patientResponse.getGender());
    }

    @Test
    public void shouldNotGetPatientInvalidId() {
        when(patientRepository.findById(anyInt())).thenReturn(Optional.empty());

        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> patientService.getPatientById(100));

        assertTrue(illegalArgumentException.getMessage().contains("Invalid id"));
    }

    @Test
    public void shouldCreatePatient() {
        Patient patient = Patient.builder()
                .id(100)
                .lastName("Doe")
                .firstName("John")
                .birthDate(LocalDate.now())
                .gender('M')
                .build();

        PatientRequest patientRequest = PatientRequest.builder()
                .lastName("Doe")
                .firstName("John")
                .birthDate(LocalDate.now())
                .gender('M')
                .build();

        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        PatientResponse patientResponse = patientService.createPatient(patientRequest);

        assertEquals(100, patientResponse.getId());
        assertEquals("Doe", patientResponse.getLastName());
        assertEquals("John", patientResponse.getFirstName());
        assertEquals('M', patientResponse.getGender());
    }

    @Test
    public void shouldDeletePatientById() {
        when(patientRepository.existsById(anyInt())).thenReturn(true);

        patientService.deletePatientById(100);

        assertDoesNotThrow(() -> {});
    }

    @Test
    public void shouldNotDeletePatientIllegalArgument() {
        when(patientRepository.existsById(anyInt())).thenReturn(false);

        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> patientService.deletePatientById(100));

        assertTrue(illegalArgumentException.getMessage().contains("Invalid id"));
    }

    @Test
    public void shouldUpdatePatient() {
        Patient patient = Patient.builder()
                .id(100)
                .lastName("Doe")
                .firstName("John")
                .birthDate(LocalDate.now())
                .gender('M')
                .build();


        PatientRequest patientRequest = PatientRequest.builder()
                .lastName("Doe")
                .firstName("John")
                .birthDate(LocalDate.now())
                .gender('M')
                .build();

        when(patientRepository.existsById(anyInt())).thenReturn(true);
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        PatientResponse patientResponse = patientService.updatePatientById(100, patientRequest);

        assertDoesNotThrow(() -> {});
        assertEquals(100, patientResponse.getId());
        assertEquals(patient.getFirstName(), patientResponse.getFirstName());
        assertEquals(patient.getLastName(), patientResponse.getLastName());
        assertEquals(patient.getBirthDate(), patientResponse.getBirthDate());
        assertEquals(patient.getGender(), patientResponse.getGender());
    }

    @Test
    public void shouldNotUpdatePatientIllegalArgument() {
        when(patientRepository.existsById(anyInt())).thenReturn(false);

        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> patientService.updatePatientById(100, new PatientRequest()));

        assertTrue(illegalArgumentException.getMessage().contains("Invalid id"));
    }
}