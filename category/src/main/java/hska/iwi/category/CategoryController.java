package hska.iwi.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController implements CategoryManager {

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
    public void deleteCategoryById(@RequestParam int id) {
        Category category = getCategory(id);
        if(category != null) {
            categoryRepository.delete(category);
            // TODO Product Microservice aufrufen, um Produkte zu löschen
        }
    }

    @Override
    @GetMapping("/categories")
    public List<Category> getCategories() {
        return Streamable.of(categoryRepository.findAll()).toList();
    }

    @Override
    @GetMapping("/categories/{id}")
    public Category getCategory(@RequestParam int id) {
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
