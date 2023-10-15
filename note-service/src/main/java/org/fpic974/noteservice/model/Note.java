package org.fpic974.noteservice.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Note {
    @Id
    private String id;

    @NotNull
    private String patientId;

    @NotNull
    private String note;
}
