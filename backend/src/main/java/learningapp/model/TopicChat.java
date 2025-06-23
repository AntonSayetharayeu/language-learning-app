package learningapp.model;

import jakarta.persistence.*;
import learningapp.model.id.TopicChatId;

import java.time.LocalDateTime;

@Entity
public class TopicChat {

    @EmbeddedId
    private TopicChatId id;

    @ManyToOne
    @MapsId("topicId")
    private Topic topic;

    @ManyToOne
    @MapsId("chatId")
    private Chat chat;

    private LocalDateTime addedAt;

    @PrePersist
    public void prePersist() {
        addedAt = LocalDateTime.now();
    }
}
