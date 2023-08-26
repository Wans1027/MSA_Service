package TradeGoodsService.entity.town;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "neighborhood_town")
public class NeighborhoodTown {
    @Id
    @Column(name = "neighborhood_id")
    private Long id;
    @Column(name = "member_id")
    private Long memberId;
    private Integer townCode;
    private String townName;
    private Boolean myTown;

}
