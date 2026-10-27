package learningapp.mapper;

import learningapp.dto.response.RatingResponseDTO;
import learningapp.model.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RatingMapper {

    @Mapping(source = "targetUser.id", target = "userId")
    @Mapping(source = "reporter.id", target = "reporterId")
    RatingResponseDTO toRatingDTO(Rating rating);
}
