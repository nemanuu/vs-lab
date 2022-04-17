package hska.iwi.product;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController implements ProductManager {

    private static List<Product> testProductData = List.of(
            new Product("Pommes", 5, "Lecker Pommes!"),
            new Product("Salat", 2, "Grüner Salat"));

    @Override
    @GetMapping("/products")
    public List<Product> getProducts() {
        return testProductData;
    }

    @Override
    @GetMapping("/product")
    public Product getProductById(@RequestParam int id) {
        return testProductData.stream()
                .filter(x -> x.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    //TODO
    public Product getProductByName(@RequestParam String name) {
        return testProductData.stream()
                .filter(x -> x.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    //TODO
    public List<Product> getProductsForSearchValues( String searchValue, Double searchMinPrice, Double searchMaxPrice) {
        return testProductData.stream()
                .filter(x -> x.getName().contains(searchValue))
                .collect(Collectors.toList());
    }

    @Override
    @PutMapping("/product")
    public int addProduct(String name, double price, int categoryId, String details) {
        testProductData.add(new Product(name, price, details));
        return 0;
    }

    @Override
    public boolean deleteProductsByCategoryId(int categoryId) {
        //TODO
        return false;
    }

    @Override
    @DeleteMapping("/product")
    public void deleteProductById(int id) {
        Product product = getProductById(id);
        if (product != null) {
            testProductData.remove(product);
        }
    }
}
