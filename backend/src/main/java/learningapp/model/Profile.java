package learningapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import learningapp.model.base.ModelClass;
import learningapp.model.enums.ProfileGender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Profile extends ModelClass {

    private String imageUrl;

    private String bio;

    @Enumerated(EnumType.STRING)
    private ProfileGender gender;

    @OneToOne
    private User user;

    public Profile(Long id) {
        super(id);
    }
}
