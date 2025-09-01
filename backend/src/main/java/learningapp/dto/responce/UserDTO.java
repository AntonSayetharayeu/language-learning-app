package learningapp.dto.responce;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import learningapp.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
public class UserDTO {
    @JsonProperty
    private Long userID;
    @JsonProperty
    private String userName;
    @JsonProperty
    private String userEmail;
    @JsonProperty
    private LocalDateTime userCreatedAt;
    @JsonProperty
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
}
