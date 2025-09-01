package learningapp.service.interfaces;

import learningapp.model.Language;

import java.util.List;
import java.util.Optional;

public interface ILanguageService {

    List<Language> findAllLanguages();

    Optional<Language> findLanguageById(Long id);

    Language createLanguage(Language language);

    Language updateLanguage(Long id, Language language);

    Optional<Language> deleteLanguageById(Long id);
}
