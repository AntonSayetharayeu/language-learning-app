package learningapp.dto.responce;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentResponseDTO {

    private Long documentId;

    @NotBlank
    private String documentPath;

    @NotBlank
    private String documentName;

    @NotBlank
    private Long documentOwnerId;
}
