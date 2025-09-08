package learningapp.model;

import jakarta.persistence.*;
import learningapp.model.base.ModelClass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Course extends ModelClass {
    /***
     * TODO: think about Course - Chat connectivity. So should be any dependency between them? - I think, yes.
     * Maybe there should be additional step like some info with (e.g.how much time can You spend daily? are there days you cannot chat? etc.)
     */

    @ManyToOne
    private Language nativeLanguage;

    @ManyToMany
    @JoinTable(
            name = "course_language",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id"))
    private List<Language> foreignLanguages;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    public Course(Long id) {
        super(id);
    }
}
