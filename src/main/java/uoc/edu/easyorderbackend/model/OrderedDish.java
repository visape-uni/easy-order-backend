package uoc.edu.easyorderbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.cloud.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OrderedDish {
    private String uid;
    private Integer quantity;
    private Float totalPrice;
    private String categoryId;
    private Dish dish;
    @JsonIgnore
    private DocumentReference dishRef;

    public OrderedDish() {
    }

    public OrderedDish(String uid, Integer quantity, Float totalPrice, String categoryId, Dish dish) {
        this.uid = uid;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.categoryId = categoryId;
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

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();

        if (uid != null) map.put("uid", uid);
        if (quantity != null) map.put("quantity", quantity);
        if (totalPrice != null) map.put("totalPrice", totalPrice);
        if (categoryId != null) map.put("categoryId", categoryId);
        if (dishRef != null) map.put("dishRef", dishRef);

        return map;
    }
}
