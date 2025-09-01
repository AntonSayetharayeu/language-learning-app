package learningapp.controller;

import jakarta.validation.Valid;
import learningapp.dto.request.LanguageRequestDTO;
import learningapp.dto.responce.LanguageResponseDTO;
import learningapp.mapper.LanguageMapper;
import learningapp.service.interfaces.ILanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/language")
public class LanguageController {

    private final ILanguageService languageService;
    private final LanguageMapper languageMapper;

    @Autowired
    public LanguageController(ILanguageService languageService, LanguageMapper languageMapper) {
        this.languageService = languageService;
        this.languageMapper = languageMapper;
    }

    @GetMapping
    public ResponseEntity<List<LanguageResponseDTO>> getAllLanguages() {
        return ResponseEntity.ok(languageService.findAllLanguages()
                .stream()
                .map(languageMapper::toResponseDTO)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LanguageResponseDTO> getLanguageByID(@PathVariable Long id) {
        return languageService.findLanguageById(id)
                .map(language -> ResponseEntity.ok(languageMapper.toResponseDTO(language)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LanguageResponseDTO> addNewLanguage(@Valid @RequestBody LanguageRequestDTO dto) {
        return ResponseEntity.ok(
                languageMapper.toResponseDTO(
                        languageService.createLanguage(languageMapper.toEntity(dto))));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<LanguageResponseDTO> updateLanguage(@PathVariable Long id, @Valid @RequestBody LanguageRequestDTO dto){
        return ResponseEntity.ok(
                languageMapper.toResponseDTO(
                        languageService.updateLanguage(id, languageMapper.toEntity(dto))));
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<LanguageResponseDTO> removeLanguage(@PathVariable Long id) {
        return languageService.deleteLanguageById(id)
                .map(language -> ResponseEntity.ok(languageMapper.toResponseDTO(language)))
                .orElse(ResponseEntity.notFound().build());
    }
}
