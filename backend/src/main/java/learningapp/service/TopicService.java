package learningapp.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import learningapp.dto.request.TopicRequestDTO;
import learningapp.dto.response.TopicResponseDTO;
import learningapp.mapper.TopicMapper;
import learningapp.model.Topic;
import learningapp.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;

    @Transactional
    public List<TopicResponseDTO> getAllTopics() {
        return topicRepository.findAll().stream()
                .map(topicMapper::toTopicDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public TopicResponseDTO addTopic(TopicRequestDTO dto) {
        Topic topic = Topic.builder()
                .text(dto.getText())
                .category(dto.getCategory())
                .build();

        return topicMapper.toTopicDTO(topicRepository.save(topic));
    }

    @Transactional
    public List<TopicResponseDTO> getTopicsByCategory(String categoryLabel) {
        return topicRepository.findByCategory(categoryLabel).stream()
                .map(topicMapper::toTopicDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public TopicResponseDTO updateTopic(Long id, TopicRequestDTO dto) {
        Topic existingTopic = topicRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found"));

        topicMapper.updateTopicFromDto(dto, existingTopic);
        return topicMapper.toTopicDTO(topicRepository.save(existingTopic));
    }

    @Transactional
    public void deleteTopic(Long id) {
        if (!topicRepository.existsById(id)) {
            throw new EntityNotFoundException("Topic not found");
        }
        topicRepository.deleteById(id);
    }
}