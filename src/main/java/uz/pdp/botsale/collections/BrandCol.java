package uz.pdp.botsale.collections;

import uz.pdp.botsale.entity.Attachment;

public interface BrandCol {
    String getName();

    Attachment getBrandIcon();
    
    boolean getActive();
}
