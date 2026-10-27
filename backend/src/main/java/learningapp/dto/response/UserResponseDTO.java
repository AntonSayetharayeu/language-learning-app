package learningapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
    private Long id;
    private String login;
    private String role;
    private Double rating;
    private String username;
    private String imgUrl;
    private Set<Long> knownLanguages;
    private Set<Long> languagesToLearn;
}
