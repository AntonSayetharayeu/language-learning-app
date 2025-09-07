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
