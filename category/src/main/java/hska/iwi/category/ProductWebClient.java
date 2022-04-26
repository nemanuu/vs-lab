package hska.iwi.category;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductWebClient {

    @Value("${productservice.url}")
    private String url;

    RestTemplate restTemplate = new RestTemplate();

    public void deleteProducts(int categoryID) {
        String entityUrl = url + "/products?categoryId=" + categoryID;
        restTemplate.delete(entityUrl);
    }
}
