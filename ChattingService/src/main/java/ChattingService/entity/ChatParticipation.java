package ChattingService.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@AllArgsConstructor
@Getter
public class ChatParticipation {
    @Id
    @GeneratedValue
    @Column(name = "chatPart_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chattingRoom_id")
    private ChattingRoom chattingRoom;

    @NotEmpty
    private Long memberId;

    public ChatParticipation(ChattingRoom chattingRoom, Long memberId) {
        this.chattingRoom = chattingRoom;
        this.memberId = memberId;
    }
}
