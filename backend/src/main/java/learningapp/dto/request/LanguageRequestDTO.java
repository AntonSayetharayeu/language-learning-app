package learningapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LanguageRequestDTO {

    @NotBlank
    private String languageName;

    @NotBlank
    private String languageCode;
}
