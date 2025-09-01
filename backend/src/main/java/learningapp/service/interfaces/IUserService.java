package learningapp.service.interfaces;

import learningapp.dto.request.LoginDTO;
import learningapp.dto.request.RegisterDTO;
import learningapp.dto.request.UserUpdateDTO;
import learningapp.dto.responce.LoggedUserDTO;
import learningapp.dto.responce.UserDTO;
import learningapp.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<UserDTO> findAllUsers();

    Optional<UserDTO> findUserById(Long id);

    UserDTO createUser(RegisterDTO dto);

    Optional<LoggedUserDTO> authenticateUser(LoginDTO dto);

    UserDTO updateUser(Long id, UserUpdateDTO dto);

    boolean deleteUserById(Long id);
}
