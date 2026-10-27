package learningapp.mapper;

import learningapp.dto.common.LanguageDTO;
import learningapp.dto.request.LanguageRequestDTO;
import learningapp.dto.request.TopicRequestDTO;
import learningapp.model.Language;
import learningapp.model.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LanguageMapper {

    LanguageDTO toLanguageDTO(Language language);

    void updateLanguageFromDto(LanguageRequestDTO dto, @MappingTarget Language language);
}
