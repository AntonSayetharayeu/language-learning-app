package learningapp.dto.responce;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CourseResponseDTO {

    private Long courseId;

    private Long courseNativeLanguageId;

    private List<Long> courseLanguageIds;

    private Long courseCreatorId;
}
