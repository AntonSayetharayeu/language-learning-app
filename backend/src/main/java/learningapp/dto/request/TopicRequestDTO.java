package learningapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicRequestDTO {
    @NotBlank
    private String text;
    @NotNull
    private String category;
}
