package uz.pdp.botsale.payload;

import lombok.Data;

@Data
public class ReqClient {
    private Long id;
    private String name;
    private Double debt;
    private String phoneNumber;

}
