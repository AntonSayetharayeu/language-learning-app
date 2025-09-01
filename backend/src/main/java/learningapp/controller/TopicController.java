package learningapp.controller;

import jakarta.validation.Valid;
import learningapp.dto.request.TopicRequestDTO;
import learningapp.dto.responce.TopicResponseDTO;
import learningapp.mapper.TopicMapper;
import learningapp.service.interfaces.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/topic")
public class TopicController {

    private final ITopicService topicService;
    private final TopicMapper topicMapper;

    @Autowired
    public TopicController(ITopicService topicService, TopicMapper topicMapper) {
        this.topicService = topicService;
        this.topicMapper = topicMapper;
    }

    @GetMapping
    public ResponseEntity<List<TopicResponseDTO>> getAllTopics() {
        return ResponseEntity.ok(topicService.findAllTopics()
                .stream()
                .map(topicMapper::toResponseDTO)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicResponseDTO> getTopicByID(@PathVariable Long id) {
        return topicService.findTopicById(id)
                .map(topic -> ResponseEntity.ok(topicMapper.toResponseDTO(topic)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TopicResponseDTO> addNewTopic(@Valid @RequestBody TopicRequestDTO dto) {
        return ResponseEntity.ok(
                topicMapper.toResponseDTO(
                        topicService.createTopic(topicMapper.toEntity(dto))));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TopicResponseDTO> updateTopic(@PathVariable Long id, @Valid @RequestBody TopicRequestDTO dto){
        return ResponseEntity.ok(
                topicMapper.toResponseDTO(
                        topicService.updateTopic(id, topicMapper.toEntity(dto))));
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<TopicResponseDTO> removeTopic(@PathVariable Long id) {
        return topicService.deleteTopicById(id)
                .map(topic -> ResponseEntity.ok(topicMapper.toResponseDTO(topic)))
                .orElse(ResponseEntity.notFound().build());
    }
}
