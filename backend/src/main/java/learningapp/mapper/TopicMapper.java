package learningapp.mapper;

import learningapp.dto.request.TopicRequestDTO;
import learningapp.dto.response.TopicResponseDTO;
import learningapp.model.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TopicMapper {

    TopicResponseDTO toTopicDTO(Topic topic);

    void updateTopicFromDto(TopicRequestDTO dto, @MappingTarget Topic topic);
}
