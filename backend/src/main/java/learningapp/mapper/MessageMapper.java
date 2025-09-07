package learningapp.mapper;

import learningapp.dto.request.MessageRequestDTO;
import learningapp.dto.responce.MessageResponseDTO;
import learningapp.model.Chat;
import learningapp.model.Message;
import learningapp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(source = "messageContent", target = "content")
    @Mapping(source = "messageChatId", target = "chat", qualifiedByName = "mapMessageChatIdToChat")
    @Mapping(source = "messageSenderId", target = "sender", qualifiedByName = "mapMessageSenderIdToUser")
    Message toEntity(MessageRequestDTO dto);

    @Mapping(source = "id", target = "messageId")
    @Mapping(source = "content", target = "messageContent")
    @Mapping(source = "createdAt", target = "sentAt")
    @Mapping(source = "readAt", target = "messageReadAt")
    @Mapping(source = "chat.id", target = "messageChatId")
    @Mapping(source = "sender.id", target = "messageSenderId")
    MessageResponseDTO toResponseDTO(Message message);

    @Named("mapMessageChatIdToChat")
    default Chat mapMessageChatIdToChat(Long id) {
        return id == null ? null : new Chat(id);
    }

    @Named("mapMessageSenderIdToUser")
    default User mapMessageSenderIdToUser(Long id) {
        return id == null ? null : new User(id);
    }
}
