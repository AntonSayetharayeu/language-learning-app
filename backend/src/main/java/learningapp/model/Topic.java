package learningapp.model;

import jakarta.persistence.*;
import learningapp.model.base.ModelClass;
import learningapp.model.enums.TopicStatus;
import learningapp.model.jointable.TopicChat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Topic extends ModelClass {

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private TopicStatus status;

    @OneToMany(mappedBy = "topic")
    private List<TopicChat> topicChatList;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    public Topic(Long id) {
        super(id);
    }
}
