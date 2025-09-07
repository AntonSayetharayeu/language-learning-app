package learningapp.service;

import learningapp.model.Document;
import learningapp.repository.DocumentRepository;
import learningapp.service.interfaces.IDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentService implements IDocumentService {

    private final DocumentRepository repository;

    @Autowired
    public DocumentService(DocumentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Document> findAllDocuments() {
        return repository.findAll();
    }

    @Override
    public Optional<Document> findDocumentById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Document createDocument(Document document) {
        return repository.save(document);
    }

    @Override
    public Document updateDocument(Long id, Document document) {
        Document existingDocument = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found"));

        existingDocument.setPath(document.getPath());
        existingDocument.setFileName(document.getFileName());

        return repository.save(existingDocument);
    }

    @Override
    public Optional<Document> deleteDocumentById(Long id) {
        return repository.findById(id)
                .map(document -> {
                    repository.delete(document);
                    return document;
                });
    }
}
