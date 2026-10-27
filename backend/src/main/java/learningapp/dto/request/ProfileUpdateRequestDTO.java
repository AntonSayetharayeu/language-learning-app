package learningapp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileUpdateRequestDTO {
    private String username;
    private String imgUrl;
    private Set<Long> knownLanguages;
    private Set<Long> languagesToLearn;
}
