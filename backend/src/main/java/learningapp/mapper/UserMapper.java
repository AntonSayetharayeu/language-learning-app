package learningapp.mapper;

import learningapp.dto.response.UserResponseDTO;
import learningapp.model.Language;
import learningapp.model.Profile;
import learningapp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "user.id", target = "id")
    @Mapping(source = "user.login", target = "login")
    @Mapping(source = "user.role", target = "role")
    @Mapping(source = "profile.username", target = "username")
    @Mapping(source = "profile.imgUrl", target = "imgUrl")
    @Mapping(target = "knownLanguages", expression = "java(mapLanguagesToIds(profile.getKnownLanguages()))")
    @Mapping(target = "languagesToLearn", expression = "java(mapLanguagesToIds(profile.getLanguagesToLearn()))")
    @Mapping(target = "rating", ignore = true) //It requires calculation
    UserResponseDTO toUserDTO(User user, Profile profile);

    default Set<Long> mapLanguagesToIds(Set<Language> languages) {
        if (languages == null) return null;
        return languages.stream().map(Language::getId).collect(Collectors.toSet());
    }
}
