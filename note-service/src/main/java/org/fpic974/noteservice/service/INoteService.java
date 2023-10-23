package org.fpic974.noteservice.service;

import org.fpic974.noteservice.dto.NoteRequest;
import org.fpic974.noteservice.dto.NoteResponse;

import java.util.List;

public interface INoteService {
    /**
     * Retrieves a list of entities by its patient id.
     *
     * @param id must not be null.
     * @return the list of entities with the given id.
     * @throws IllegalArgumentException if given {@literal id} is not found.
     */
    List<NoteResponse> getNotesByPatientId(String id);


    /**
     * Saves a given entity.
     *
     * @param noteRequest must not be {@literal null}.
     * @return the saved entity; will never be {@literal null}.
     */
    NoteResponse createNote(NoteRequest noteRequest);


    /**
     * Deletes the entities for the given patientId.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException if entity with {@literal id} is not found.
     */
    void deleteNotesByPatientId(String id);
}
