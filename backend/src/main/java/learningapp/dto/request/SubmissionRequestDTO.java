package learningapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmissionRequestDTO {
    @NotNull
    private Long initiatorId;
    @NotBlank
    private String type;
    @NotNull
    private Long submittedLanguageId;
    @NotNull
    private Long nativeLanguageId;
    @NotBlank
    private String description;
}
