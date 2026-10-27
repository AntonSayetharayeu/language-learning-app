package learningapp.controller;

import jakarta.validation.Valid;
import learningapp.dto.request.ChatRequestDTO;
import learningapp.dto.request.MessageRequestDTO;
import learningapp.dto.response.ChatResponseDTO;
import learningapp.dto.response.MessageResponseDTO;
import learningapp.dto.response.TopicResponseDTO;
import learningapp.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController { //Chat and Messages

    private final ChatService chatService;

    @GetMapping
    public ResponseEntity<List<ChatResponseDTO>> getAllChats() {
        return ResponseEntity.ok(chatService.getAllChats());
    }

    @PostMapping
    public ResponseEntity<ChatResponseDTO> createChat(@Valid @RequestBody ChatRequestDTO dto) {
        return ResponseEntity.ok(chatService.createChat(dto));
    }

    @PatchMapping("/{id}/archive")
    public ResponseEntity<Void> archiveChat(@PathVariable Long id) {
        chatService.archiveChat(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatResponseDTO> getChatById(@PathVariable Long id) {
        return ResponseEntity.ok(chatService.getChatById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ChatResponseDTO>> getChatsByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "true") boolean isActive) {
        return ResponseEntity.ok(chatService.getChatsByUserIdAndStatus(userId, isActive));
    }

    @PostMapping("/messages")
    public ResponseEntity<MessageResponseDTO> sendMessage(@Valid @RequestBody MessageRequestDTO dto) {
        return ResponseEntity.ok(chatService.sendMessage(dto));
    }

    @GetMapping("/{chatId}/messages")
    public ResponseEntity<List<MessageResponseDTO>> getMessagesByChatId(@PathVariable Long chatId) {
        return ResponseEntity.ok(chatService.getMessagesByChatId(chatId));
    }
}