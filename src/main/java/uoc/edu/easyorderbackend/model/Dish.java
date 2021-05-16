package uoc.edu.easyorderbackend.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Dish {
    private String uid;
    private String name;
    private String description;
    private Double price;

    //private Integer calories;

    public Dish() {
    }

    public Dish(String uid, String name, String description, Double price) {
        this.uid = uid;
        this.name = name;
        this.description = description;
        this.price = price;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    /*
    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }*/

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();

        if (uid != null) map.put("uid", uid);
        if (name != null) map.put("name", name);
        if (description != null) map.put("description", description);
        if (price != null) map.put("price", price);
        //if (calories != null) map.put("calories", calories);

        return map;
    }
}
