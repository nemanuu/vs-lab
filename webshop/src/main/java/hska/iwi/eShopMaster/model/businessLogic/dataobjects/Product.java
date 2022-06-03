package hska.iwi.eShopMaster.model.businessLogic.dataobjects;


/**
 * This class contains details about products.
 */

public class Product {
	
    private Integer id;

    private Integer categoryId;

    private String name;

    private Double price;

    private String details;

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public Product setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public Product setPrice(Double price) {
        this.price = price;
        return this;
    }

    public Product setDetails(String details) {
        this.details = details;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public String getDetails() {
        return details;
    }

    public Integer getId() {
        return id;
	}
}
