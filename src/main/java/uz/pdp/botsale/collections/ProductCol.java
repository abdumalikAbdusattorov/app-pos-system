package uz.pdp.botsale.collections;

import uz.pdp.botsale.entity.Brand;
import uz.pdp.botsale.entity.Company;

public interface ProductCol {
    String getName();

    Company getCompany();

    Brand getBrand();

    String getBarCode();

    boolean getActive();
}
