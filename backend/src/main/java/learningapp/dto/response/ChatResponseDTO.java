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
public class ChatResponseDTO {
    private Long id;
    private Long lecturerId;
    private Long studentId;
    private LanguageDTO learning;
    private LanguageDTO nativeLanguage;
    private boolean isActive;
}
