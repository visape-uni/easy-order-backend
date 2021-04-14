package uoc.edu.easyorderbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.cloud.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Worker extends User {
    private Restaurant restaurant;
    private Boolean isOwner;
    @JsonIgnore // Avoid serialize DocumentReference in Response
    private DocumentReference restaurantRef;

    public Worker() {
    }

    public Worker(String uid, String username, String email, Boolean isEmailVerified, Restaurant restaurant, Boolean isOwner) {
        super(uid, username, email, isEmailVerified, false);
        this.restaurant = restaurant;
        this.isOwner = isOwner;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Boolean getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(Boolean isOwner) {
        this.isOwner = isOwner;
    }

    public DocumentReference getRestaurantRef() {
        return restaurantRef;
    }

    public void setRestaurantRef(DocumentReference restaurantRef) {
        this.restaurantRef = restaurantRef;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "user=" + super.toString() +
                "restaurant='" +(restaurant != null ? restaurant.toString() : "null") + '\'' +
                "isOwner='" + isOwner + '\'' +
                '}';
    }

    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<String, Object>();

        if (getUid() != null) map.put("uid", getUid());
        if (getUsername() != null) map.put("username", getUsername());
        if (getEmail() != null) map.put("email", getEmail());
        if (getIsClient() != null) map.put("isClient", getIsClient());
        if (getIsEmailVerified() != null) map.put("isEmailVerified", getIsEmailVerified());
        if (getRestaurantRef() != null) map.put("restaurantRef", getRestaurantRef());

        return map;
    }

}
