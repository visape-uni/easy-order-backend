package uoc.edu.easyorderbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.cloud.firestore.DocumentReference;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OrderedDish {
    private String uid;
    private Integer quantity;
    private Integer totalPrice;
    private Dish dish;
    @JsonIgnore
    private DocumentReference dishRef;

    public OrderedDish() {
    }

    public OrderedDish(String uid, Integer quantity, Integer totalPrice, Dish dish) {
        this.uid = uid;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.dish = dish;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public DocumentReference getDishRef() {
        return dishRef;
    }

    public void setDishRef(DocumentReference dishRef) {
        this.dishRef = dishRef;
    }
}
