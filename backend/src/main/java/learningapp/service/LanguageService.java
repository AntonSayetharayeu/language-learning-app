package learningapp.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import learningapp.dto.common.LanguageDTO;
import learningapp.dto.request.LanguageRequestDTO;
import learningapp.mapper.LanguageMapper;
import learningapp.model.Language;
import learningapp.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LanguageService {

    private final LanguageRepository languageRepository;
    private final LanguageMapper languageMapper;

    @Transactional
    public List<LanguageDTO> getAllLanguages() {
        return languageRepository.findAll().stream()
                .map(languageMapper::toLanguageDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public LanguageDTO getLanguageById(Long id) {
        Language lang = languageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chat not found"));
        return  languageMapper.toLanguageDTO(lang);
    }

    @Transactional
    public LanguageDTO addLanguage(LanguageRequestDTO dto) {
        if (languageRepository.findByName(dto.getName()).isPresent()) {
            throw new IllegalArgumentException("Language already exists");
        }

        Language language = Language.builder()
                .name(dto.getName())
                .build();

        return languageMapper.toLanguageDTO(languageRepository.save(language));
    }

    @Transactional
    public LanguageDTO updateLanguage(Long id, LanguageRequestDTO dto) {
        Language language = languageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Language not found"));

        languageMapper.updateLanguageFromDto(dto, language);
        return languageMapper.toLanguageDTO(languageRepository.save(language));
    }

    @Transactional
    public void deleteLanguage(Long id) {
        if (!languageRepository.existsById(id)) {
            throw new EntityNotFoundException("Language not found");
        }
        languageRepository.deleteById(id);
    }
}
