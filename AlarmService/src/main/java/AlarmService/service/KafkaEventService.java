package AlarmService.service;

import AlarmService.kafka.AlarmInfo;
import AlarmService.kafka.KafkaConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaEventService {
    private final MemberTokenService memberTokenService;

    @KafkaListener(topics = KafkaConstants.KAFKA_TOPIC, groupId = KafkaConstants.GROUP_ID, containerFactory = "kafkaListenerContainerFactory")
    public void consume(AlarmInfo alarmInfo){
        log.info("Consumed Message : " + alarmInfo.getTitle());
        List<String> tokenList = memberTokenService.getTokenList(alarmInfo.getMemberIdList());
        memberTokenService.sendPushAlarm(tokenList, alarmInfo.getTitle(), alarmInfo.getBody(), alarmInfo.getServiceType(), 10L);
    }
}
