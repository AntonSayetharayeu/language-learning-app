package learningapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageRequestDTO {
    @NotNull
    private Long chatId;
    @NotNull
    private Long senderId;
    @NotBlank
    private String text;
}
