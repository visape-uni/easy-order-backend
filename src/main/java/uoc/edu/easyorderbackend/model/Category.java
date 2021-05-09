package uoc.edu.easyorderbackend.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Category {
    private String uid;
    private String name;
    private String description;
    private List<Dish> dishes;

    public Category() {
    }

    public Category(String uid, String name, String description, ArrayList<Dish> dishes) {
        this.uid = uid;
        this.name = name;
        this.description = description;
        this.dishes = dishes;
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

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();

        if (uid != null) map.put("uid", uid);
        if (name != null) map.put("name", name);
        if (description != null) map.put("description", description);
        if (dishes != null && !dishes.isEmpty()) map.put("dishes", dishes);

        return map;
    }
}
