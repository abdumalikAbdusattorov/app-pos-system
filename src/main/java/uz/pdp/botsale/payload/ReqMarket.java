package uz.pdp.botsale.payload;

import lombok.Data;
import uz.pdp.botsale.entity.Purchase;
import uz.pdp.botsale.entity.User;

import java.util.List;
import java.util.UUID;

@Data
public class ReqMarket {
    private Integer id;
    private String name;
    private String address;
    private Double lan;
    private Double lat;
    private List<Long> purchaseList;
    private List<UUID> users;
    private List<Integer> cashList;
}
