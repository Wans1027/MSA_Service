package TradeGoodsService.entity.trade;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class GoodsImage {
    @Id
    @GeneratedValue
    @Column(name = "goods_image_id")
    private Long id;

    private String imageName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    public GoodsImage(String imageName, Goods goods) {
        this.imageName = imageName;
        this.goods = goods;
    }
}
