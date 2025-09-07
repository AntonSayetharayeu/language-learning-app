package learningapp.mapper;

import learningapp.dto.request.CourseRequestDTO;
import learningapp.dto.responce.CourseResponseDTO;
import learningapp.model.Course;
import learningapp.model.Language;
import learningapp.model.User;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE)
public interface CourseMapper {

    @Mapping(source = "courseNativeLanguageId", target = "nativeLanguage", qualifiedByName = "mapCourseNativeLanguageIdToLanguage")
    @Mapping(source = "courseLanguageIds", target = "foreignLanguages", qualifiedByName = "mapCourseLanguageIdsToLanguages")
    @Mapping(source = "courseCreatorId", target = "creator", qualifiedByName = "mapCourseCreatorIdToUser")
    Course toEntity(CourseRequestDTO dto);

    @Mapping(source = "id", target = "courseId")
    @Mapping(source = "nativeLanguage.id", target = "courseNativeLanguageId")
    @Mapping(source = "foreignLanguages", target = "courseLanguageIds", qualifiedByName = "mapLanguagesToCourseLanguageIds")
    @Mapping(source = "creator.id", target = "courseCreatorId")
    CourseResponseDTO toResponseDTO(Course course);

    @Named("mapCourseNativeLanguageIdToLanguage")
    default Language mapCourseNativeLanguageIdToLanguage(Long id) {
        return id == null ? null : new Language(id);
    }

    @Named("mapCourseLanguageIdsToLanguages")
    default List<Language> mapCourseLanguageIdsToLanguages(List<Long> ids) {
        return ids.stream().map(Language::new).collect(Collectors.toList());
    }

    @Named("mapLanguagesToCourseLanguageIds")
    default List<Long> mapLanguagesToCourseLanguageIds(List<Language> languages) {
        return languages.stream().map(Language::getId).collect(Collectors.toList()); //.toList() gives You java.util.ImmutableCollections.ListN so such list cannot be modified
    }

    @Named("mapCourseCreatorIdToUser")
    default User mapCourseCreatorIdToUser(Long id) {
        return id == null ? null : new User(id);
    }
}
