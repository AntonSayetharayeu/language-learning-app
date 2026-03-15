package learningapp.service;

import learningapp.dto.request.RegisterDTO;
import learningapp.dto.request.UserUpdateDTO;
import learningapp.dto.responce.UserDTO;
import learningapp.mapper.UserMapper;
import learningapp.model.User;
import learningapp.repository.UserRepository;
import learningapp.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        if (userRepository.findByUsername(dto.getUserName()).isPresent()) {
            throw new RuntimeException("Username already taken!");
        }

        User user = userMapper.toEntity(dto);
        String hashedPassword = passwordEncoder.encode(dto.getUserPassword());
        user.setPassword(hashedPassword);

        return userMapper.toResponseDTO(userRepository.save(user));
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
