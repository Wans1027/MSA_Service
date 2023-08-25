package ChattingService.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Table(indexes = @Index(name = "index_memberId", columnList = "memberId"))
public class ChatParticipation {
    @Id
    @GeneratedValue
    @Column(name = "chatPart_id")
    private Long id;
    @NotNull
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chattingRoom_id")
    private ChattingRoom chattingRoom;

    public ChatParticipation(ChattingRoom chattingRoom, Long memberId) {
        this.chattingRoom = chattingRoom;
        this.memberId = memberId;
    }
}
