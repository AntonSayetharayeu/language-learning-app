package learningapp.dto.responce;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY) //or use @Getter for serialization
public class TopicResponseDTO {

    private Long topicId;

    private String topicTitle;

    private String topicDescription;

    private String topicStatus;

    private Long authorId;
}
