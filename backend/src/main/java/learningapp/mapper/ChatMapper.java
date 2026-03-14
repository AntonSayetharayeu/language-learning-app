package learningapp.mapper;

import learningapp.dto.request.ChatRequestDTO;
import learningapp.dto.responce.ChatResponseDTO;
import learningapp.model.Chat;
import learningapp.model.Message;
import learningapp.model.User;
import learningapp.model.base.ModelClass;
import learningapp.model.jointable.ChatUser;
import learningapp.model.jointable.TopicChat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ChatMapper {

    @Mapping(source = "chatName", target = "name")
    @Mapping(source = "chatType", target = "chatType")
    @Mapping(source = "chatParticipantsIds", target = "chatUsers", qualifiedByName = "mapChatParticipantsIdsToChatUsers")
    Chat toEntity(ChatRequestDTO dto);

    @Mapping(source = "id", target = "chatId")
    @Mapping(source = "name", target = "chatName")
    @Mapping(source = "chatType", target = "chatType")
    @Mapping(source = "chatUsers", target = "chatParticipantsIds", qualifiedByName = "mapChatUsersToChatParticipantsIds")
    @Mapping(source = "chatTopics", target = "chatTopicsIds", qualifiedByName = "mapChatTopicsToChatTopicsIds")
    @Mapping(source = "chatMessages", target = "chatMessagesIds", qualifiedByName = "mapChatMessagesToChatMessagesIds")
    ChatResponseDTO toResponseDTO(Chat chat);

    @Named("mapChatParticipantsIdsToChatUsers")
    default List<ChatUser> mapChatParticipantsIdsToChatUsers(List<Long> usersIds) {
        return usersIds == null ? null : usersIds.stream()
                .map(id -> {
                    ChatUser chatUser = new ChatUser();
                    chatUser.setUser(new User(id));
                    return chatUser;
                }).collect(Collectors.toList());
    }

    @Named("mapChatUsersToChatParticipantsIds")
    default List<Long> mapChatUsersToChatParticipantsIds(List<ChatUser> chatUsers) {
        return chatUsers == null ? null : chatUsers.stream()
                .map(chatUser -> chatUser.getUser().getId())
                .collect(Collectors.toList());
    }

    @Named("mapChatTopicsToChatTopicsIds")
    default List<Long> mapChatTopicsToChatTopicsIds(List<TopicChat> chatTopics) {
        return chatTopics == null ? null : chatTopics.stream()
                .map(topicChat -> topicChat.getTopic().getId())
                .collect(Collectors.toList());
    }

    @Named("mapChatMessagesToChatMessagesIds")
    default List<Long> mapChatMessagesToChatMessagesIds(List<Message> messages) {
        return messages == null ? null : messages.stream()
                .map(ModelClass::getId)
                .collect(Collectors.toList());
    }
}
