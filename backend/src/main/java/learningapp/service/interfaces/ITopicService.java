package learningapp.service.interfaces;

import learningapp.model.Topic;

import java.util.List;
import java.util.Optional;

public interface ITopicService {

    List<Topic> findAllTopics();

    Optional<Topic> findTopicById(Long id);

    Topic createTopic(Topic topic);

    Topic updateTopic(Long id, Topic topic);

    Optional<Topic> deleteTopicById(Long id);
}
