package learningapp.model;

import jakarta.persistence.*;
import learningapp.model.base.ModelClass;
import learningapp.model.enums.SubmissionType;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "submissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Submission extends ModelClass {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubmissionType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id", nullable = false)
    private User initiator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submitted_language_id", nullable = false)
    private Language submittedLanguage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "native_language_id", nullable = false)
    private Language nativeLanguage;

    @Column(columnDefinition = "TEXT")
    private String description;
}
