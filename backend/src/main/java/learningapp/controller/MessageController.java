package learningapp.controller;

import jakarta.validation.Valid;
import learningapp.dto.request.MessageRequestDTO;
import learningapp.dto.responce.MessageResponseDTO;
import learningapp.mapper.MessageMapper;
import learningapp.service.interfaces.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    private final IMessageService messageService;

    private final MessageMapper messageMapper;

    @Autowired
    public MessageController(IMessageService messageService, MessageMapper messageMapper) {
        this.messageService = messageService;
        this.messageMapper = messageMapper;
    }

    @GetMapping
    public ResponseEntity<List<MessageResponseDTO>> getAllMessages() {
        return ResponseEntity.ok(messageService.findAllMessages()
                .stream()
                .map(messageMapper::toResponseDTO)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> getMessageByID(@PathVariable Long id) {
        return messageService.findMessageById(id)
                .map(message -> ResponseEntity.ok(messageMapper.toResponseDTO(message)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MessageResponseDTO> addNewMessage(@Valid @RequestBody MessageRequestDTO dto) {
        return ResponseEntity.ok(
                messageMapper.toResponseDTO(
                        messageService.createMessage(messageMapper.toEntity(dto))));

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MessageResponseDTO> updateMessage(@PathVariable Long id, @Valid @RequestBody MessageRequestDTO dto) {
        return ResponseEntity.ok(
                messageMapper.toResponseDTO(
                        messageService.updateMessage(id, messageMapper.toEntity(dto))));
    }

    @PutMapping("/read/{id}")
    public ResponseEntity<MessageResponseDTO> readMessage(@PathVariable Long id) {
        return ResponseEntity.ok(
                messageMapper.toResponseDTO(
                        messageService.readMessage(id)));
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<MessageResponseDTO> removeMessage(@PathVariable Long id) {
        return messageService.deleteMessageById(id)
                .map(message -> ResponseEntity.ok(messageMapper.toResponseDTO(message)))
                .orElse(ResponseEntity.notFound().build());
    }
}
