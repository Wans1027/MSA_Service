package TradeGoodsService.repository.trade;

import TradeGoodsService.entity.trade.Goods;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class GoodsRepositoryTest {
    @Autowired GoodsRepository goodsRepository;

    @Autowired
    EntityManager em;

    @Test
    @Transactional
    void findGoodsByAreaIdsOrderByDate() {
        goodsRepository.save(new Goods(1L, 100, 1, "상품0", Goods.Status.New, 1000, "상품입니다."));
        goodsRepository.save(new Goods(2L, 101, 1, "상품1", Goods.Status.New, 1000, "상품입니다."));
        goodsRepository.save(new Goods(3L, 102, 1, "상품2", Goods.Status.New, 1000, "상품입니다."));
        goodsRepository.save(new Goods(4L, 103, 1, "상품3", Goods.Status.New, 1000, "상품입니다."));

        List<Integer> areaIds = new ArrayList<>();
        areaIds.add(100);
        areaIds.add(101);
        areaIds.add(102);


        List<Goods> goodsList = goodsRepository.findGoodsByAreaIdsOrderByDate(areaIds);
        int i = 2;
        em.flush();

        for (Goods goods : goodsList) {
            System.out.println(goods.getTitle());
            System.out.println(goods.getCreateAt());
            Assertions.assertThat(goods.getTitle()).isEqualTo("상품" + i);
            i--;
        }
        Assertions.assertThat(goodsList.size()).isEqualTo(3);
    }
}