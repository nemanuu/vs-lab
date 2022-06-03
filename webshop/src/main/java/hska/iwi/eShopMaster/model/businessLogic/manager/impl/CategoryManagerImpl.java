package hska.iwi.eShopMaster.model.businessLogic.manager.impl;


import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.businessLogic.rest.CategoryService;
import hska.iwi.eShopMaster.model.businessLogic.rest.Factory;
import hska.iwi.eShopMaster.model.businessLogic.dataobjects.Category;

import java.util.List;

public class CategoryManagerImpl implements CategoryManager{
	private CategoryService helper = Factory.getCategoryService();

	public List<Category> getCategories() {
		return helper.getCategories();
	}

	public Category getCategory(int id) {
		return helper.getCategory(id);
	}

	public Category getCategoryByName(String name) {
		return helper.getCategoryByName(name);
	}

	public void addCategory(String name) {
		helper.addCategory(name);

	}

	public void delCategory(Category cat) {
	
// 		Products are also deleted because of relation in Category.java 
		delCategoryById(cat.getId());
	}

	public void delCategoryById(int id) {
		
		helper.delCategoryById(id);
	}
}
