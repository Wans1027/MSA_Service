package TradeGoodsService.repository.trade;

import TradeGoodsService.entity.trade.GoodsImage;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoodsImageRepository extends JpaRepository<GoodsImage, Long> {
    @Transactional
    @EntityGraph(attributePaths = {"goods"})
    @Query("select i from GoodsImage i where goods.id = :goodsId")
    Optional<List<GoodsImage>> findByGoodsId(@Param("goodsId") Long goodsId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @EntityGraph(attributePaths = {"goods"})
    @Query("delete from GoodsImage i where goods.id = :goodsId")
    void deleteByGoodsId(@Param("goodsId") Long goodsId);
}
