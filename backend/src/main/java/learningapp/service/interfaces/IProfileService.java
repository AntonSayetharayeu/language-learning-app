package learningapp.service.interfaces;

import learningapp.model.Profile;

import java.util.List;
import java.util.Optional;

public interface IProfileService {

    List<Profile> findAllProfiles();

    Optional<Profile> findProfileById(Long id);

    Profile createProfile(Profile profile);

    Profile updateProfile(Long id, Profile profile);

    Optional<Profile> deleteProfileById(Long id);
}
