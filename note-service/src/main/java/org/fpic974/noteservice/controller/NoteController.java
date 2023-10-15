package org.fpic974.noteservice.controller;

import lombok.RequiredArgsConstructor;
import org.fpic974.noteservice.dto.NoteRequest;
import org.fpic974.noteservice.dto.NoteResponse;
import org.fpic974.noteservice.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/note")
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NoteResponse> getNotesByPatientId(@RequestParam String id) {
        return noteService.getNotesByPatientId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NoteResponse createNote(@RequestBody NoteRequest noteRequest) {
        return noteService.createNote(noteRequest);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteNotesByPatientId(@RequestParam String id) {
        noteService.deleteNotesByPatientId(id);
    }
}
