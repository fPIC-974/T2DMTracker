package org.fpic974.noteservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fpic974.noteservice.dto.NoteRequest;
import org.fpic974.noteservice.dto.NoteResponse;
import org.fpic974.noteservice.model.Note;
import org.fpic974.noteservice.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteService {

    private final NoteRepository noteRepository;

    public List<NoteResponse> getNotesByPatientId(String id) {
        log.debug("Calling Service : getNotesByPatientId({})", id);

        List<Note> notes = noteRepository.findByPatientId(id);

        log.debug("Result : " + notes);

        return notes.stream()
                .map(note -> NoteResponse.builder()
                        .patientId(note.getPatientId())
                        .note(note.getNote())
                        .build()).toList();
    }

    public NoteResponse createNoteByPatientId(String id, NoteRequest noteRequest) {
        log.debug("Calling Service : createNoteByPatientId({}, {}", id, noteRequest);

        Note note = Note.builder()
                .patientId(id)
                .note(noteRequest.getNote())
                .build();

        noteRepository.save(note);

        log.debug("Result : Note with id {} created", note.getId());

        return NoteResponse.builder()
                .patientId(note.getPatientId())
                .note(note.getNote())
                .build();
    }
}
