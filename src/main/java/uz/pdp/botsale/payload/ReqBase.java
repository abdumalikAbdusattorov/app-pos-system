package uz.pdp.botsale.payload;

import lombok.Data;
import uz.pdp.botsale.entity.Product;
import uz.pdp.botsale.entity.PurchaseElements;

import java.util.List;

@Data
public class ReqBase {
    private Long id;
    private List<PurchaseElements> purchaseElementsList;
    private Integer marketId;
    private Product productId;
    private Double count;
    private Double stockpercent;//opshi astatka soni
}
