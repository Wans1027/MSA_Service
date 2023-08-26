package TradeGoodsService.service;

import TradeGoodsService.entity.trade.Goods;
import TradeGoodsService.entity.trade.WishList;
import TradeGoodsService.repository.trade.GoodsRepository;
import TradeGoodsService.repository.trade.WishListRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishListService {
    private final WishListRepository wishListRepository;
    private final GoodsRepository goodsRepository;

    @Transactional
    public Long addWishGoods(Long memberId, Long goodsId){
        Goods goods = goodsRepository.findById(goodsId).orElseThrow();
        WishList wish = wishListRepository.save(new WishList(memberId, goods));
        return wish.getId();
    }
    @Transactional
    public void deleteWishGoods(Long memberId, Long goodsId){
        wishListRepository.deleteWishGoods(memberId, goodsId);
    }
}
