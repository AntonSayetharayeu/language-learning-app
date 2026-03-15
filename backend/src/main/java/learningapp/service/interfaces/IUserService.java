package learningapp.service.interfaces;

import learningapp.dto.request.RegisterDTO;
import learningapp.dto.request.UserUpdateDTO;
import learningapp.dto.responce.UserDTO;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<UserDTO> findAllUsers();

    Optional<UserDTO> findUserById(Long id);

    UserDTO createUser(RegisterDTO dto);

    UserDTO updateUser(Long id, UserUpdateDTO dto);

    boolean deleteUserById(Long id);
}
