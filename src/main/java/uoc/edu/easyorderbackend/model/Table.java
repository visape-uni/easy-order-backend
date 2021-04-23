package uoc.edu.easyorderbackend.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Table {
    private String uid;
    private Integer capacity;
    private String state;

    public Table() {
    }

    public Table(String uid, Integer capacity, String state) {
        this.uid = uid;
        this.capacity = capacity;
        this.state = state;
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

    @Override
    public String toString() {
        return "Table{" +
                "uid='" + uid + '\'' +
                ", capacity=" + capacity +
                ", state='" + state + '\'' +
                '}';
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();

        if (uid != null) map.put("uid", uid);
        if (capacity != null) map.put("capacity", capacity);
        if (state != null) map.put("state", state);

        return map;
    }
}
