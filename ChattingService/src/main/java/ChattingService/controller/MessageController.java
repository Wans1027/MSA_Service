package ChattingService.controller;

import ChattingService.dto.MessageDto;
import ChattingService.dto.Result;
import ChattingService.entity.Message;
import ChattingService.kafka.constants.KafkaConstants;
import ChattingService.service.MessageService;
import ChattingService.websocket.KafkaMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@RestController
@Slf4j
public class MessageController {
    private final KafkaMessageService kafkaMessageService;
    private final MessageService messageService;


    @MessageMapping("/message")
    public void sendMessage(MessageDto message){
        log.info("성공");
        kafkaMessageService.send(KafkaConstants.KAFKA_TOPIC, message);

    }

    @GetMapping("/list/{roomId}")
    public Result<Message> getMessages(@PathVariable Long roomId){
        List<Message> messages = messageService.getEventList(roomId);
        return new Result<>(messages, messages.size());
    }
}
