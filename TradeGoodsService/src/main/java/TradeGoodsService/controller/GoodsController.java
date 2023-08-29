package TradeGoodsService.controller;

import TradeGoodsService.entity.dto.GoodsDto;
import TradeGoodsService.entity.trade.Goods;
import TradeGoodsService.entity.trade.Result;
import TradeGoodsService.service.GoodsService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GoodsController {
    private final GoodsService goodsService;

    @PostMapping("/goods")
    public Long createNewGoods(GoodsDto goodsDto){
        return goodsService.saveGoods(goodsDto.getSellerId(), goodsDto.getSellingAreaId(), goodsDto.getSellPrice(),
                goodsDto.getCategoryId(), goodsDto.getTitle(), goodsDto.getDescription(), goodsDto.getStatus());

    }

    @GetMapping("/goods")
    public Result<Goods> loadGoodsByAreaIds(AreaIdList areaIdList) {
        List<Integer> areaIds = areaIdList.getAreaIds();
        List<Goods> goods = goodsService.loadGoodsByAreaIds(areaIds);
        return new Result<>(goods, goods.size());
    }

    @PatchMapping("/goods/{goodsId}")
    public Long modifyGoods(GoodsDto goodsDto, @PathVariable("goodsId") Long goodsId){
        return goodsService.updateGoods(goodsId, goodsDto.getSellPrice(),
                goodsDto.getCategoryId(), goodsDto.getTitle(), goodsDto.getDescription(), goodsDto.getStatus());
    }

    @Data
    private static class AreaIdList{
        private List<Integer> areaIds;
    }

}
