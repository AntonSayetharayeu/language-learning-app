package learningapp.dto.responce;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageResponseDTO {

    private Long messageId;

    private String messageContent;

    private LocalDateTime sentAt;

    private LocalDateTime messageReadAt;

    private Long messageChatId;

    private Long messageSenderId;
}
