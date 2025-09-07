package learningapp.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import learningapp.model.base.ModelClass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AttributeOverride(name = "createdAt", column = @Column(name = "added_at")) //Use createdAt as addedAt in DB.
public class Language extends ModelClass {

    private String name;

    private String code;

    public Language(Long id) {
        super(id);
    }
}
