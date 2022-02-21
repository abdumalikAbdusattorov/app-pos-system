package uz.pdp.botsale.collections;

import uz.pdp.botsale.entity.Sell;

import java.util.List;

public interface CashCol {
    String getName();

    Double getStartBalance();

    List<Sell> getSellList();

    boolean getActive();
}
