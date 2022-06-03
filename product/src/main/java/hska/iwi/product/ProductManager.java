package hska.iwi.product;

import java.util.List;
import org.springframework.http.ResponseEntity;

public interface ProductManager {


    ResponseEntity<List<Product>> getProducts(String name, String value, Double minPrice, Double maxPrice);

    ResponseEntity<Product> getProductById(int id);


    ResponseEntity<Void> addProduct(Product product);


    ResponseEntity<Boolean> deleteProductsByCategoryId(int categoryId);


    ResponseEntity<Void> deleteProductById(int id);
}
