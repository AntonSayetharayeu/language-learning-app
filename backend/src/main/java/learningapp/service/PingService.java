package learningapp.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import learningapp.dto.request.PingRequestDTO;
import learningapp.dto.response.PingResponseDTO;
import learningapp.mapper.PingMapper;
import learningapp.model.Ping;
import learningapp.model.Submission;
import learningapp.model.User;
import learningapp.model.enums.PingStatus;
import learningapp.repository.PingRepository;
import learningapp.repository.SubmissionRepository;
import learningapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PingService {

    private final PingRepository pingRepository;
    private final UserRepository userRepository;
    private final SubmissionRepository submissionRepository;
    private final PingMapper pingMapper;

    @Transactional
    public PingResponseDTO createPing(PingRequestDTO dto) {
        if (pingRepository.existsBySenderIdAndBasedOnId(dto.getSenderId(), dto.getBasedOnId())) {
            throw new IllegalStateException("Ping already exists for this submission by this user.");
        }

        User sender = userRepository.findById(dto.getSenderId())
                .orElseThrow(() -> new EntityNotFoundException("Sender not found"));
        User receiver = userRepository.findById(dto.getReceiverId())
                .orElseThrow(() -> new EntityNotFoundException("Receiver not found"));
        Submission basedOn = submissionRepository.findById(dto.getBasedOnId())
                .orElseThrow(() -> new EntityNotFoundException("Submission not found"));

        Ping ping = Ping.builder()
                .sender(sender)
                .receiver(receiver)
                .basedOn(basedOn)
                .status(PingStatus.PENDING)
                .build();

        return pingMapper.toPingDTO(pingRepository.save(ping));
    }

    @Transactional
    public PingResponseDTO updatePingStatus(Long pingId, String newStatusStr) {
        Ping ping = pingRepository.findById(pingId)
                .orElseThrow(() -> new EntityNotFoundException("Ping not found"));

        PingStatus newStatus = PingStatus.valueOf(newStatusStr.toUpperCase());
        ping.setStatus(newStatus);

        if (newStatus == PingStatus.ACCEPTED) {
            declineCompetingPings(pingId, ping.getBasedOn().getId());
        }

        return pingMapper.toPingDTO(pingRepository.save(ping));
    }

    private void declineCompetingPings(Long acceptedPingId, Long basedOnId) {
        List<Ping> competingPings = pingRepository.findByBasedOnIdAndStatus(basedOnId, PingStatus.PENDING);

        competingPings.stream()
                .filter(p -> !p.getId().equals(acceptedPingId))
                .forEach(p -> p.setStatus(PingStatus.DECLINED));

        pingRepository.saveAll(competingPings);
    }

    @Transactional
    public List<PingResponseDTO> getSentPings(Long userId) {
        return pingRepository.findBySenderId(userId).stream()
                .map(pingMapper::toPingDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PingResponseDTO> getReceivedPings(Long userId) {
        return pingRepository.findByReceiverId(userId).stream()
                .map(pingMapper::toPingDTO)
                .collect(Collectors.toList());
    }
}
