package hska.iwi.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

import javax.net.ssl.HttpsURLConnection;

@RestController
public class CategoryController implements CategoryManager {

    @Autowired
    private ProductWebClient productWebClient;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @PostMapping("/categories")
    public ResponseEntity<Void> addCategory(@RequestParam("name") String name) {
        Category category = new Category().setName(name);
        categoryRepository.save(category);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    @DeleteMapping("categories/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable("id") int id) {
        Category category = getCategoryHelper(id);
        if(category != null) {
            productWebClient.deleteProducts(id);
            categoryRepository.delete(category);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories(@RequestParam(value = "name", required = false) String name) {
        // show replication
        try {
            String hostName = InetAddress.getLocalHost().getHostName();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Hostname", hostName);

            List<Category> categories = Streamable.of(categoryRepository.findAll())
                .filter(c -> name == null || c.getName().equals(name))
                .toList();
            return new ResponseEntity<>(categories, headers, HttpStatus.OK);
        } catch (UnknownHostException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable("id") int id) {
        Category category = getCategoryHelper(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    private Category getCategoryHelper(int id) {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such product");
        }
        return categoryRepository.findById(id).get();
    }
}
