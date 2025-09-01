package learningapp.mapper;

import learningapp.dto.request.TopicRequestDTO;
import learningapp.dto.responce.TopicResponseDTO;
import learningapp.model.Topic;
import learningapp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface TopicMapper {

    @Mapping(source = "topicTitle", target = "title")
    @Mapping(source = "topicDescription", target = "description")
    @Mapping(source = "topicStatus", target = "status")
    @Mapping(source = "authorId", target = "author", qualifiedByName = "mapAuthorIdToUser")
    Topic toEntity(TopicRequestDTO dto);

    @Mapping(source = "id", target = "topicId")
    @Mapping(source = "title", target = "topicTitle")
    @Mapping(source = "description", target = "topicDescription")
    @Mapping(source = "status", target = "topicStatus")
    @Mapping(source = "author.id", target = "authorId")
    TopicResponseDTO toResponseDTO(Topic topic);

/*
    @Mapping(source = "topicTitle", target = "title")
    @Mapping(source = "topicDescription", target = "description")
    @Mapping(source = "topicStatus", target = "status")
    @Mapping(source = "authorId", target = "author", qualifiedByName = "mapAuthorIdToUser")
    void updateTopicFromDto(TopicRequestDTO dto, @MappingTarget Topic topic);
*/

    @Named("mapAuthorIdToUser")
    default User mapAuthorIdToUser(Long authorId) {
        if (authorId == null) {
            return null;
        }
        return new User(authorId);
    }
}
