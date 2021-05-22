package uoc.edu.easyorderbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.cloud.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Restaurant {
    private String uid;
    private String name;
    private String street;
    private String city;
    private String zipCode;
    private String country;
    private String imageUrl;
    private List<Table> tables;
    private List<Menu> menus;
    private List<Worker> workers;
    private Worker owner;
    @JsonIgnore // Avoid serialize DocumentReference in Response
    private DocumentReference ownerRef;

    public Restaurant() {
        tables = new ArrayList<>();
        menus = new ArrayList<>();
        workers = new ArrayList<>();
    }

    public Restaurant(String uid, String name, String street, String city, String zipCode, String country) {
        this();
        this.uid = uid;
        this.name = name;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
    }

    public Restaurant(String uid, String name, String street, String city, String zipCode, String country, String imageUrl, List<Table> tables, List<Menu> menus, List<Worker> workers, Worker owner) {
        this.uid = uid;
        this.name = name;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
        this.imageUrl = imageUrl;
        this.tables = tables;
        this.menus = menus;
        this.workers = workers;
        this.owner = owner;
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

    public void addWorker(Worker worker) {
        this.workers.add(worker);
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

    public Worker getOwner() {
        return owner;
    }

    public void setOwner(Worker owner) {
        this.owner = owner;
    }

    public DocumentReference getOwnerRef() {
        return ownerRef;
    }

    public void setOwnerRef(DocumentReference ownerRef) {
        this.ownerRef = ownerRef;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", country='" + country + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", tables=" + tables +
                ", menus=" + menus +
                ", workers=" + workers +
                ", owner=" + (owner != null ? owner.toString() : "null") +
                '}';
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();

        if (uid != null) map.put("uid", uid);
        if (name != null) map.put("name", name);
        if (street != null) map.put("street", street);
        if (city != null) map.put("city", city);
        if (zipCode != null) map.put("zipCode", zipCode);
        if (country != null) map.put("country", country);
        if (imageUrl != null) map.put("imageUrl", imageUrl);
        if (tables != null && !tables.isEmpty()) map.put("tables", tables);
        if (menus != null && !menus.isEmpty()) map.put("menus", menus);
        if (workers != null && !workers.isEmpty()) map.put("workers", workers);
        if (owner != null) map.put("owner", owner);
        if (ownerRef != null) map.put("ownerRef", ownerRef);

        return map;
    }

    // TODO: fromMap(Map<String, Object>)


}
