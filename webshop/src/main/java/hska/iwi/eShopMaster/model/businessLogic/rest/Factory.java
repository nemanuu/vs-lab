package hska.iwi.eShopMaster.model.businessLogic.rest;

import hska.iwi.eShopMaster.model.businessLogic.rest.impl.CategoryServiceImpl;
import hska.iwi.eShopMaster.model.businessLogic.rest.impl.ProductServiceImpl;

public class Factory {

    public static ProductService getProductService() {
        return new ProductServiceImpl();
    }

    public static CategoryService getCategoryService() {
        return new CategoryServiceImpl();
    }
    
}
