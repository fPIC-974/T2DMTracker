package org.fpic974.patientservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.transaction.Transactional;
import org.fpic974.patientservice.dto.PatientRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.CoreMatchers.is;


@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerIT {
    @Autowired
    public MockMvc mockMvc;

    @Test
    public void shouldGetPatients() throws Exception {
        mockMvc.perform(get("/api/patient/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].lastName", is("TestNode")))
                .andExpect(jsonPath("$[0].firstName", is("Test")))
                .andExpect(jsonPath("$[3].lastName", is("TestEarlyOnSet")))
                .andExpect(jsonPath("$[3].firstName", is("Test")));
    }

    @Test
    public void shouldGetPatientById() throws Exception {
        mockMvc.perform(get("/api/patient")
                        .param("id", "4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName", is("TestEarlyOnSet")))
                .andExpect(jsonPath("$.firstName", is("Test")));
    }

    @Test
    public void shouldNotGetPatientByInvalidId() throws Exception {
        mockMvc.perform(get("/api/patient")
                        .param("id", "44"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void shouldCreatePatient() throws Exception {

        PatientRequest patientRequest = PatientRequest.builder()
                .lastName("Doe")
                .firstName("John")
                .birthDate(LocalDate.now())
                .gender('M')
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        mockMvc.perform(post("/api/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.lastName", is("Doe")))
                .andExpect(jsonPath("$.firstName", is("John")));
    }

    @Test
    @Transactional
    public void shouldDeletePatientById() {
        assertThatCode(() -> mockMvc.perform(delete("/api/patient")
                        .param("id", "4"))
                .andExpect(status().isOk()))
                .doesNotThrowAnyException();
    }

    @Test
    @Transactional
    public void shouldNotDeletePatientByInvalidId() throws Exception {
        mockMvc.perform(delete("/api/patient")
                        .param("id", "44"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void shouldUpdatePatientById() throws Exception {
        PatientRequest patientRequest = PatientRequest.builder()
                .lastName("Doe")
                .firstName("John")
                .gender('M')
                .birthDate(LocalDate.now())
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        mockMvc.perform(put("/api/patient")
                        .param("id", "4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName", is("Doe")))
                .andExpect(jsonPath("$.firstName", is("John")));
    }

    @Test
    @Transactional
    public void shouldNotUpdatePatientByInvalidId() throws Exception {
        PatientRequest patientRequest = PatientRequest.builder()
                .lastName("Doe")
                .firstName("John")
                .gender('M')
                .birthDate(LocalDate.now())
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        mockMvc.perform(put("/api/patient")
                        .param("id", "44")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}