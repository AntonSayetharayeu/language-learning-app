package learningapp.model;

import jakarta.persistence.*;
import learningapp.model.base.ModelClass;
import learningapp.model.enums.ChatType;
import learningapp.model.jointable.ChatUser;
import learningapp.model.jointable.TopicChat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Chat extends ModelClass {

    private String name;

    private ChatType chatType;

    @OneToMany(mappedBy = "chat")
    private List<ChatUser> chatUsers;

    @OneToMany(mappedBy = "chat")
    private List<TopicChat> chatTopics;

    @OneToMany(mappedBy = "chat")
    private List<Message> chatMessages;

    public Chat(Long id) {
        super(id);
    }
}
