package learningapp.dto.request;

import learningapp.model.enums.ChatType;
import lombok.Getter;

import java.util.List;

@Getter
public class ChatRequestDTO {

    private String chatName;

    private ChatType chatType;

    private List<Long> chatParticipantsIds;
}
