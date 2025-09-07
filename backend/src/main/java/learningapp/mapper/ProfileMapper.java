package learningapp.mapper;

import learningapp.dto.request.ProfileRequestDTO;
import learningapp.dto.responce.ProfileResponseDTO;
import learningapp.model.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    @Mapping(source = "profileImageUrl", target = "imageUrl")
    @Mapping(source = "profileBio", target = "bio")
    @Mapping(source = "profileGender", target = "gender")
    Profile toEntity(ProfileRequestDTO dto);

    @Mapping(source = "id", target = "profileId")
    @Mapping(source = "imageUrl", target = "profileImageUrl")
    @Mapping(source = "bio", target = "profileBio")
    @Mapping(source = "gender", target = "profileGender")
    ProfileResponseDTO toResponseDTO(Profile profile);
}
