package uoc.edu.easyorderbackend.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Order {
    private String uid;
    private Integer price;
    private String state;
    private List<OrderedDish> orderedDishes;

    public Order() {
        orderedDishes = new ArrayList<>();
    }

    public Order(String uid, Integer price, String state, List<OrderedDish> orderedDishes) {
        this.uid = uid;
        this.price = price;
        this.state = state;
        this.orderedDishes = orderedDishes;
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

    public List<OrderedDish> getOrderedDishes() {
        return orderedDishes;
    }

    public void setOrderedDishes(List<OrderedDish> orderedDishes) {
        this.orderedDishes = orderedDishes;
    }
}
