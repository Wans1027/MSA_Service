package ChattingService.service;

import ChattingService.entity.ChattingRoom;
import ChattingService.kafka.AlarmInfo;
import ChattingService.kafka.constants.KafkaAlarmConstants;
import ChattingService.repository.ChatParticipationRepository;
import ChattingService.repository.ChattingRoomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Rollback
class ChattingRoomServiceTest {

    @Autowired
    ChattingRoomService chattingRoomService;
    @Autowired
    ChattingRoomRepository chattingRoomRepository;
    @Autowired
    ChatParticipationRepository chatParticipationRepository;

    @Autowired
    KafkaTemplate<String, AlarmInfo> alarmInfoKafkaTemplate;

    @Test
    @Transactional
    public void testCreateChattingRoom() {
        Long chatRoomId = chattingRoomService.createNewChattingRoom(10L, "chatRoom");
        ChattingRoom room = chattingRoomRepository.findById(chatRoomId).orElseThrow();
        Assertions.assertThat(room.getRoomName()).isEqualTo("chatRoom");
        chattingRoomService.inviteChattingRoom(12L,room.getId());
        Integer participantsCount = chattingRoomRepository.findById(chatRoomId).orElseThrow().getParticipantsCount();
        Assertions.assertThat(chatParticipationRepository.findByRoom(room).size()).isEqualTo(2);

        Assertions.assertThat(participantsCount).isEqualTo(2);
        chattingRoomService.outOfChattingRoom(12L,room.getId());
        Assertions.assertThat(chattingRoomRepository.findById(room.getId()).orElseThrow().getParticipantsCount()).isEqualTo(1);
        Assertions.assertThat(chatParticipationRepository.findByRoom(room).size()).isEqualTo(1);
        //Assertions.assertThat(chatParticipationRepository.find)
        chattingRoomService.outOfChattingRoom(10L, room.getId());
        Assertions.assertThatThrownBy(()->chattingRoomRepository.findById(room.getId()).orElseThrow()).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void 푸시테스트(){
        List<Long> memberIdList = new ArrayList<>();
        memberIdList.add(1L);
        memberIdList.add(2L);
        alarmInfoKafkaTemplate.send(KafkaAlarmConstants.KAFKA_TOPIC, new AlarmInfo(memberIdList,"chatting","카프카테스트", "카프카테스트바디"));
    }
}