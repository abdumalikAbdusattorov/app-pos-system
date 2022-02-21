package uz.pdp.botsale.payload;

import lombok.Data;

@Data
public class ReqPurchaseElements {
    private Long id;
    private Long productId;
    private Double incomePrice;
    private Double sellPrice;
    private Integer minCount;
    private Integer count;
}
