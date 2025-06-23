package learningapp.model;

import jakarta.persistence.*;
import learningapp.model.enums.ChatRole;
import learningapp.model.id.ChatUserId;

import java.time.LocalDateTime;

@Entity
public class ChatUser {

    @EmbeddedId
    private ChatUserId id;

    private LocalDateTime enteredAt;

    private LocalDateTime leftAt;

    @Enumerated(EnumType.STRING)
    private ChatRole chatRole;

    @ManyToOne
    @MapsId("chatId") //Says: "Com'on, set the same value to the id." In such case to id.chatId
    private Chat chat;

    @ManyToOne
    @MapsId("userId")
    /*@JoinColumn(name = "user_id")*/
    private User user;

    @PrePersist
    public void prePersist() {
        enteredAt = LocalDateTime.now();
        leftAt = null;
    }
}
