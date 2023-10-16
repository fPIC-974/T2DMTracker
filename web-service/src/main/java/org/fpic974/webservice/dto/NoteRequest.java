package org.fpic974.webservice.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteRequest {

    private String id;

    @NotEmpty
    private Integer patientId;

    @NotEmpty
    private String note;
}
