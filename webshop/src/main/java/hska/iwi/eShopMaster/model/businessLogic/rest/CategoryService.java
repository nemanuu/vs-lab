package hska.iwi.eShopMaster.model.businessLogic.rest;

import java.util.List;

import hska.iwi.eShopMaster.model.businessLogic.dataobjects.Category;

public interface CategoryService {
    public List<Category> getCategories();
	
	public Category getCategory(int id);
	
	public Category getCategoryByName(String name);
	
	public void addCategory(String name);
	
	public void delCategoryById(int id);
    
}
