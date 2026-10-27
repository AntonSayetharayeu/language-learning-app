package learningapp.mapper;

import learningapp.dto.response.ChatResponseDTO;
import learningapp.model.Chat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChatMapper {

    @Mapping(source = "lecturer.id", target = "lecturerId")
    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "nativeLanguage", target = "nativeLanguage")
    ChatResponseDTO toChatDTO(Chat chat);
}
