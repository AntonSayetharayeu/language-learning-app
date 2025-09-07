package learningapp.model;

import jakarta.persistence.*;
import learningapp.model.base.ModelClass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AttributeOverride(name = "createdAt", column = @Column(name = "sent_at")) //Use createdAt as sentAt in DB.
public class Message extends ModelClass {
    //TODO: add new field Message answerTo - so there could be messages that are answers to specific one
    //TODO: add new field boolean wasModified (or LocalDateTime modifiedAt) - to show whether it was modified or even where
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
