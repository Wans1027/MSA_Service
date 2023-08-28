package TradeGoodsService.repository.trade;

import TradeGoodsService.entity.trade.GoodsImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsImageRepository extends JpaRepository<GoodsImage, Long> {
}
