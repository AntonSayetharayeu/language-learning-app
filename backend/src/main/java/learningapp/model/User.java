package learningapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import learningapp.model.base.ModelClass;
import learningapp.model.enums.UserRole;
import learningapp.model.jointable.ChatUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "\"app_user\"")
public class User extends ModelClass {

    private String username;

    @Email
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "user")
    private List<ChatUser> chatUserList;

    public User(Long id) {
        super(id);
    }

    @Override
    public String toString() {
        return String.format("%s Email: %s", username, email);
    }
}
