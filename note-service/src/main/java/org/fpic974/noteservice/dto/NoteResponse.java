package org.fpic974.noteservice.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteResponse {

    @NotEmpty
    private String patientId;

    @NotEmpty
    private String note;
}
