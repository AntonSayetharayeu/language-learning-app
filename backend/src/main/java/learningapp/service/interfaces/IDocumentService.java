package learningapp.service.interfaces;

import learningapp.model.Document;

import java.util.List;
import java.util.Optional;

public interface IDocumentService {

    List<Document> findAllDocuments();

    Optional<Document> findDocumentById(Long id);

    Document createDocument(Document document);

    Document updateDocument(Long id, Document document);

    Optional<Document> deleteDocumentById(Long id);
}
