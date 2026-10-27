package learningapp.controller;

import jakarta.validation.Valid;
import learningapp.dto.request.SubmissionRequestDTO;
import learningapp.dto.response.OfferResponseDTO;
import learningapp.dto.response.RequestResponseDTO;
import learningapp.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
public class SubmissionController { //Offer and Request

    private final SubmissionService submissionService;

    @GetMapping("/offers")
    public ResponseEntity<List<OfferResponseDTO>> getAllOffers() {
        return ResponseEntity.ok(submissionService.getAllOffers());
    }

    @GetMapping("/requests")
    public ResponseEntity<List<OfferResponseDTO>> getAllRequests() {
        return ResponseEntity.ok(submissionService.getAllRequests());
    }

    @PostMapping
    public ResponseEntity<Object> createSubmission(@Valid @RequestBody SubmissionRequestDTO dto) {
        return ResponseEntity.ok(submissionService.createSubmission(dto));
    }

    @GetMapping("/offers/lecturer/{lecturerId}")
    public ResponseEntity<List<OfferResponseDTO>> getOffersByLecturer(@PathVariable Long lecturerId) {
        return ResponseEntity.ok(submissionService.getOffersByLecturer(lecturerId));
    }

    @GetMapping("/requests/student/{studentId}")
    public ResponseEntity<List<RequestResponseDTO>> getRequestsByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(submissionService.getRequestsByStudent(studentId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubmission(@PathVariable Long id) {
        submissionService.deleteSubmission(id);
        return ResponseEntity.noContent().build();
    }
}