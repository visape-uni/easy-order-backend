package uoc.edu.easyorderbackend.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.*;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Order {
    private String uid;
    private Integer price;
    private String state;
    private Date startedTime;
    private List<OrderedDish> orderedDishes;

    public Order() {
        orderedDishes = new ArrayList<>();
    }

    public Order(String uid, Integer price, String state, List<OrderedDish> orderedDishes) {
        this.uid = uid;
        this.price = price;
        this.state = state;
        this.orderedDishes = orderedDishes;
        startedTime = Calendar.getInstance().getTime();
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

    public Date getStartedTime() {
        return startedTime;
    }

    public void setStartedTime(Date startedTime) {
        this.startedTime = startedTime;
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
        if (startedTime != null) map.put("startedTime", startedTime);
        if (orderedDishes != null && !orderedDishes.isEmpty()) map.put("orderedDishes", orderedDishes);

        return map;
    }
}
