package uoc.edu.easyorderbackend.model;

public class Dish {
    private String uid;
    private String name;
    private String description;
    private double price;
    private int calories;

    public Dish() {
    }

    public Dish(String uid, String name, String description, double price) {
        this.uid = uid;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Dish(String uid, String name, String description, double price, int calories) {
        this.uid = uid;
        this.name = name;
        this.description = description;
        this.price = price;
        this.calories = calories;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }
}
