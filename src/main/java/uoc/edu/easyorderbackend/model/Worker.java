package uoc.edu.easyorderbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Worker extends User {
    private Restaurant restaurant;

    public Worker() {
    }

    public Worker(String uid, String username, String email, Boolean isEmailVerified, Restaurant restaurant) {
        super(uid, username, email, isEmailVerified, false);
        this.restaurant = restaurant;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "user=" + super.toString() +
                "restaurant='" +(restaurant != null ? restaurant.toString() : "null") + '\'' +
                '}';
    }
}
