package TradeGoodsService.service;

import TradeGoodsService.entity.trade.Goods;
import TradeGoodsService.repository.trade.GoodsRepository;
import TradeGoodsService.repository.trade.WishListRepository;
import jakarta.transaction.Transactional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;


@SpringBootTest

class WishListServiceTest {
    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    WishListRepository wishListRepository;
    @Autowired
    WishListService wishListService;

    @Test
    @Transactional
    void addWishGoods() {
        Goods goods = goodsRepository.save(new Goods(1L, 100, 1, "상품", Goods.Status.New, 1000, "상품입니다."));
    }

    @Test
    @Transactional
    void deleteWishGoods() {
        Goods goods = goodsRepository.save(new Goods(1L, 100, 1, "상품", Goods.Status.New, 1000, "상품입니다."));
        Long wishId = wishListService.addWishGoods(2L, goods.getId());
        Assertions.assertThat(wishListRepository.findById(wishId)).isPresent();
        //wishListService.deleteWishGoods(2L, goods.getId());
        //wishListRepository.deleteById(wishId);
        //wishListService.deleteWishGoods(2L, goods);
        wishListRepository.deleteWishGoods(2L, goods.getId());
        Assertions.assertThatThrownBy(() -> wishListRepository.findById(wishId).orElseThrow()).isInstanceOf(NoSuchElementException.class);
    }
}