package uoc.edu.easyorderbackend.model;

import java.util.ArrayList;
import java.util.List;

public class Dish {
    private String uid;
    private String name;
    private String description;
    private List<Aliment> aliments;
    private float price;
    private int calories;

    public Dish() {
        aliments = new ArrayList<>();
    }

    public Dish(String uid, String name, String description, List<Aliment> aliments, float price) {
        this.uid = uid;
        this.name = name;
        this.description = description;
        this.aliments = aliments;
        this.price = price;
    }

    public Dish(String uid, String name, String description, List<Aliment> aliments, float price, int calories) {
        this.uid = uid;
        this.name = name;
        this.description = description;
        this.aliments = aliments;
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

    public List<Aliment> getAliments() {
        return aliments;
    }

    public void setAliments(List<Aliment> aliments) {
        this.aliments = aliments;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }
}
