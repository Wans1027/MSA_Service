package TradeGoodsService.entity.trade;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Goods extends TimeEntity {

    @Id
    @Column(name = "goods_id")
    @GeneratedValue
    private Long id;
    @NotNull
    private Long sellerId; //판매자Id
    @NotNull
    private Integer sellingAreaId; //판매지역Id
    @NotNull
    private Integer categoryId; //카테고리Id
    @NotNull
    private String title; //상품이름
    @NotNull
    private Status status; //상태
    @NotNull
    private Integer sellPrice; //판매가격
    //@NotNull
    //private Integer viewCount; //조회수
    @NotNull
    private String description; //상품설명
    //private byte[] goodsThumbnail;

    @OneToMany(mappedBy = "goods", cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    private List<WishList> wishLists;

    @OneToMany(mappedBy = "goods", cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    private List<GoodsImage> goodsImages;

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status{
        New, Reserved, SoldOut
    }

    public Goods(Long sellerId, Integer sellingAreaId, Integer categoryId, String title, Status status, Integer sellPrice, String description) {
        this.sellerId = sellerId;
        this.sellingAreaId = sellingAreaId;
        this.categoryId = categoryId;
        this.title = title;
        this.status = status;
        this.sellPrice = sellPrice;
        this.description = description;
    }
}
