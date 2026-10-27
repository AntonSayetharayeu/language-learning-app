package learningapp.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import learningapp.dto.request.UserRegistrationRequestDTO;
import learningapp.dto.response.UserResponseDTO;
import learningapp.mapper.UserMapper;
import learningapp.model.Profile;
import learningapp.model.User;
import learningapp.model.enums.Role;
import learningapp.repository.ProfileRepository;
import learningapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final RatingService ratingService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    private static final String DEFAULT_IMAGE = "/src/assets/user.png";

    @Transactional
    public UserResponseDTO register(UserRegistrationRequestDTO dto) {
        if (userRepository.existsByLogin(dto.getLogin())) {
            throw new IllegalArgumentException("Login is already taken");
        }

        String securedPassword = passwordEncoder.encode(dto.getPassword());

        User user = User.builder()
                .login(dto.getLogin())
                .password(securedPassword)
                .role(Role.valueOf(dto.getRole().toUpperCase()))
                .build();

        user = userRepository.save(user);

        Profile profile = Profile.builder()
                .user(user)
                .username(dto.getUsername())
                .imgUrl(DEFAULT_IMAGE)
                .build();

        profile = profileRepository.save(profile);

        return buildUserResponse(user, profile);
    }

    @Transactional
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Profile profile = profileRepository.findByUserId(id)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found"));

        return buildUserResponse(user, profile);
    }

    @Transactional
    public List<UserResponseDTO> getUsersByRole(String role) {
        Role enumRole = Role.valueOf(role.toUpperCase());
        return userRepository.findByRole(enumRole).stream()
                .map(user -> buildUserResponse(user, user.getProfile()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public String login(String login, String rawPassword) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException("Invalid credentials"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        return "SUCCESS_TOKEN"; //JWT token
    }

    private UserResponseDTO buildUserResponse(User user, Profile profile) {
        UserResponseDTO dto = userMapper.toUserDTO(user, profile);
        dto.setRating(ratingService.getAverageRatingForUser(user.getId()));
        return dto;
    }
}
