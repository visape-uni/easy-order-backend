package uoc.edu.easyorderbackend.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Table {
    private String uid;
    private Integer capacity;
    private String state;
    private List<Order> orderList;
    private String userId;

    public Table() {
    }

    public Table(String uid, Integer capacity, String state, List<Order> orderList, String userId) {
        this.uid = uid;
        this.capacity = capacity;
        this.state = state;
        this.orderList = orderList;
        this.userId = userId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Table{" +
                "uid='" + uid + '\'' +
                ", capacity=" + capacity +
                ", state='" + state + '\'' +
                ", orderList=" + orderList +
                ", userId=" + userId +
                '}';
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();

        if (uid != null) map.put("uid", uid);
        if (capacity != null) map.put("capacity", capacity);
        if (state != null) map.put("state", state);
        if (userId != null) map.put("userId", userId);

        return map;
    }
}
