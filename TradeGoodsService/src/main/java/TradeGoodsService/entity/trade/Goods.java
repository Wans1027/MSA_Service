package TradeGoodsService.entity.trade;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Goods extends BaseTime {

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
    /*@ColumnDefault("0")
    private Integer viewCount; //조회수*/
    @NotNull
    private String description; //상품설명
    private String goodsThumbnail;

    public void setGoodsThumbnail(String goodsThumbnail) {
        this.goodsThumbnail = goodsThumbnail;
    }

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

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSellPrice(Integer sellPrice) {
        this.sellPrice = sellPrice;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
