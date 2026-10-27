package learningapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PingResponseDTO {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private Long basedOnId;
    private String basedOnType;
    private String status;
    private Long createdAt;
}
