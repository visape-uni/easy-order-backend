package uoc.edu.easyorderbackend.model;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String uid;
    private String name;
    private List<Table> tables;
    private List<Menu> menus;
    private List<Worker> workers;

    public Restaurant() {
        tables = new ArrayList<>();
        menus = new ArrayList<>();
        workers = new ArrayList<>();
    }

    public Restaurant(String uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    public Restaurant(String uid, String name, List<Table> tables, List<Menu> menus, List<Worker> workers) {
        this.uid = uid;
        this.name = name;
        this.tables = tables;
        this.menus = menus;
        this.workers = workers;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }
}
