package learningapp.model.jointable;

import jakarta.persistence.*;
import learningapp.model.Chat;
import learningapp.model.Topic;
import learningapp.model.id.TopicChatId;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
//One of the Many-to-many relationship solutions - Embedded key
//This solution is used because topic could be only added to the chat, not removed.
public class TopicChat {

    @EmbeddedId
    private TopicChatId id;

    @ManyToOne
    @MapsId("topicId")
    private Topic topic;

    @ManyToOne
    @MapsId("chatId") //Says: "Com'on, set the same value to the id." In such case to id.chatId
    private Chat chat;

    private LocalDateTime addedAt;

    private LocalDateTime doneAt; //shows when topic was closed for specific chat.

    @PrePersist
    public void prePersist() {
        addedAt = LocalDateTime.now();
    }
}
