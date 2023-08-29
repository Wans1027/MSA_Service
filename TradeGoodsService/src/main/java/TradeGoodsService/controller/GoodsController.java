package TradeGoodsService.controller;

import TradeGoodsService.entity.dto.GoodsDto;
import TradeGoodsService.entity.trade.Goods;
import TradeGoodsService.entity.trade.Result;
import TradeGoodsService.service.GoodsService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GoodsController {
    private final GoodsService goodsService;

    @PostMapping("/goods")
    public Long createNewGoods(@RequestBody GoodsDto goodsDto){
        System.out.println(goodsDto.getDescription());
        return goodsService.saveGoods(goodsDto.getSellerId(), goodsDto.getSellingAreaId(), goodsDto.getSellPrice(),
                goodsDto.getCategoryId(), goodsDto.getTitle(), goodsDto.getDescription(), goodsDto.getStatus());

    }

    @GetMapping("/goods") //goods?areaIdList=1,2,3
    public Result<GoodsDto> loadGoodsByAreaIds(@RequestParam List<Integer> areaIdList) {
        //List<Integer> areaIds = areaIdList.getAreaIds();
        List<Goods> goods = goodsService.loadGoodsByAreaIds(areaIdList);
        List<GoodsDto> goodsDto = goods.stream().map(g -> new GoodsDto(g.getId(), g.getSellerId(), g.getSellingAreaId(),
                g.getCategoryId(), g.getTitle(), g.getStatus(), g.getSellPrice(),
                g.getDescription(),g.getGoodsThumbnail(),g.getCreateAt())).collect(Collectors.toList());
        return new Result<>(goodsDto, goodsDto.size());
    }

    @PatchMapping("/goods/{goodsId}")
    public Long modifyGoods(@RequestBody GoodsDto goodsDto, @PathVariable("goodsId") Long goodsId){
        return goodsService.updateGoods(goodsId, goodsDto.getSellPrice(),
                goodsDto.getCategoryId(), goodsDto.getTitle(), goodsDto.getDescription(), goodsDto.getStatus());
    }

    @Data
    private static class AreaIdList{
        private List<Integer> areaIds;
    }

}
