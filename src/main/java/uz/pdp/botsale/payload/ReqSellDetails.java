package uz.pdp.botsale.payload;

import lombok.Data;

@Data
public class ReqSellDetails {
    private Long id;
    private String productBarcode;
    private Double count;
}
