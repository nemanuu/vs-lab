package hska.iwi.eShopMaster.model.businessLogic.rest.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import hska.iwi.eShopMaster.model.businessLogic.dataobjects.Category;
import hska.iwi.eShopMaster.model.businessLogic.rest.CategoryService;

public class CategoryServiceImpl implements CategoryService {

    private final String BASE_URI = System.getenv("CATEGORYSERVICE_URL");
    private final WebClient webClient = WebClient.create(BASE_URI);

    private List<Category> categories = null;

    private void updateCategories() {
        try {
            Category[] objects = webClient
                .get()
                .uri("/categories")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Category[].class)
                .block();

            categories = Arrays.asList(objects);

        } catch (WebClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Category> getCategories() {
        if(categories == null) {
            updateCategories();
        }
        return categories != null ? categories : new ArrayList<>();
    }

    @Override
    public Category getCategory(int id) {
        Optional<Category> optCategory = getCategories()
                .stream()
                .filter(c -> c.getId() == id)
                .findFirst();
        return optCategory.isPresent() ? optCategory.get() : null;
    }

    @Override
    public Category getCategoryByName(String name) {
        try {
            Category[] objects = webClient
                .get()
                .uri("/categories?name=" + name)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Category[].class)
                .block();

            return objects.length != 0 ? objects[0] : null;

        } catch (WebClientException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void addCategory(String name) {
        Category category = new Category();
        category.setName(name);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String jsonString = objectMapper.writeValueAsString(category);
            webClient
                .post()
                .uri("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(jsonString)
                .retrieve()
                .bodyToMono(String.class)
                .block();

            updateCategories();
        } catch (WebClientException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delCategoryById(int id) {
        try {
            webClient
                .delete()
                .uri("/categories/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Category.class)
                .block();

            updateCategories();
        } catch (WebClientException e) {
            e.printStackTrace();
        }
    }

    
}
