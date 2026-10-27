package learningapp.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import learningapp.dto.request.ChatRequestDTO;
import learningapp.dto.request.MessageRequestDTO;
import learningapp.dto.response.ChatResponseDTO;
import learningapp.dto.response.MessageResponseDTO;
import learningapp.mapper.ChatMapper;
import learningapp.mapper.MessageMapper;
import learningapp.model.Chat;
import learningapp.model.Language;
import learningapp.model.Message;
import learningapp.model.User;
import learningapp.repository.ChatRepository;
import learningapp.repository.LanguageRepository;
import learningapp.repository.MessageRepository;
import learningapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final LanguageRepository languageRepository;
    private final ChatMapper chatMapper;
    private final MessageMapper messageMapper;

    @Transactional
    public ChatResponseDTO createChat(ChatRequestDTO dto) {
        User lecturer = userRepository.findById(dto.getLecturerId())
                .orElseThrow(() -> new EntityNotFoundException("Lecturer not found"));
        User student = userRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
        Language learning = languageRepository.findById(dto.getLearningId())
                .orElseThrow(() -> new EntityNotFoundException("Language not found"));
        Language nativeLang = languageRepository.findById(dto.getNativeLanguageId())
                .orElseThrow(() -> new EntityNotFoundException("Language not found"));

        Chat chat = Chat.builder()
                .lecturer(lecturer)
                .student(student)
                .learning(learning)
                .nativeLanguage(nativeLang)
                .isActive(true)
                .build();

        return chatMapper.toChatDTO(chatRepository.save(chat));
    }

    @Transactional
    public void archiveChat(Long id) {
        Chat chat = chatRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chat not found"));
        chat.setActive(false);
        chatRepository.save(chat);
    }

    @Transactional
    public List<ChatResponseDTO> getAllChats() {
        return chatRepository.findAll().stream().
                map(chatMapper::toChatDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ChatResponseDTO getChatById(Long id) {
        Chat chat = chatRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chat not found"));
        return  chatMapper.toChatDTO(chat);
    }

    @Transactional
    public List<ChatResponseDTO> getChatsByUserIdAndStatus(Long userId, boolean isActive) {
        return chatRepository.findByUserIdAndIsActive(userId, isActive).stream()
                .map(chatMapper::toChatDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public MessageResponseDTO sendMessage(MessageRequestDTO dto) {
        Chat chat = chatRepository.findById(dto.getChatId())
                .orElseThrow(() -> new EntityNotFoundException("Chat not found"));
        User sender = userRepository.findById(dto.getSenderId())
                .orElseThrow(() -> new EntityNotFoundException("Sender not found"));

        Message message = Message.builder()
                .chat(chat)
                .sender(sender)
                .text(dto.getText())
                .build();

        return messageMapper.toMessageDTO(messageRepository.save(message));
    }

    @Transactional
    public List<MessageResponseDTO> getMessagesByChatId(Long chatId) {
        return messageRepository.findByChatId(chatId).stream()
                .map(messageMapper::toMessageDTO)
                .collect(Collectors.toList());
    }
}