package learningapp.model;

import jakarta.persistence.*;
import learningapp.model.base.ModelClass;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "topics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Topic extends ModelClass {

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private String category;
}
