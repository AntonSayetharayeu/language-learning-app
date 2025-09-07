package learningapp.service.interfaces;

import learningapp.model.Message;

import java.util.List;
import java.util.Optional;

public interface IMessageService {

    List<Message> findAllMessages();

    Optional<Message> findMessageById(Long id);

    Message createMessage(Message message);

    Message updateMessage(Long id, Message message);

    Message readMessage(Long id);

    Optional<Message> deleteMessageById(Long id);
}
