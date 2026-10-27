package learningapp.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import learningapp.dto.request.SubmissionRequestDTO;
import learningapp.dto.response.OfferResponseDTO;
import learningapp.dto.response.RequestResponseDTO;
import learningapp.mapper.SubmissionMapper;
import learningapp.model.Language;
import learningapp.model.Submission;
import learningapp.model.User;
import learningapp.model.enums.SubmissionType;
import learningapp.repository.LanguageRepository;
import learningapp.repository.SubmissionRepository;
import learningapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;
    private final LanguageRepository languageRepository;
    private final SubmissionMapper submissionMapper;

    @Transactional
    public Object createSubmission(SubmissionRequestDTO dto) {
        User initiator = userRepository.findById(dto.getInitiatorId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Language submittedLang = languageRepository.findById(dto.getSubmittedLanguageId())
                .orElseThrow(() -> new EntityNotFoundException("Language not found"));
        Language nativeLang = languageRepository.findById(dto.getNativeLanguageId())
                .orElseThrow(() -> new EntityNotFoundException("Language not found"));

        SubmissionType type = SubmissionType.valueOf(dto.getType().toUpperCase());

        Submission submission = Submission.builder()
                .initiator(initiator)
                .type(type)
                .submittedLanguage(submittedLang)
                .nativeLanguage(nativeLang)
                .description(dto.getDescription())
                .build();

        submission = submissionRepository.save(submission);

        return type == SubmissionType.OFFER ? submissionMapper.toOfferDTO(submission) : submissionMapper.toRequestDTO(submission);
    }

    @Transactional
    public List<OfferResponseDTO> getAllOffers() {
        return submissionRepository.findByType(SubmissionType.OFFER)
                .stream()
                .map(submissionMapper::toOfferDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<OfferResponseDTO> getAllRequests() {
        return submissionRepository.findByType(SubmissionType.REQUEST)
                .stream()
                .map(submissionMapper::toOfferDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<OfferResponseDTO> getOffersByLecturer(Long lecturerId) {
        return submissionRepository.findByTypeAndInitiatorId(SubmissionType.OFFER, lecturerId)
                .stream()
                .map(submissionMapper::toOfferDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<RequestResponseDTO> getRequestsByStudent(Long studentId) {
        return submissionRepository.findByTypeAndInitiatorId(SubmissionType.REQUEST, studentId)
                .stream()
                .map(submissionMapper::toRequestDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteSubmission(Long id) {
        submissionRepository.deleteById(id);
    }
}
