package TradeGoodsService.repository.town;

import TradeGoodsService.entity.town.NeighborhoodTown;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TownRepository extends ReadOnlyJpaRepository<NeighborhoodTown, Long>{
    @Transactional(readOnly = true)
    @Query("select t from NeighborhoodTown t where t.memberId = :memberId")
    List<NeighborhoodTown> findTownByMemberId(@Param("memberId") Long memberId);

    @Transactional(readOnly = true)
    @Query("select t from NeighborhoodTown t where t.memberId = :memberId and t.myTown = true")
    List<NeighborhoodTown> findMyTownByMemberId(@Param("memberId") Long memberId);
}
