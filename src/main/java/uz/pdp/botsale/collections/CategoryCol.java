package uz.pdp.botsale.collections;

import uz.pdp.botsale.entity.Category;

import java.util.List;

public interface CategoryCol {
    String getName();

    Category getParent();

    List<Category> getChildCategories();
    
    boolean getActive();
}
