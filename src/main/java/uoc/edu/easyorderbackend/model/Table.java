package uoc.edu.easyorderbackend.model;

public class Table {
    private String uid;
    private int tableNumber;
    private int capacity;

    public Table() {
    }

    public Table(String uid, int tableNumber, int capacity) {
        this.uid = uid;
        this.tableNumber = tableNumber;
        this.capacity = capacity;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
