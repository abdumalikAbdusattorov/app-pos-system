package uz.pdp.botsale.payload;

import lombok.Data;

import java.util.List;

@Data
public class ReqPurchase {
    private Long id;
    private List<ReqPurchaseElements> reqPurchaseElementsList;
    private Long companyId;
}