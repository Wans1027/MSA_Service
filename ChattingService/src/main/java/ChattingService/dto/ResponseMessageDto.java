package ChattingService.dto;

import ChattingService.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessageDto {

    Message.MessageType type;
    Long roomId;
    String detailMessage;
    Long senderId;
    String senderName;
    LocalDateTime sendTime;
}
