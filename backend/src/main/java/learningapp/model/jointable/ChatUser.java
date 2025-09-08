package learningapp.model.jointable;

import jakarta.persistence.*;
import learningapp.model.Chat;
import learningapp.model.User;
import learningapp.model.base.ModelClass;
import learningapp.model.enums.ChatRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AttributeOverride(name = "createdAt", column = @Column(name = "entered_at")) //shows when User entered Chat
//One of the Many-to-many relationship solutions - Surrogate key
public class ChatUser extends ModelClass {

    private LocalDateTime leftAt;

    @Enumerated(EnumType.STRING)
    private ChatRole chatRole;

    @ManyToOne
    private Chat chat;

    @ManyToOne
    private User user;

    public ChatUser(Long id) {
        super(id);
    }
}
