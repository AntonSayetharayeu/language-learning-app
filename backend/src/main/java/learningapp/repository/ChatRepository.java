package learningapp.repository;

import learningapp.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query("SELECT c FROM Chat c WHERE (c.lecturer.id = :userId OR c.student.id = :userId) AND c.isActive = :isActive")
    List<Chat> findByUserIdAndIsActive(@Param("userId") Long userId, @Param("isActive") boolean isActive);
}
