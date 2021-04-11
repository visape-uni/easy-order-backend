package uoc.edu.easyorderbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Worker extends User {
    private String idRestaurant;

    public Worker() {
    }

    public Worker(String uid, String username, String email, Boolean isEmailVerified, String idRestaurant) {
        super(uid, username, email, isEmailVerified, false);
        this.idRestaurant = idRestaurant;
    }

    public String getRestaurant() {
        return idRestaurant;
    }

    public void setRestaurant(String restaurant) {
        this.idRestaurant = idRestaurant;
    }
}
