package learningapp.mapper;

import learningapp.dto.response.OfferResponseDTO;
import learningapp.dto.response.RequestResponseDTO;
import learningapp.model.Submission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubmissionMapper {

    @Mapping(source = "initiator.id", target = "lecturerId")
    @Mapping(source = "submittedLanguage", target = "teaching")
    @Mapping(source = "nativeLanguage", target = "nativeLanguage")
    OfferResponseDTO toOfferDTO(Submission submission);

    @Mapping(source = "initiator.id", target = "studentId")
    @Mapping(source = "submittedLanguage", target = "learning")
    @Mapping(source = "nativeLanguage", target = "nativeLanguage")
    RequestResponseDTO toRequestDTO(Submission submission);
}
