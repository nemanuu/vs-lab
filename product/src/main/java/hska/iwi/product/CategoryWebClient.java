package hska.iwi.product;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CategoryWebClient {

    @Value("${categoryservice.url}")
    private String url;

    RestTemplate restTemplate = new RestTemplate();

    public boolean categoryExists(int categoryID) {

        String entityUrl = url + "/categories/" + categoryID;
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(entityUrl, String.class);
            return response.getStatusCode().is2xxSuccessful();
        }catch (Exception e){
            return false;
        }
    }
}
