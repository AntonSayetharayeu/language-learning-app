package learningapp.controller;

import jakarta.validation.Valid;
import learningapp.dto.request.RatingRequestDTO;
import learningapp.dto.request.TopicRequestDTO;
import learningapp.dto.response.RatingResponseDTO;
import learningapp.dto.response.TopicResponseDTO;
import learningapp.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @GetMapping
    public ResponseEntity<List<RatingResponseDTO>> getAllRatings() {
        return ResponseEntity.ok(ratingService.getAllRatings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingResponseDTO> getRatingById(@PathVariable Long id) {
        return ResponseEntity.ok(ratingService.getRatingsById(id));
    }

    @PostMapping
    public ResponseEntity<RatingResponseDTO> addOrUpdateRating(@Valid @RequestBody RatingRequestDTO dto) {
        return ResponseEntity.ok(ratingService.addOrUpdateRating(dto));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RatingResponseDTO>> getRatingsForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(ratingService.getRatingsForUser(userId));
    }

    @GetMapping("/reporter/{reporterId}")
    public ResponseEntity<List<RatingResponseDTO>> getRatingsForReporter(@PathVariable Long reporterId) {
        return ResponseEntity.ok(ratingService.getRatingsForReporter(reporterId));
    }

    @GetMapping("/users/{reporterId}/{userId}")
    public ResponseEntity<RatingResponseDTO> getRatingsByReporterAndPartnerId(@PathVariable Long reporterId, @PathVariable Long userId) {
        return ResponseEntity.ok(ratingService.getRatingsByReporterAndPartnerId(reporterId, userId));
    }
}