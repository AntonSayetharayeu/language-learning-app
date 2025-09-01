package learningapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import learningapp.dto.request.LoginDTO;
import learningapp.dto.request.RegisterDTO;
import learningapp.dto.request.UserUpdateDTO;
import learningapp.dto.responce.LoggedUserDTO;
import learningapp.dto.responce.UserDTO;
import learningapp.mapper.UserMapper;
import learningapp.model.User;
import learningapp.repository.UserRepository;
import learningapp.service.interfaces.IUserService;

import java.util.List;
import java.util.Optional;

@Primary
@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseDTO)
                .toList();
    }

    public Optional<UserDTO> findUserById(Long id) {
        return userRepository.findById(id).map(userMapper::toResponseDTO);
    }

    public UserDTO createUser(RegisterDTO dto){
        User user = userMapper.toEntity(dto);
        String hashedPassword = passwordEncoder.encode(dto.getUserPassword());
        user.setPassword(hashedPassword);

        return userMapper.toResponseDTO(userRepository.save(user));
    }

    public Optional<LoggedUserDTO> authenticateUser(LoginDTO dto) {
        User user = userRepository.findByEmail(dto.getUserEmail())
                .orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if(!passwordEncoder.matches(dto.getUserPassword(), user.getPassword())) {
            return Optional.empty();
        }

        return Optional.of(userMapper.toLoggedResponseDTO(user));
    }

    public UserDTO updateUser(Long id, UserUpdateDTO dto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        userMapper.updateUserFromDto(dto, existingUser);
        User updatedUser = userRepository.save(existingUser);

        return userMapper.toResponseDTO(updatedUser);
    }

    public boolean deleteUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return true;
                })
                .orElse(false);
    }
}
