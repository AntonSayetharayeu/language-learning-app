package learningapp.service.interfaces;

import learningapp.model.Chat;
import learningapp.model.Topic;
import learningapp.model.User;

import java.util.List;
import java.util.Optional;

public interface IChatService {

    List<Chat> findAllChats();

    Optional<Chat> findChatById(Long id);

    Chat createChat(Long chatCreatorId, Chat chat);

    Chat updateChat(Long id, Chat chat);

    Chat addTopic(Long id, Topic topic);

    Chat addUsers(Long id, List<User> users);

    Chat removeUsers(Long id, List<User> users);

    Optional<Chat> deleteChatById(Long id);
}
