package uz.pdp.botsale.collections;

import uz.pdp.botsale.entity.Market;
import uz.pdp.botsale.entity.StockDetail;

import java.sql.Timestamp;
import java.util.List;

public interface StockCol {
    String getName();

    Double getPercent();

    Timestamp getStartData();

    Timestamp getEndData();

    List<StockDetail> getStockDetailList();

    Market getMarket();

    boolean getActive();
}
