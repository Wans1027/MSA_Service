package TradeGoodsService.repository.trade;

import TradeGoodsService.entity.trade.WishList;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {
    @Modifying(clearAutomatically = true)
    @Transactional
    @EntityGraph(attributePaths = {"goods"})
    @Query("delete from WishList w where w.memberId = :memberId and goods.id = :goodsId")
    void deleteWishGoods(@Param("memberId") Long memberId, @Param("goodsId") Long goodsId);

    /**
     * clearAutomatically : modifying query를 실행한 직후에 영속성 컨텍스트를 clear 시킨다.
     * flushAutomatically : modifying query를 실행하기 직전에 영속성 컨텍스트를 flush 한다.
     */
    @Modifying(clearAutomatically = true)
    @Transactional
    //@EntityGraph(attributePaths = {"goods"})
    @Query("delete from WishList w where w.memberId = :memberId")
    void deleteWishGoodsTest(@Param("memberId") Long memberId);

    /*
     * @Modifying 이용 : 자유롭게 조건을 설정하여 단 한 번의 쿼리로 Bulk Delete
     * 벌크 연산은 1차 캐시를 포함한 영속성 컨텍스트를 무시하고 바로 Query를 실행하기 때문에 영속성 컨텍스트는 데이터 변경을 알 수가 없습니다.
     * -> clearAutomatically를 true로 변경해준다면, 벌크 연산 직 후 자동으로 영속성 컨텍스트를 clear 해줍니다.
     * 그래서 조회를 실행하면 1차캐시에 해당 엔티티가 존재하지 않기 때문에 DB 조회 쿼리를 실행하게 됩니다.
     * Hibernate의 FlushModeType의 Default 값은 AUTO입니다.
     * 그래서 @Modifying(flushAutomatically = false)여도 해당 쿼리 메서드 실행 전 flush가 실행됩니다.
     * -> FlushModeType의 값을 COMMIT으로 변경
     * flushAutomatically의 Default 값인 false를 그대로 사용하게 되면, 쿼리 메서드 실행 시 flush가 실행이 되지 않습니다. 즉, 필요한 경우에 flush를 강제 호출하거나, @Modifying 같은 경우에는 flushAutomatically를 true로 변경해야 합니다.
     */
}
