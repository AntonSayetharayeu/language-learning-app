package learningapp.service;

import learningapp.model.Message;
import learningapp.repository.MessageRepository;
import learningapp.service.interfaces.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService implements IMessageService {

    private final MessageRepository repository;

    @Autowired
    public MessageService(MessageRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Message> findAllMessages() {
        return repository.findAll();
    }

    @Override
    public Optional<Message> findMessageById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Message createMessage(Message message) {
        return repository.save(message);
    }

    @Override
    public Message updateMessage(Long id, Message message) {
        Message existingMessage = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found"));

        existingMessage.setContent(message.getContent());

        return repository.save(existingMessage);
    }

    @Override
    public Message readMessage(Long id) {
        Message unreadMessage = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found"));

        unreadMessage.setReadAt(LocalDateTime.now());

        return repository.save(unreadMessage);
    }

    @Override
    public Optional<Message> deleteMessageById(Long id) {
        return repository.findById(id)
                .map(message -> {
                    repository.delete(message);
                    return message;
                });
    }
}
