package hska.iwi.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ProductController implements ProductManager {

    @Autowired
    private ProductRepository productRepository;

    //private static List<Product> testProductData = List.of(
    //        new Product("Pommes", 5, "Lecker Pommes!"),
    //       new Product("Salat", 2, "Grüner Salat"));

    @Override
    @GetMapping("/products")
    public List<Product> getProducts() {
        return Streamable.of(productRepository.findAll()).toList();
    }

    @Override
    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable("id") int id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such product");
        }
        return productRepository.findById(id).get();
    }

    @Override
    @GetMapping("/products/find")
    public Product getProductByName(@RequestParam("name") String name) {
        List<Product> products = getProducts();

        return products.stream()
                .filter(x -> x.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    @GetMapping("/products/search")
    public List<Product> getProductsForSearchValues(@RequestParam("value") String value, @RequestParam("minPrice") Double minPrice, @RequestParam("maxPrice") Double maxPrice) {
        List<Product> products = getProducts();

        return products.stream()
                .filter(x -> x.getName().contains(value))
                .collect(Collectors.toList());
    }

    @Override
    @PostMapping("/products")
    public int addProduct(@RequestParam("name") String name, @RequestParam("price") double price, @RequestParam("categoryId") int categoryId, @RequestParam("details") String details) {
        Product product = new Product().setName(name).setPrice(price).setDetails(details).setCategoryId(categoryId);
        productRepository.save(product);
        return 0;
    }

    @Override
    @DeleteMapping("/products")
    public boolean deleteProductsByCategoryId(@RequestParam("categoryId") int categoryId) {
        List<Product> products = getProducts().stream().filter(x -> x.getCategoryId() == categoryId).collect(Collectors.toList());
        products.forEach(p -> productRepository.delete(p));
        return products.size() != 0;
    }

    @Override
    @DeleteMapping("/products/{id}")
    public void deleteProductById(@PathVariable("id") int id) {
        Product product = getProductById(id);

        if (product != null) {
            productRepository.delete(product);
        }
    }
}
