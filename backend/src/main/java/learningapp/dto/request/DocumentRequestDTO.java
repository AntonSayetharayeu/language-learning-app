package learningapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DocumentRequestDTO {

    @NotBlank
    private String documentPath;

    @NotBlank
    private String documentName;

    @NotBlank
    private Long documentOwnerId;
}
