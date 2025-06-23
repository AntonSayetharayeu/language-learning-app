package learningapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import learningapp.dto.request.RegisterDTO;
import learningapp.dto.request.UserUpdateDTO;
import learningapp.dto.responce.LoggedUserDTO;
import learningapp.dto.responce.UserDTO;
import learningapp.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "userName", target = "username")
    @Mapping(source = "userEmail", target = "email")
    @Mapping(source = "userPassword", target = "password")
    User toEntity(RegisterDTO dto);

    @Mapping(source = "id", target = "userID")
    @Mapping(source = "username", target = "userName")
    @Mapping(source = "email", target = "userEmail")
    @Mapping(source = "createdAt", target = "userCreatedAt")
    UserDTO toResponseDTO(User user);

    @Mapping(source = "id", target = "userID")
    @Mapping(source = "username", target = "userName")
    LoggedUserDTO toLoggedResponseDTO(User user);

    @Mapping(source = "userName", target = "username")
    @Mapping(source = "userEmail", target = "email")
    @Mapping(source = "userPassword", target = "password")
    void updateUserFromDto(UserUpdateDTO dto, @MappingTarget User user);
}
