package org.fpic974.noteservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fpic974.noteservice.dto.NoteRequest;
import org.fpic974.noteservice.dto.NoteResponse;
import org.fpic974.noteservice.exception.CustomException;
import org.fpic974.noteservice.service.INoteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/note")
@Slf4j
public class NoteController {

    private final INoteService noteService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NoteResponse> getNotesByPatientId(@RequestParam String id) {
        log.debug(">> Controller GET : /api/note?id={}", id);

        List<NoteResponse> notes;

        try {
            notes = noteService.getNotesByPatientId(id);
        } catch (IllegalArgumentException iae) {
            throw new CustomException(iae, HttpStatus.BAD_REQUEST);
        }

        return notes;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NoteResponse createNote(@RequestBody NoteRequest noteRequest) {
        log.debug(">> Controller POST : /api/note/{}", noteRequest);

        return noteService.createNote(noteRequest);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteNotesByPatientId(@RequestParam String id) {
        log.debug(">> Controller DELETE : /api/note?id={}", id);

        try {
            noteService.deleteNotesByPatientId(id);
        } catch (IllegalArgumentException iae) {
            throw new CustomException(iae, HttpStatus.BAD_REQUEST);
        }
    }
}
