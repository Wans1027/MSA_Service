package TradeGoodsService.entity.trade;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@Getter
public class WishList extends BaseTime{
    @Id
    @GeneratedValue
    @Column(name = "wish_list_id")
    private Long id;
    private Long memberId;
    //한명의 유저는 여러 상품을 찜
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    public WishList(Long memberId, Goods goods) {
        this.memberId = memberId;
        this.goods = goods;
    }
}
