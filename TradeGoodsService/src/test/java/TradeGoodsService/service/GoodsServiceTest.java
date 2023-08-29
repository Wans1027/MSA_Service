package TradeGoodsService.service;

import TradeGoodsService.entity.trade.Goods;
import TradeGoodsService.repository.trade.GoodsRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class GoodsServiceTest {
    @Autowired GoodsService goodsService;
    @Autowired
    GoodsRepository goodsRepository;
    @Test
    @Transactional
    void saveGoods() {
        Long goodsId = goodsService.saveGoods(1L, 10, 1000, 1, "제목", "아 안녕하세요 물건팔아요!", Goods.Status.New);
        Optional<Goods> goods = goodsRepository.findById(goodsId);
        Assertions.assertThat(goods.isPresent()).isTrue();
        //System.out.println(goods.orElseThrow().getDescription());
    }

    @Test
    void updateGoods() {
    }

    @Test
    void loadGoodsByAreaIds() {
    }

    @Test
    void deleteGoods() {
    }
}