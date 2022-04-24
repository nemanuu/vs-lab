package hska.iwi.product;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called productRepository

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    // ...
}
