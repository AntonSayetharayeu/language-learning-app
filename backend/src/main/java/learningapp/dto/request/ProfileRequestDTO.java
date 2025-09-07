package learningapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ProfileRequestDTO {

    private String profileImageUrl;

    private String profileBio;

    @NotBlank
    private String profileGender;
}
