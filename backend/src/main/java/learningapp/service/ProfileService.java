package learningapp.service;

import learningapp.model.Profile;
import learningapp.repository.ProfileRepository;
import learningapp.service.interfaces.IProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService implements IProfileService {

    private final ProfileRepository repository;

    @Autowired
    public ProfileService(ProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Profile> findAllProfiles() {
        return repository.findAll();
    }

    @Override
    public Optional<Profile> findProfileById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Profile createProfile(Profile profile) {
        return repository.save(profile);
    }

    @Override
    public Profile updateProfile(Long id, Profile profile) {
        Profile existingProfile = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found"));

        existingProfile.setImageUrl(profile.getImageUrl());
        existingProfile.setBio(profile.getBio());
        existingProfile.setGender(profile.getGender());

        return repository.save(existingProfile);
    }

    @Override
    public Optional<Profile> deleteProfileById(Long id) {
        return repository.findById(id)
                .map(profile -> {
                        repository.delete(profile);
                        return profile;
                });
    }
}
