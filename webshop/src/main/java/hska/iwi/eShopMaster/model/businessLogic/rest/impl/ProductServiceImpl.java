package hska.iwi.eShopMaster.model.businessLogic.rest.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import hska.iwi.eShopMaster.model.businessLogic.dataobjects.Product;
import hska.iwi.eShopMaster.model.businessLogic.dataobjects.ProductView;
import hska.iwi.eShopMaster.model.businessLogic.rest.CategoryService;
import hska.iwi.eShopMaster.model.businessLogic.rest.Factory;
import hska.iwi.eShopMaster.model.businessLogic.rest.ProductService;

public class ProductServiceImpl implements ProductService {

    private final String BASE_URI = System.getenv("PRODUCTSERVICE_URL");
    private final WebClient webClient = WebClient.create(BASE_URI);

    private CategoryService categoryService = Factory.getCategoryService();

    @Override
    public List<ProductView> getProducts() {
        try {
            Product[] objects = webClient
                .get()
                .uri("/products")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Product[].class)
                .block();

            return Arrays
                    .asList(objects)
                    .stream()
                    .map(p -> new ProductView(p, categoryService.getCategory(p.getCategoryId())))
                    .collect(Collectors.toList());


        } catch (WebClientException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public ProductView getProductById(int id) {
        try {
            Product object = webClient
                .get()
                .uri("/products/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Product.class)
                .block();

            return new ProductView(object, categoryService.getCategory(object.getCategoryId()));
        } catch (WebClientException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ProductView getProductByName(String name) {
        try {
            Product[] objects = webClient
                .get()
                .uri("/products?name=" + name)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Product[].class)
                .block();

            return objects.length != 0 ? new ProductView(objects[0], categoryService.getCategory(objects[0].getCategoryId())) : null;

        } catch (WebClientException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int addProduct(String name, double price, int categoryId, String details) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setCategoryId(categoryId);
        product.setDetails(details);
        
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String jsonString = objectMapper.writeValueAsString(product);
            return webClient
                .post()
                .uri("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(jsonString)
                .retrieve()
                .bodyToMono(Integer.class)
                .block()
                .intValue();

        } catch (WebClientException | JsonProcessingException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<ProductView> getProductsForSearchValues(String searchValue, Double searchMinPrice, Double searchMaxPrice) {
        try {
            String searchValueStr = searchValue == null ? "" : searchValue;
            String searchMinPriceStr = searchMinPrice == null ? "" : searchMinPrice.toString();
            String searchMaxPriceStr = searchMaxPrice == null ? "" : searchMaxPrice.toString();
            
            Product[] objects = webClient
                .get()
                .uri("/products?value=" + searchValueStr + "&min-price=" + searchMinPriceStr + "&max-price=" + searchMaxPriceStr)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Product[].class)
                .block();

            return Arrays
                .asList(objects)
                .stream()
                .map(p -> new ProductView(p, categoryService.getCategory(p.getCategoryId())))
                .collect(Collectors.toList());

        } catch (WebClientException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public boolean deleteProductsByCategoryId(int categoryId) {
        try {
            return webClient
                .delete()
                .uri("/products?categoryId=" + categoryId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block()
                .booleanValue();
        } catch (WebClientException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void deleteProductById(int id) {
        try {
            webClient
                .delete()
                .uri("/products/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        } catch (WebClientException e) {
            e.printStackTrace();
        }
    }
}
