package learningapp.dto.response;

import learningapp.dto.common.LanguageDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestResponseDTO { //Remember it is Submission
    private Long id;
    private Long studentId;
    private LanguageDTO learning;
    private LanguageDTO nativeLanguage;
    private String description;
}
