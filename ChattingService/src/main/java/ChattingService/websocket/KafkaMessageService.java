package ChattingService.websocket;

import ChattingService.dto.MessageDto;
import ChattingService.dto.ResponseMessageDto;
import ChattingService.exception.RestException;
import ChattingService.kafka.AlarmInfo;
import ChattingService.kafka.constants.KafkaAlarmConstants;
import ChattingService.kafka.constants.KafkaConstants;
import ChattingService.kafka.constants.KafkaMemberConstants;
import ChattingService.repository.ChatParticipationRepository;
import ChattingService.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaMessageService {

    private final KafkaTemplate<String, ResponseMessageDto> kafkaTemplate;
    private final KafkaTemplate<String, AlarmInfo> alarmInfoKafkaTemplate;
    private final MessageService messageService;
    private final ChatParticipationRepository chatParticipationRepository;
    //producer
    public void send(String topic, MessageDto messageDto) {
        log.info("topic : " + topic);
        log.info("send Message : " + messageDto.getDetailMessage());
        try{
            ResponseMessageDto responseMessageDto = messageService.SaveAndChangeToMessageResponseDto(messageDto);
            kafkaTemplate.send(topic,responseMessageDto);
        }catch (Exception e){
            e.printStackTrace();
            throw new RestException(HttpStatus.NOT_ACCEPTABLE,"SAVE Failed");
        }

        //kafkaTemplate.send(topic,messageDto);
    }

    //consumer
    private final SimpMessageSendingOperations sendingOperations;
    private final SimpMessagingTemplate template;
    //@KafkaListener(topics = KafkaConstants.KAFKA_TOPIC, groupId = KafkaConstants.GROUP_ID)
    @KafkaListener(topics = KafkaConstants.KAFKA_TOPIC)
    public void consume(ResponseMessageDto responseMessageDto) throws IOException {
        log.info("Consumed Message : " + responseMessageDto.getDetailMessage());

        //sendingOperations.convertAndSend("/topic/"+messageDto.getRoomId(),messageDto);
        template.convertAndSend("/topic/"+responseMessageDto.getRoomId(),responseMessageDto);
        //푸시알림보내기
        List<Long> memberIdList = chatParticipationRepository.findMemberIdListByChattingRoomId(responseMessageDto.getRoomId());
        alarmInfoKafkaTemplate.send(KafkaAlarmConstants.KAFKA_TOPIC, new AlarmInfo(memberIdList,"chatting", responseMessageDto.getSenderName(), responseMessageDto.getDetailMessage()));

    }
    private final KafkaTemplate<String, String> kafkaTemplateMember;

    public void sendMemberTopic(){
        String data = "tjddns";
        kafkaTemplateMember.send(KafkaMemberConstants.KAFKA_TOPIC, data);
    }
    @KafkaListener(topics = KafkaMemberConstants.KAFKA_TOPIC, groupId = KafkaMemberConstants.GROUP_ID, containerFactory = "memberKafkaListenerContainerFactory")
    public void consume0(String data) {
        log.info("data:{}",data);
    }


}
