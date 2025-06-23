package learningapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import learningapp.model.enums.UserGender;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class Profile extends ModelClass {

    private String imageUrl;

    private String bio;

    @Enumerated(EnumType.STRING)
    private UserGender gender;

    public Profile(Long id) {
        super(id);
    }
}
