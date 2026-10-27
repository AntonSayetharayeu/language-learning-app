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
public class OfferResponseDTO { //Remember it is Submission
    private Long id;
    private Long lecturerId;
    private LanguageDTO teaching;
    private LanguageDTO nativeLanguage;
    private String description;
}
