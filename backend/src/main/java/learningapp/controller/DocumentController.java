package learningapp.controller;

import jakarta.validation.Valid;
import learningapp.dto.request.DocumentRequestDTO;
import learningapp.dto.responce.DocumentResponseDTO;
import learningapp.mapper.DocumentMapper;
import learningapp.service.interfaces.IDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/document")
public class DocumentController {

    private final IDocumentService documentService;

    private final DocumentMapper documentMapper;

    @Autowired
    public DocumentController(IDocumentService documentService, DocumentMapper documentMapper) {
        this.documentService = documentService;
        this.documentMapper = documentMapper;
    }

    @GetMapping
    public ResponseEntity<List<DocumentResponseDTO>> getAllDocuments() {
        return ResponseEntity.ok(documentService.findAllDocuments()
                .stream()
                .map(documentMapper::toResponseDTO)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentResponseDTO> getDocumentByID(@PathVariable Long id) {
        return documentService.findDocumentById(id)
                .map(document -> ResponseEntity.ok(documentMapper.toResponseDTO(document)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DocumentResponseDTO> addNewDocument(@Valid @RequestBody DocumentRequestDTO dto) {
        return ResponseEntity.ok(
                documentMapper.toResponseDTO(
                        documentService.createDocument(documentMapper.toEntity(dto))));

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DocumentResponseDTO> updateDocument(@PathVariable Long id, @Valid @RequestBody DocumentRequestDTO dto) {
        return ResponseEntity.ok(
                documentMapper.toResponseDTO(
                        documentService.updateDocument(id, documentMapper.toEntity(dto))));
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<DocumentResponseDTO> removeDocument(@PathVariable Long id) {
        return documentService.deleteDocumentById(id)
                .map(document -> ResponseEntity.ok(documentMapper.toResponseDTO(document)))
                .orElse(ResponseEntity.notFound().build());
    }
}
