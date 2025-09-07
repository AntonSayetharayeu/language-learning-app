package learningapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.List;

@Getter
public class CourseRequestDTO {

    @NotBlank
    private Long courseNativeLanguageId;

    @NotEmpty
    private List<@NotBlank Long> courseLanguageIds;
    
    @NotBlank
    private Long courseCreatorId;
}
