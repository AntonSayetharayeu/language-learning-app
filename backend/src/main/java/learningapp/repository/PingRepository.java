package learningapp.repository;

import learningapp.model.Ping;
import learningapp.model.enums.PingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PingRepository extends JpaRepository<Ping, Long> {
    List<Ping> findBySenderId(Long senderId);
    List<Ping> findByReceiverId(Long receiverId);
    boolean existsBySenderIdAndBasedOnId(Long senderId, Long basedOnId);
    List<Ping> findByBasedOnIdAndStatus(Long basedOnId, PingStatus status);
}
