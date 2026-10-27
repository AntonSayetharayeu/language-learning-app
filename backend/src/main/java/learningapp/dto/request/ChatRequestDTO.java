package learningapp.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRequestDTO {
    @NotNull
    private Long lecturerId;
    @NotNull
    private Long studentId;
    @NotNull
    private Long learningId;
    @NotNull
    private Long nativeLanguageId;
}
