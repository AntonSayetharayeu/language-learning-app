package learningapp.service;

import learningapp.model.Chat;
import learningapp.model.Topic;
import learningapp.model.User;
import learningapp.model.enums.ChatRole;
import learningapp.model.enums.ChatType;
import learningapp.model.jointable.ChatUser;
import learningapp.model.jointable.TopicChat;
import learningapp.repository.ChatRepository;
import learningapp.repository.UserRepository;
import learningapp.service.interfaces.IChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatService implements IChatService {

    private final ChatRepository chatRepository;

    private final UserRepository userRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Chat> findAllChats() {
        return chatRepository.findAll();
    }

    @Override
    public Optional<Chat> findChatById(Long id) {
        return chatRepository.findById(id);
    }

    @Override
    public Chat createChat(Long chatCreatorId, Chat chat) {
        checkChatTypeAndParticipantsNumber(chat);
        List<ChatUser> chatUsers = chat.getChatUsers();

        chatUsers.stream()
                .peek(chatUser -> {
                    User user = userRepository.findById(chatUser.getUser().getId())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User during Chat creation not found"));
                    chatUser.setUser(user);
                    chatUser.setChatRole(ChatRole.STUDENT);
                })
                .collect(Collectors.toList());

        User creator = userRepository.findById(chatCreatorId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Creator user during Chat creation not found"));
        ChatUser creatorRelation = new ChatUser();
        creatorRelation.setUser(creator);
        creatorRelation.setChatRole(ChatRole.TEACHER);
        chatUsers.add(creatorRelation);

        chat.setChatUsers(chatUsers);
        return chatRepository.save(chat);
    }

    @Override
    public Chat updateChat(Long id, Chat chat) {
        Chat existingChat = chatRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chat not found"));

        existingChat.setName(chat.getName());
        checkChatTypeAndParticipantsNumber(chat);
        existingChat.setChatType(chat.getChatType());

        return chatRepository.save(existingChat);
    }

    @Override
    public Chat addTopic(Long id, Topic topic) {
        Chat existingChat = chatRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chat not found"));

        TopicChat topicRelation = new TopicChat();
        topicRelation.setTopic(topic);
        topicRelation.setChat(existingChat);
        topicRelation.setAddedAt(LocalDateTime.now());
        existingChat.getChatTopics().add(topicRelation);

        return chatRepository.save(existingChat);
    }

    @Override
    public Chat addUsers(Long id, List<User> users) {
        return null;
    }

    @Override
    public Chat removeUsers(Long id, List<User> users) {
        return null;
    }

    @Override
    public Optional<Chat> deleteChatById(Long id) {
        return Optional.empty();
    }

    private void checkChatTypeAndParticipantsNumber(Chat chat) {
        if (chat.getChatType() == ChatType.INDIVIDUAL && chat.getChatUsers().size() > 1) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Chat with more than 2 people cannot be individual");
        }
    }
}
