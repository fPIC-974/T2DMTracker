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
public class NoteService implements INoteService {

    private final NoteRepository noteRepository;

    @Override
    public List<NoteResponse> getNotesByPatientId(String id) {
        log.debug(">> Calling method : getNotesByPatientId({})", id);

        if (!noteRepository.existsByPatientId(id)) {
            log.debug("<< No object found with id {}", id);
            throw new IllegalArgumentException("Invalid id");
        }

        List<Note> notes = noteRepository.findByPatientId(id);

        log.debug(">> Result         : {} object(s)", notes.size());

        return notes.stream()
                .map(note -> NoteResponse.builder()
                        .patientId(note.getPatientId())
                        .note(note.getNote())
                        .build()).toList();
    }

    @Override
    public NoteResponse createNote(NoteRequest noteRequest) {
        log.debug(">> Calling method : createNote({})", noteRequest);

        Note note = Note.builder()
                .patientId(noteRequest.getPatientId())
                .note(noteRequest.getNote())
                .build();

        Note noteSaved = noteRepository.save(note);

        log.debug("<< Result         : object with id {} created", noteSaved.getId());

        return NoteResponse.builder()
                .patientId(note.getPatientId())
                .note(note.getNote())
                .build();
    }

    @Override
    public void deleteNotesByPatientId(String id) {
        log.debug(">> Calling method : deleteNotesByPatientId({})", id);

        if (!noteRepository.existsByPatientId(id)) {
            log.debug("<< No object found with id {}", id);
            throw new IllegalArgumentException("Invalid id");
        }

        noteRepository.deleteByPatientId(id);

        log.debug("<< Result         : objects with patientId {} deleted", id);
    }
}
