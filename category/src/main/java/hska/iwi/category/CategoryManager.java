package hska.iwi.category;

import java.util.List;
import org.springframework.http.ResponseEntity;

public interface CategoryManager {

    ResponseEntity<List<Category>> getCategories(String name);

    ResponseEntity<Category> getCategory(int id);

    ResponseEntity<Void> addCategory(String name);

    ResponseEntity<Void> deleteCategoryById(int id);


}
