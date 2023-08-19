package ChattingService.repository;

import ChattingService.entity.ChatParticipation;
import ChattingService.entity.ChattingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatParticipationRepository extends JpaRepository<ChatParticipation, Long> {
    @Modifying
    @Query("delete from ChatParticipation c where c.memberId = :memberId and c.chattingRoom = :chattingRoom")
    void deleteByIdAndChatRoom(@Param("memberId") Long memberId, @Param("chattingRoom") ChattingRoom chattingRoom);

    @Query("select c from ChatParticipation c where c.chattingRoom = :chattingRoom")
    List<ChatParticipation> findByRoom(@Param("chattingRoom") ChattingRoom chattingRoom);

    @Query("select c from ChatParticipation c where c.memberId = :memberId and c.chattingRoom = :chattingRoom")
    List<ChatParticipation> findByMemberIdAndRoom(@Param("memberId") Long memberId, @Param("chattingRoom") ChattingRoom chattingRoom);
}
