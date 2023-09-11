package ChattingService.repository;

import ChattingService.entity.ChatParticipation;
import ChattingService.entity.ChattingRoom;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
class ChatParticipationRepositoryTest {

    @Autowired ChatParticipationRepository chatParticipationRepository;
    @Autowired ChattingRoomRepository chattingRoomRepository;

    @Test
    @Transactional
    void findMemberIdListByChattingRoomId() {
        ChattingRoom chatRoom = chattingRoomRepository.save(new ChattingRoom("테스트", 0, "중고거래"));
        chatParticipationRepository.save(new ChatParticipation(chatRoom, 100L));
        chatParticipationRepository.save(new ChatParticipation(chatRoom, 101L));
        List<Long> memberIdList = chatParticipationRepository.findMemberIdListByChattingRoomId(chatRoom.getId());
        Assertions.assertThat(memberIdList.get(0)).isEqualTo(100L);
        Assertions.assertThat(memberIdList.get(1)).isEqualTo(101L);
    }
}