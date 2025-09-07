package learningapp.controller;

import jakarta.validation.Valid;
import learningapp.dto.request.ProfileRequestDTO;
import learningapp.dto.responce.ProfileResponseDTO;
import learningapp.mapper.ProfileMapper;
import learningapp.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    private final ProfileMapper profileMapper;

    @Autowired
    public ProfileController(ProfileService profileService, ProfileMapper profileMapper) {
        this.profileService = profileService;
        this.profileMapper = profileMapper;
    }

    @GetMapping
    public ResponseEntity<List<ProfileResponseDTO>> getAllProfiles() {
        return ResponseEntity.ok(profileService.findAllProfiles()
                .stream()
                .map(profileMapper::toResponseDTO)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponseDTO> getProfileByID(@PathVariable Long id) {
        return profileService.findProfileById(id)
                .map(profile -> ResponseEntity.ok(profileMapper.toResponseDTO(profile)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProfileResponseDTO> addNewProfile(@Valid @RequestBody ProfileRequestDTO dto) {
        return ResponseEntity.ok(
                profileMapper.toResponseDTO(
                        profileService.createProfile(profileMapper.toEntity(dto))));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProfileResponseDTO> updateProfile(@PathVariable Long id, @Valid @RequestBody ProfileRequestDTO dto){
        return ResponseEntity.ok(
            profileMapper.toResponseDTO(
                profileService.updateProfile(id, profileMapper.toEntity(dto))));
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<ProfileResponseDTO> removeProfile(@PathVariable Long id) {
        return profileService.deleteProfileById(id)
                .map(profile -> ResponseEntity.ok(profileMapper.toResponseDTO(profile)))
                .orElse(ResponseEntity.notFound().build());
    }
}
