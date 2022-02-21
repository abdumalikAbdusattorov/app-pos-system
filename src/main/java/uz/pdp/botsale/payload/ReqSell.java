package uz.pdp.botsale.payload;

import lombok.Data;
import uz.pdp.botsale.entity.Client;
import uz.pdp.botsale.entity.enums.StatusSell;

import java.util.List;

@Data
public class ReqSell {
   private List<ReqSellDetails> reqSellDetailsList;
   private Client client;
   private Integer marketId;
   private Integer cashId;
   private StatusSell statusSell;
}
