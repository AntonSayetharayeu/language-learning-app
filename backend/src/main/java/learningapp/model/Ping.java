package learningapp.model;

import jakarta.persistence.*;
import learningapp.model.base.ModelClass;
import learningapp.model.enums.PingStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "pings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Ping extends ModelClass {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submission_id", nullable = false)
    private Submission basedOn; //BasedOnType would be from basedOn.type

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PingStatus status;

}
