package uoc.edu.easyorderbackend.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Order {
    private String uid;
    private Integer price;
    private String state;
    private Timestamp started;
    private List<OrderedDish> orderedDishes;

    public Order() {
        orderedDishes = new ArrayList<>();
    }

    public Order(String uid, Integer price, String state, List<OrderedDish> orderedDishes) {
        this.uid = uid;
        this.price = price;
        this.state = state;
        this.orderedDishes = orderedDishes;
        started = new Timestamp(System.currentTimeMillis());
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Timestamp getStarted() {
        return started;
    }

    public void setStarted(Timestamp started) {
        this.started = started;
    }

    public List<OrderedDish> getOrderedDishes() {
        return orderedDishes;
    }

    public void setOrderedDishes(List<OrderedDish> orderedDishes) {
        this.orderedDishes = orderedDishes;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();

        if (uid != null) map.put("uid", uid);
        if (price != null) map.put("price", price);
        if (state != null) map.put("state", state);
        if (started != null) map.put("started", started);
        if (orderedDishes != null && !orderedDishes.isEmpty()) map.put("orderedDishes", orderedDishes);

        return map;
    }
}
