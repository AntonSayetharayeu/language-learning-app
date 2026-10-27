package learningapp.mapper;

import learningapp.dto.response.PingResponseDTO;
import learningapp.model.Ping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;

@Mapper(componentModel = "spring")
public interface PingMapper {

    @Mapping(source = "sender.id", target = "senderId")
    @Mapping(source = "receiver.id", target = "receiverId")
    @Mapping(source = "basedOn.id", target = "basedOnId")
    @Mapping(source = "basedOn.type", target = "basedOnType")
    @Mapping(target = "createdAt", expression = "java(toEpochMilli(ping.getCreatedAt()))")
    PingResponseDTO toPingDTO(Ping ping);

    default Long toEpochMilli(Instant time) {
        if (time == null) return null;
        return time.toEpochMilli();
    }
}
