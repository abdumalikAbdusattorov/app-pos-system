package uz.pdp.botsale.collections;

import uz.pdp.botsale.entity.Cash;
import uz.pdp.botsale.entity.Client;
import uz.pdp.botsale.entity.Market;
import uz.pdp.botsale.entity.SellDetails;
import uz.pdp.botsale.entity.enums.StatusSell;

import java.util.List;

public interface SellCol {
    List<SellDetails> getSellDetails();

    Client getClient();

    Market getMarket();

    Cash getCash();

    StatusSell getStatusSell();

    Double getTotal();
}
