package learningapp.mapper;

import learningapp.dto.request.LanguageRequestDTO;
import learningapp.dto.responce.LanguageResponseDTO;
import learningapp.model.Language;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LanguageMapper {

    @Mapping(source = "languageName", target = "name")
    @Mapping(source = "languageCode", target = "code")
    Language toEntity(LanguageRequestDTO dto);

    @Mapping(source = "id", target = "languageId")
    @Mapping(source = "name", target = "languageName")
    @Mapping(source = "code", target = "languageCode")
    LanguageResponseDTO toResponseDTO(Language language);
}
