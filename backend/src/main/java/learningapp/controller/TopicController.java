package learningapp.controller;

import jakarta.validation.Valid;
import learningapp.dto.request.TopicRequestDTO;
import learningapp.dto.response.TopicResponseDTO;
import learningapp.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @GetMapping
    public ResponseEntity<List<TopicResponseDTO>> getAllTopics() {
        return ResponseEntity.ok(topicService.getAllTopics());
    }

    @GetMapping("/category/{categoryLabel}")
    public ResponseEntity<List<TopicResponseDTO>> getTopicsByCategory(@PathVariable String categoryLabel) {
        return ResponseEntity.ok(topicService.getTopicsByCategory(categoryLabel));
    }

    @PostMapping
    public ResponseEntity<TopicResponseDTO> addTopic(@Valid @RequestBody TopicRequestDTO dto) {
        return ResponseEntity.ok(topicService.addTopic(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicResponseDTO> updateTopic(@PathVariable Long id, @Valid @RequestBody TopicRequestDTO dto) {
        return ResponseEntity.ok(topicService.updateTopic(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        topicService.deleteTopic(id);
        return ResponseEntity.noContent().build();
    }
}