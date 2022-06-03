package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.ProductManager;
import hska.iwi.eShopMaster.model.businessLogic.rest.Factory;
import hska.iwi.eShopMaster.model.businessLogic.rest.ProductService;
import hska.iwi.eShopMaster.model.businessLogic.dataobjects.Category;
import hska.iwi.eShopMaster.model.businessLogic.dataobjects.ProductView;

import java.util.List;

import org.springframework.web.reactive.function.client.WebClientException;

public class ProductManagerImpl implements ProductManager {
	private ProductService helper = Factory.getProductService();

	public List<ProductView> getProducts() {
		return helper.getProducts();
	}
	
	public List<ProductView> getProductsForSearchValues(String searchDescription,
			Double searchMinPrice, Double searchMaxPrice) {	
		return helper.getProductsForSearchValues(searchDescription, searchMinPrice, searchMaxPrice);
	}

	public ProductView getProductById(int id) {
		return helper.getProductById(id);
	}

	public ProductView getProductByName(String name) {
		return helper.getProductByName(name);
	}
	
	public int addProduct(String name, double price, int categoryId, String details) {
		CategoryManager categoryManager = new CategoryManagerImpl();
		Category category = categoryManager.getCategory(categoryId);

		return helper.addProduct(name, price, category.getId(), details);
	}
	

	public void deleteProductById(int id) {
		helper.deleteProductById(id);
	}

	public boolean deleteProductsByCategoryId(int categoryId) {
		try {
			helper.deleteProductsByCategoryId(categoryId);
			return true;
		} catch (WebClientException e) {
			return false;
		}
	}

}
