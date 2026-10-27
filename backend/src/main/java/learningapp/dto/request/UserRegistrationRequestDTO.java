package learningapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegistrationRequestDTO {
    @NotBlank
    private String login;
    @NotBlank
    private String password;
    @NotBlank
    private String role;
    @NotBlank
    private String username;
}
