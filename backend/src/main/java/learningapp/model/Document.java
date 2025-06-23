package learningapp.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@AttributeOverride(name = "createdAt", column = @Column(name = "added_at")) //Use createdAt as addedAt in DB.
public class Document extends ModelClass {

    private String path;

    private String fileName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    public Document(Long id) {
        super(id);
    }
}
