package learningapp.mapper;

import learningapp.dto.request.DocumentRequestDTO;
import learningapp.dto.responce.DocumentResponseDTO;
import learningapp.model.Document;
import learningapp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    @Mapping(source = "documentPath", target = "path")
    @Mapping(source = "documentName", target = "fileName")
    @Mapping(source = "documentOwnerId", target = "owner", qualifiedByName = "mapDocumentOwnerIdToUser")
    Document toEntity(DocumentRequestDTO dto);

    @Mapping(source = "id", target = "documentId")
    @Mapping(source = "path", target = "documentPath")
    @Mapping(source = "fileName", target = "documentName")
    @Mapping(source = "owner.id", target = "documentOwnerId")
    DocumentResponseDTO toResponseDTO(Document document);

    @Named("mapDocumentOwnerIdToUser")
    default User mapDocumentOwnerIdToUser(Long id) {
        return id == null ? null : new User(id);
    }
}
