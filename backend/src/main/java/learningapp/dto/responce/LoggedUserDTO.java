package learningapp.dto.responce;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
//JsonAutoDetect
public class LoggedUserDTO {

    private Long userID;

    private String userName;
}
