package uz.pdp.botsale.payload;

import lombok.Data;

@Data
public class ReqStockDetail {
    private Long id;
    private Long productId;
    private Double beforePrice;
    private Double afterPrice;
}
