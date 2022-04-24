package hska.iwi.category;

import java.util.List;

public interface CategoryManager {

	public List<Category> getCategories();
	
	public Category getCategory(int id);
	
	public Category getCategoryByName(String name);
	
	public void addCategory(String name);
	
	public void deleteCategoryById(int id);

	
}
