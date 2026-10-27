package learningapp.controller;

import jakarta.validation.Valid;
import learningapp.dto.common.LanguageDTO;
import learningapp.dto.request.LanguageRequestDTO;
import learningapp.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/languages")
@RequiredArgsConstructor
public class LanguageController {

    private final LanguageService languageService;

    @GetMapping
    public ResponseEntity<List<LanguageDTO>> getAllLanguages() {
        return ResponseEntity.ok(languageService.getAllLanguages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LanguageDTO> getLanguageById(@PathVariable Long id) {
        return ResponseEntity.ok(languageService.getLanguageById(id));
    }

    @PostMapping
    public ResponseEntity<LanguageDTO> addLanguage(@Valid @RequestBody LanguageRequestDTO dto) {
        return ResponseEntity.ok(languageService.addLanguage(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LanguageDTO> updateLanguage(@PathVariable Long id, @Valid @RequestBody LanguageRequestDTO dto) {
        return ResponseEntity.ok(languageService.updateLanguage(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<LanguageDTO> deleteLanguage(@PathVariable Long id) {
        languageService.deleteLanguage(id);
        return ResponseEntity.noContent().build();
    }
}