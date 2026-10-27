package learningapp.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingRequestDTO {
    @NotNull
    private Long userId;
    @NotNull
    private Long reporterId;
    @NotNull
    private Double value;
    private String description;
}
