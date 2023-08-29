package TradeGoodsService.repository.trade;

import TradeGoodsService.entity.trade.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long> {
    @Transactional(readOnly = true)
    @Query("select g from Goods g where g.sellingAreaId in :areaIds order by g.createAt desc")
    List<Goods> findGoodsByAreaIdsOrderByDate(@Param("areaIds") List<Integer> areaIds);
}
