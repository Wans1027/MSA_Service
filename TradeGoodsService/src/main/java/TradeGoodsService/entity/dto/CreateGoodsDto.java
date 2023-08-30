package TradeGoodsService.entity.dto;

import TradeGoodsService.entity.trade.Goods;
import lombok.Data;

@Data
public class CreateGoodsDto {
    private Long sellerId; //판매자Id

    private Integer sellingAreaId; //판매지역Id

    private Integer categoryId; //카테고리Id

    private String title; //상품이름

    private Goods.Status status; //상태

    private Integer sellPrice; //판매가격

    //private Integer viewCount; //조회수
    private String description; //상품설명
}
