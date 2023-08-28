package TradeGoodsService.entity.trade;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Result<T> {
    private List<T> content;
    private int totalElements;
}
