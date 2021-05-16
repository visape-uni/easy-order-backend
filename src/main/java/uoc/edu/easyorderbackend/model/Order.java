package uoc.edu.easyorderbackend.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.*;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Order {
    private String uid;
    private Float price;
    private String state;
    private Long startedTime;
    private Long endTime;
    private List<OrderedDish> orderedDishes;

    public Order() {
        orderedDishes = new ArrayList<>();
    }

    public Order(String uid, Float price, String state, List<OrderedDish> orderedDishes) {
        this.uid = uid;
        this.price = price;
        this.state = state;
        this.orderedDishes = orderedDishes;
        startedTime = Calendar.getInstance().getTimeInMillis();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getStartedTime() {
        return startedTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public void setStartedTime(Long startedTime) {
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
        if (endTime != null) map.put("endTime", endTime);

        return map;
    }
}
