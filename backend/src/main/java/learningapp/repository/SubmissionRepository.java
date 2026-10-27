package learningapp.repository;

import learningapp.model.Submission;
import learningapp.model.enums.SubmissionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByTypeAndInitiatorId(SubmissionType type, Long initiatorId);

    List<Submission> findByType(SubmissionType type);
}
