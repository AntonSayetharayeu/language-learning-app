package learningapp.mapper;

import learningapp.dto.response.MessageResponseDTO;
import learningapp.model.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(source = "chat.id", target = "chatId")
    @Mapping(source = "sender.id", target = "senderId")
    @Mapping(target = "time", expression = "java(toEpochMilli(message.getCreatedAt()))")
    MessageResponseDTO toMessageDTO(Message message);

    default Long toEpochMilli(Instant time) {
        if (time == null) return null;
        return time.toEpochMilli();
    }
}
