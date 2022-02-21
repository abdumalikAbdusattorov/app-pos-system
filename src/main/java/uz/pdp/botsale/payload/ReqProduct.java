package uz.pdp.botsale.payload;

import lombok.Data;

@Data
public class ReqProduct {
    private Long id;
    private String name;
    private String barCode;
    private Integer categoryId;
    private Integer brandId;
}
