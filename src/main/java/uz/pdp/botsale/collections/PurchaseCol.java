package uz.pdp.botsale.collections;

import uz.pdp.botsale.entity.Company;
import uz.pdp.botsale.entity.PurchaseElements;

import java.util.List;

public interface PurchaseCol {
    List<PurchaseElements> getPurchaseElementsList();

    Company getCompany();

    Double getTotal();

    boolean getActice();
}
