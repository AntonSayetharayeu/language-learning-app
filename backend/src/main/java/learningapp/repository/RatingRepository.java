package learningapp.repository;

import learningapp.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByTargetUserId(Long targetUserId);
    List<Rating> findByReporterId(Long reporterId);
    Optional<Rating> findByReporterIdAndTargetUserId(Long reporterId, Long targetUserId);
}
