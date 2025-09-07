package learningapp.dto.responce;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentResponseDTO {

    private Long documentId;

    private String documentPath;

    private String documentName;

    private Long documentOwnerId;
}
