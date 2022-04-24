package hska.iwi.category;

import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called categoryRepository

public interface CategoryRepository extends CrudRepository<Category, Integer> {
    // ...
}
