package hska.iwi.product;


public class Product {
    public Product(String name, double price, String details) {
        this.name = name;
        this.price = price;
        this.details = details;
        this.id = (int) (Math.random() * 100);
    }

    private int id;

    private String name;

    private double price;

    private String details;

    public Product() {

    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDetails() {
        return details;
    }

    public int getId() {
        return id;
    }
}
