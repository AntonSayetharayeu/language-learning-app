package learningapp.service;

import learningapp.model.Language;
import learningapp.repository.LanguageRepository;
import learningapp.service.interfaces.ILanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class LanguageService implements ILanguageService {

    private LanguageRepository languageRepository;

    @Autowired
    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    public List<Language> findAllLanguages() {
        return languageRepository.findAll();
    }

    @Override
    public Optional<Language> findLanguageById(Long id) {
        return languageRepository.findById(id);
    }

    @Override
    public Language createLanguage(Language language) {
        return languageRepository.save(language);
    }

    @Override
    public Language updateLanguage(Long id, Language language) {
        Language existingLanguage = languageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Language not found"));

        existingLanguage.setName(language.getName());
        existingLanguage.setCode(language.getCode());

        return languageRepository.save(existingLanguage);
    }

    @Override
    public Optional<Language> deleteLanguageById(Long id) {
        return languageRepository.findById(id)
                .map(language -> {
                    languageRepository.delete(language);
                    return language;
                });
    }
}
