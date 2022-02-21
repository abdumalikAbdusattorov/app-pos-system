package uz.pdp.botsale.collections;

import uz.pdp.botsale.entity.Product;

public interface PurchaseElementsCol {

    Long getId();

    Product getProduct();

    Double getIncomePrice();

    Double getSellPrice();

    Integer getCount();

    Integer getPresentCount();

    Double getPresentPrice();

    Integer getMinCount();

    boolean getActive();
}
