package learningapp.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Entity
public class Course extends ModelClass {

    @ManyToOne
    private Language nativeLanguage;

    @ManyToMany
    @JoinTable(
            name = "course_language",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id"))
    private List<Language> foreignLanguage;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    public Course(Long id) {
        super(id);
    }
}
