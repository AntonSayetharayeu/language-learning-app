package learningapp.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import learningapp.model.enums.UserRole;
import lombok.Getter;

@Getter
public class RegisterDTO {
    @NotBlank
    private String userName;
    @NotBlank
    @Email
    private String userEmail;
    @NotBlank
    private String userPassword;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
}
