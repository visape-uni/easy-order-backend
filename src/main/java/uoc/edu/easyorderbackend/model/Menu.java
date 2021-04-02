package uoc.edu.easyorderbackend.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu {
    private String uid;
    private float price;
    private Map<String, List<Dish>> categoryWithDishes;

    public Menu() {
        categoryWithDishes = new HashMap<>();
    }

    public Menu(String uid, Map<String, List<Dish>> categoryWithDishes) {
        this.uid = uid;
        this.categoryWithDishes = categoryWithDishes;
    }

    public Menu(String uid, float price, Map<String, List<Dish>> categoryWithDishes) {
        this.uid = uid;
        this.price = price;
        this.categoryWithDishes = categoryWithDishes;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Map<String, List<Dish>> getCategoryWithDishes() {
        return categoryWithDishes;
    }

    public void setCategoryWithDishes(Map<String, List<Dish>> categoryWithDishes) {
        this.categoryWithDishes = categoryWithDishes;
    }
}
