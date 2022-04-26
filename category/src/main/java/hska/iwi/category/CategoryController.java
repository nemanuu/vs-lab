package hska.iwi.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class CategoryController implements CategoryManager {

    @Autowired
    private ProductWebClient productWebClient;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @PostMapping("/categories")
    public void addCategory(@RequestParam("name") String name) {
        Category category = new Category().setName(name);
        categoryRepository.save(category);
    }

    @Override
    @DeleteMapping("categories/{id}")
    public void deleteCategoryById(@PathVariable("id") int id) {
        Category category = getCategory(id);
        if(category != null) {
            productWebClient.deleteProducts(id);
            categoryRepository.delete(category);
        }
    }

    @Override
    @GetMapping("/categories")
    public List<Category> getCategories() {
        return Streamable.of(categoryRepository.findAll()).toList();
    }

    @Override
    @GetMapping("/categories/{id}")
    public Category getCategory(@PathVariable("id") int id) {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such product");
        }
        return categoryRepository.findById(id).get();
    }

    @Override
    @GetMapping("/categories/find")
    public Category getCategoryByName(@RequestParam("name") String name) {
        List<Category> categories = getCategories();
        return categories.stream()
                .filter(x -> x.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
    
}
