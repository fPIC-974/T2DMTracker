package org.fpic974.noteservice.repository;

import org.fpic974.noteservice.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
    boolean existsByPatientId(String id);
    List<Note> findByPatientId(String id);

    void deleteByPatientId(String id);
}
