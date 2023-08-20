package ChattingService.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChattingRoom extends TimeEntity{
    @Id
    @GeneratedValue
    @Column(name = "room_id")
    private Long id;

    @NotEmpty
    private String roomName;

    private Integer participantsCount;

    private String roomType;

    @OneToMany(mappedBy = "chattingRoom", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<ChatParticipation> chatParticipationList;

    public ChattingRoom(String roomName, Integer participantsCount, String roomType) {
        this.roomName = roomName;
        this.participantsCount = participantsCount;
        this.roomType = roomType;
    }
    public void setParticipantsCount(Integer participantsCount) {
        this.participantsCount = participantsCount;
    }
}
