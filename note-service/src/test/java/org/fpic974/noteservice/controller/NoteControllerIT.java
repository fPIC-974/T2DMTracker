package org.fpic974.noteservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fpic974.noteservice.dto.NoteRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NoteControllerIT {
        @Autowired
    public MockMvc mockMvc;

    @Test
    public void shouldGetNotesByPatientId() throws Exception {
        mockMvc.perform(get("/api/note")
                        .param("id", "4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].patientId", is("4")))
                .andExpect(jsonPath("$[0].note", containsString("Il se plaint")));
    }

    @Test
    public void shouldCreateNote() throws Exception {
        NoteRequest noteRequest = NoteRequest.builder()
                .patientId("8")
                .note("Test\r\nde\r\nnote")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/note")
                        .param("id", "8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(noteRequest))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.patientId", is("8")))
                .andExpect(jsonPath("$.note", is("Test\r\nde\r\nnote")));
    }

    @Test
    public void shouldDeleteNotesByPatientId() {
        assertThatCode(() -> mockMvc.perform(delete("/api/note")
                        .param("id", "8"))
                .andExpect(status().isOk()))
                .doesNotThrowAnyException();
    }
}