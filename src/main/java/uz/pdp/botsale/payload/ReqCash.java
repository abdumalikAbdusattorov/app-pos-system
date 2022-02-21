package uz.pdp.botsale.payload;

import lombok.Data;
import uz.pdp.botsale.entity.Sell;

import java.util.List;

@Data
public class ReqCash {
    private Integer id;
    private String name;
    private Double startBalance;
    private List<Sell> sellList;
}
