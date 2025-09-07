package learningapp.model;

import jakarta.persistence.*;
import learningapp.model.base.ModelClass;
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
