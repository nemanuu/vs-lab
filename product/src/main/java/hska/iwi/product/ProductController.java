package hska.iwi.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Product>> getProducts(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "value", required = false) String value,
            @RequestParam(value = "min-price", required = false) Double minPrice,
            @RequestParam(value = "max-price", required = false) Double maxPrice) {

        List<Product> products = Streamable.of(productRepository.findAll())
        .filter(p -> value == null || p.getName().contains(value))
        .filter(p -> name == null || p.getName().equals(name))
        .filter(p -> minPrice == null || p.getPrice() >= minPrice)
        .filter(p -> maxPrice == null || p.getPrice() <= maxPrice)
        .toList();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    private Product getProductByIdHelper(int id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such product");
        }
        return productRepository.findById(id).get();
    }

    @Override
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") int id) {
        Product product = getProductByIdHelper(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @Override
    @PostMapping("/products")
    public ResponseEntity<Void> addProduct(@RequestParam("name") String name, @RequestParam("price") double price, @RequestParam("categoryId") int categoryId, @RequestParam("details") String details) {

        if (!categoryWebClient.categoryExists(categoryId)) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "CategoryId does not exist.");
        }
        Product product = new Product().setName(name).setPrice(price).setDetails(details).setCategoryId(categoryId);
        productRepository.save(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    @DeleteMapping("/products")
    public ResponseEntity<Boolean> deleteProductsByCategoryId(@RequestParam("categoryId") int categoryId) {
        List<Product> products = Streamable.of(productRepository.findAll())
                .stream()
                .filter(x -> x.getCategoryId() == categoryId)
                .toList();
        productRepository.deleteAll(products);
        return new ResponseEntity<>(products.size() != 0, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable("id") int id) {
        Product product = getProductByIdHelper(id);

        if (product != null) {
            productRepository.delete(product);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
