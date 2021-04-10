package uoc.edu.easyorderbackend.model;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String id;
    private String name;
    private String street;
    private String city;
    private String zipCode;
    private String country;
    private String imageUrl;
    private List<Table> tables;
    private List<Menu> menus;
    private List<Worker> workers;

    public Restaurant() {
        tables = new ArrayList<>();
        menus = new ArrayList<>();
        workers = new ArrayList<>();
    }

    public Restaurant(String id, String name, String street, String city, String zipCode, String country) {
        this();
        this.id = id;
        this.name = name;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
    }

    public Restaurant(String id, String name, String street, String city, String zipCode, String country, String imageUrl, List<Table> tables, List<Menu> menus, List<Worker> workers) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
        this.imageUrl = imageUrl;
        this.tables = tables;
        this.menus = menus;
        this.workers = workers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
