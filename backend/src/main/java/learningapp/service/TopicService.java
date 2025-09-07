package learningapp.service;

import learningapp.model.Topic;
import learningapp.repository.TopicRepository;
import learningapp.service.interfaces.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TopicService implements ITopicService {

    private final TopicRepository topicRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository){
        this.topicRepository = topicRepository;
    }

    @Override
    public List<Topic> findAllTopics() {
        return topicRepository.findAll();
    }

    @Override
    public Optional<Topic> findTopicById(Long id) {
        return topicRepository.findById(id);
    }

    @Override
    public Topic createTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    @Override
    public Topic updateTopic(Long id, Topic topic) {
        Topic existingTopic = topicRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found"));

        existingTopic.setTitle(topic.getTitle());
        existingTopic.setDescription(topic.getDescription());
        existingTopic.setStatus(topic.getStatus());

        return topicRepository.save(existingTopic);
    }

    @Override
    public Optional<Topic> deleteTopicById(Long id) {
        return topicRepository.findById(id)
                .map(topic -> {
                    topicRepository.delete(topic);
                    return topic;
                });
    }
}
