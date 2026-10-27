package learningapp.controller;

import jakarta.validation.Valid;
import learningapp.dto.request.PingRequestDTO;
import learningapp.dto.response.PingResponseDTO;
import learningapp.service.PingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pings")
@RequiredArgsConstructor
public class PingController {

    private final PingService pingService;

    @GetMapping("/sent/{userId}")
    public ResponseEntity<List<PingResponseDTO>> getSentPings(@PathVariable Long userId) {
        return ResponseEntity.ok(pingService.getSentPings(userId));
    }

    @GetMapping("/received/{userId}")
    public ResponseEntity<List<PingResponseDTO>> getReceivedPings(@PathVariable Long userId) {
        return ResponseEntity.ok(pingService.getReceivedPings(userId));
    }

    @PostMapping
    public ResponseEntity<PingResponseDTO> createPing(@Valid @RequestBody PingRequestDTO dto) {
        return ResponseEntity.ok(pingService.createPing(dto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PingResponseDTO> updatePingStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return ResponseEntity.ok(pingService.updatePingStatus(id, status));
    }
}