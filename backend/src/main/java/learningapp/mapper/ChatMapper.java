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

import java.util.ArrayList;
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
        if (usersIds == null) {
            return null;
        }

        List<ChatUser> chatUsers = new ArrayList<>(usersIds.size());
        for (Long id : usersIds) {
            ChatUser chatUser = new ChatUser();
            chatUser.setUser(new User(id));
            chatUsers.add(chatUser);
        }
        return chatUsers;
    }

    @Named("mapChatUsersToChatParticipantsIds")
    default List<Long> mapChatUsersToChatParticipantsIds(List<ChatUser> chatUsers) {
        if (chatUsers == null) {
            return null;
        }

        List<Long> usersIds = new ArrayList<>(chatUsers.size());
        for (ChatUser chatUser : chatUsers) {
            usersIds.add(chatUser.getUser().getId());
        }
        return usersIds;
    }

    @Named("mapChatTopicsToChatTopicsIds")
    default List<Long> mapChatTopicsToChatTopicsIds(List<TopicChat> chatTopics) {
        if (chatTopics == null) {
            return null;
        }

        List<Long> topicIds = new ArrayList<>(chatTopics.size());
        for (TopicChat topicChat : chatTopics) {
            topicIds.add(topicChat.getTopic().getId());
        }
        return topicIds;
    }

    @Named("mapChatMessagesToChatMessagesIds")
    default List<Long> mapChatMessagesToChatMessagesIds(List<Message> messages) {
        if (messages == null) {
            return null;
        }

        List<Long> messageIds = new ArrayList<>(messages.size());
        for (ModelClass message : messages) {
            messageIds.add(message.getId());
        }
        return messageIds;
    }
}
