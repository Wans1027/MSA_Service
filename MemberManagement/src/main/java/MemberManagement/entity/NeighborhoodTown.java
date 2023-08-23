package MemberManagement.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class NeighborhoodTown {
    @Id
    @Column(name = "neighborhood_id")
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private Integer townCode;
    private String townName;
    private Boolean myTown;

    public NeighborhoodTown(Member member, Integer townCode, String townName, Boolean myTown) {
        this.member = member;
        this.townCode = townCode;
        this.townName = townName;
        this.myTown = myTown;
    }
}
