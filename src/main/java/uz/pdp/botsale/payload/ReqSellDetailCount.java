package uz.pdp.botsale.payload;

import lombok.Data;

@Data
public class ReqSellDetailCount {
    private Long purchaseElementId;
    private Double count;
}
