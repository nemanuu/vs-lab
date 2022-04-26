package hska.iwi.product;

import java.util.List;

public interface ProductManager {


    List<Product> getProducts(String name, String value, Double minPrice, Double maxPrice);

    Product getProductById(int id);


    int addProduct(String name, double price, int categoryId, String details);


    boolean deleteProductsByCategoryId(int categoryId);


    void deleteProductById(int id);
}
