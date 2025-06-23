package learningapp.model;

import jakarta.persistence.*;
import learningapp.model.enums.ChatType;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Entity
public class Chat extends ModelClass {

    private String name;

    private ChatType chatType;

    @OneToMany(mappedBy = "chat")
    private List<ChatUser> chatUsers;

    @OneToMany(mappedBy = "chat")
    private List<TopicChat> topicChatList;

    public Chat(Long id) {
        super(id);
    }
}
