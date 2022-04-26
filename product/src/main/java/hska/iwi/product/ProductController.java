package hska.iwi.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController implements ProductManager {

    @Autowired
    private CategoryWebClient categoryWebClient;
    @Autowired
    private ProductRepository productRepository;

    @Override
    @GetMapping("/products")
    public List<Product> getProducts(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "value", required = false) String value,
            @RequestParam(value = "min-price", required = false) Double minPrice,
            @RequestParam(value = "max-price", required = false) Double maxPrice) {
        return Streamable.of(productRepository.findAll())
                .filter(p -> value == null || p.getName().contains(value))
                .filter(p -> name == null || p.getName().equals(name))
                .filter(p -> minPrice == null || p.getPrice() >= minPrice)
                .filter(p -> maxPrice == null || p.getPrice() <= maxPrice)
                .toList();
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
    @PostMapping("/products")
    public int addProduct(@RequestParam("name") String name, @RequestParam("price") double price, @RequestParam("categoryId") int categoryId, @RequestParam("details") String details) {

        if (!categoryWebClient.categoryExists(categoryId)) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "CategoryId does not exist.");
        }
        Product product = new Product().setName(name).setPrice(price).setDetails(details).setCategoryId(categoryId);
        productRepository.save(product);
        return 0;
    }

    @Override
    @DeleteMapping("/products")
    public boolean deleteProductsByCategoryId(@RequestParam("categoryId") int categoryId) {
        List<Product> products = Streamable.of(productRepository.findAll())
                .stream()
                .filter(x -> x.getCategoryId() == categoryId)
                .toList();
        productRepository.deleteAll(products);
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
