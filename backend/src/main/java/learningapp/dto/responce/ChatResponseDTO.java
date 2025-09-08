package learningapp.dto.responce;

import learningapp.model.enums.ChatType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatResponseDTO {

    private Long chatId;

    private String chatName;

    private ChatType chatType;

    private List<Long> chatParticipantsIds;

    private List<Long> chatTopicsIds;

    private List<Long> chatMessagesIds;
}
