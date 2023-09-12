package ChattingService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChattingRoomDto {
    Long id;
    Integer participantsCount;
    String roomType;
}
