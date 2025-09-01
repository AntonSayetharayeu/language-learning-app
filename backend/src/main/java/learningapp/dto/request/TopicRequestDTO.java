package learningapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TopicRequestDTO {

    @NotBlank
    private String topicTitle;

    private String topicDescription;

    @NotBlank
    private String topicStatus;

    @NotBlank
    private Long authorId;
}
