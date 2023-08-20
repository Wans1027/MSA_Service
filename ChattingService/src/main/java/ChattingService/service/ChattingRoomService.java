package ChattingService.service;

import ChattingService.entity.ChatParticipation;
import ChattingService.entity.ChattingRoom;
import ChattingService.repository.ChatParticipationRepository;
import ChattingService.repository.ChattingRoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChattingRoomService {

    private final ChattingRoomRepository chattingRoomRepository;
    private final ChatParticipationRepository chatParticipationRepository;
    @Transactional
    public Long createNewChattingRoom(Long memberId, String chattingRoomName){
        ChattingRoom room = new ChattingRoom(chattingRoomName,1,"OPEN_CHAT");
        ChattingRoom save = chattingRoomRepository.save(room);
        ChatParticipation chatParticipation = new ChatParticipation(save, memberId);
        chatParticipationRepository.save(chatParticipation);
        return save.getId();
    }
    @Transactional
    public void inviteChattingRoom(Long memberId, Long chatRoomId) {
        Optional<ChattingRoom> chatRoom = chattingRoomRepository.findById(chatRoomId);
        if(chatParticipationRepository.findByMemberIdAndRoom(memberId, chatRoom.orElseThrow()).isEmpty()) return;
        chatRoom.orElseThrow().setParticipantsCount(chatRoom.orElseThrow().getParticipantsCount() + 1);
        ChatParticipation chatParticipation = new ChatParticipation(chatRoom.orElseThrow(),memberId);
        chatParticipationRepository.save(chatParticipation);
    }
    @Transactional
    public void outOfChattingRoom(Long memberId, Long chatRoomId){
        ChattingRoom room = chattingRoomRepository.findById(chatRoomId).orElseThrow();
        chatParticipationRepository.deleteByIdAndChatRoom(memberId, room);
        room.setParticipantsCount(room.getParticipantsCount() - 1);
        if(room.getParticipantsCount() == 0) chattingRoomRepository.delete(room);
    }

    public void showMyChattingRoomList(Long memberId){
        List<ChattingRoom> myChattingRooms = chatParticipationRepository.findByMemberId(memberId);

    }

}
