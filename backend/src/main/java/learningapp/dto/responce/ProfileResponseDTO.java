package learningapp.dto.responce;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter //for Jackson
@Setter //for Mapper
public class ProfileResponseDTO {

    private Long profileId;

    private String profileImageUrl;

    private String profileBio;

    @NotBlank
    private String profileGender;
}
