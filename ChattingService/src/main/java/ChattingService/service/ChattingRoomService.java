package ChattingService.service;

import ChattingService.entity.ChatParticipation;
import ChattingService.entity.ChattingRoom;
import ChattingService.repository.ChatParticipationRepository;
import ChattingService.repository.ChattingRoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChattingRoomService {

    private final ChattingRoomRepository chattingRoomRepository;
    private final ChatParticipationRepository chatParticipationRepository;
    @Transactional
    public Long createNewChattingRoom(Long memberId, String chattingRoomName){
        ChattingRoom room = new ChattingRoom(chattingRoomName,1,"OPEN_CHAT");
        ChattingRoom savedChattingRoom = chattingRoomRepository.save(room);
        ChatParticipation chatParticipation = new ChatParticipation(savedChattingRoom, memberId);
        chatParticipationRepository.save(chatParticipation);
        return savedChattingRoom.getId();
    }


}
