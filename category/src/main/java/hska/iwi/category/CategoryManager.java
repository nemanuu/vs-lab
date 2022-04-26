package hska.iwi.category;

import java.util.List;

public interface CategoryManager {

    List<Category> getCategories(String name);

    public Category getCategory(int id);

    public void addCategory(String name);

    public void deleteCategoryById(int id);


}
