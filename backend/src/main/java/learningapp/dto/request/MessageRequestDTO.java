package learningapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MessageRequestDTO {

    @NotBlank
    private String messageContent;

    @NotBlank
    private Long messageChatId;

    @NotBlank
    private Long messageSenderId;
}
