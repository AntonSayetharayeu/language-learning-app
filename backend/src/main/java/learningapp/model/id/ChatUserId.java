package learningapp.model.id;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ChatUserId implements Serializable {

    private Long chatId;

    /*@Column(name = "user_id")*/
    private Long userId;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatUserId that)) return false;
        return Objects.equals(chatId, that.chatId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(chatId);
        result = 31 * result + Objects.hashCode(userId);
        return result;
    }
}
