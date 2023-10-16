package org.fpic974.noteservice.service;

import org.fpic974.noteservice.dto.NoteRequest;
import org.fpic974.noteservice.dto.NoteResponse;
import org.fpic974.noteservice.model.Note;
import org.fpic974.noteservice.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    @Test
    public void shouldGetNotesByPatientId() {
        Note note = Note.builder()
                .patientId("5")
                .note("Test\r\nde\r\nnote")
                .build();

        List<Note> noteList = new ArrayList<>();
        noteList.add(note);

        when(noteRepository.findByPatientId(anyString())).thenReturn(noteList);

        List<NoteResponse> notes = noteService.getNotesByPatientId("5");

        assertNotNull(notes);
        assertEquals("5", notes.get(0).getPatientId());
        assertEquals("Test\r\nde\r\nnote", notes.get(0).getNote());
    }

    @Test
    public void shouldCreateNote() {
        NoteRequest noteRequest = NoteRequest.builder()
                .patientId("5")
                .note("Test\r\nde\r\nnote")
                .build();

        NoteResponse noteResponse = noteService.createNote(noteRequest);

        assertNotNull(noteResponse);
        assertEquals("5", noteResponse.getPatientId());
        assertEquals("Test\r\nde\r\nnote", noteResponse.getNote());
    }

    @Test
    public void shouldDeleteNotesByPatientId() {
        noteService.deleteNotesByPatientId("5");

        assertDoesNotThrow(() -> {});
    }
}