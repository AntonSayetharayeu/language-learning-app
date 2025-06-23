package learningapp.model.id;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TopicChatId implements Serializable {

    private Long topicId;

    private Long chatId;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TopicChatId that)) return false;
        return Objects.equals(chatId, that.chatId) && Objects.equals(topicId, that.topicId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(chatId);
        result = 31 * result + Objects.hashCode(topicId);
        return result;
    }
}
