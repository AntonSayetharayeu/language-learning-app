package learningapp.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import learningapp.dto.request.RatingRequestDTO;
import learningapp.dto.response.RatingResponseDTO;
import learningapp.mapper.RatingMapper;
import learningapp.model.Chat;
import learningapp.model.Rating;
import learningapp.model.User;
import learningapp.repository.RatingRepository;
import learningapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final RatingMapper ratingMapper;

    @Transactional
    public List<RatingResponseDTO> getAllRatings() {
        return ratingRepository.findAll().stream()
                .map(ratingMapper::toRatingDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public RatingResponseDTO getRatingsById(Long id) {
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rating not found"));
        return  ratingMapper.toRatingDTO(rating);
    }

    @Transactional
    public RatingResponseDTO addOrUpdateRating(RatingRequestDTO dto) {
        User targetUser = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Target user not found"));
        User reporter = userRepository.findById(dto.getReporterId())
                .orElseThrow(() -> new EntityNotFoundException("Reporter not found"));

        Rating rating = ratingRepository.findByReporterIdAndTargetUserId(dto.getReporterId(), dto.getUserId())
                .orElseGet(() -> Rating.builder()
                        .targetUser(targetUser)
                        .reporter(reporter)
                        .build());

        rating.setValue(dto.getValue());
        rating.setDescription(dto.getDescription());

        return ratingMapper.toRatingDTO(ratingRepository.save(rating));
    }

    @Transactional
    public List<RatingResponseDTO> getRatingsForUser(Long targetUserId) {
        return ratingRepository.findByTargetUserId(targetUserId).stream()
                .map(ratingMapper::toRatingDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<RatingResponseDTO> getRatingsForReporter(Long reporterUserId) {
        return ratingRepository.findByReporterId(reporterUserId).stream()
                .map(ratingMapper::toRatingDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public RatingResponseDTO getRatingsByReporterAndPartnerId(Long reporterUserId, Long targetUserId) {
        return ratingRepository.findByReporterIdAndTargetUserId(reporterUserId, targetUserId)
                .stream().map(ratingMapper::toRatingDTO)
                .findFirst().orElseThrow(() -> new EntityNotFoundException("Rating not found"));
    }

    @Transactional
    public Double getAverageRatingForUser(Long targetUserId) {
        List<Rating> ratings = ratingRepository.findByTargetUserId(targetUserId);
        if (ratings.isEmpty()) {
            return 0.0;
        }

        double sum = ratings.stream().mapToDouble(Rating::getValue).sum();
        return Math.round((sum / ratings.size()) * 10.0) / 10.0;
    }
}
