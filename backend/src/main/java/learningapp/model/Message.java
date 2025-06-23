package learningapp.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@AttributeOverride(name = "createdAt", column = @Column(name = "sent_at")) //Use createdAt as sentAt in DB.
public class Message extends ModelClass {

    private String content;

    private LocalDateTime readAt;

    @ManyToOne
    private Chat chat;

    @ManyToOne
    private User sender;

    public Message(Long id) {
        super(id);
    }
}
