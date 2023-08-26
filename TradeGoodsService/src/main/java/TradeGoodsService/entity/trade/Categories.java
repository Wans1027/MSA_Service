package TradeGoodsService.entity.trade;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Categories {

    @Id
    @Column(name = "category_id")
    @GeneratedValue
    private Long id;
    private String name;
}
