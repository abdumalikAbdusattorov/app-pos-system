package uz.pdp.botsale.payload;

import lombok.Data;

@Data
public class ReqCategory {
    private Integer id;
    private String name;
    private Integer parentId;
}
