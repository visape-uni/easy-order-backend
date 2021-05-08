package uoc.edu.easyorderbackend.model;

import java.util.List;

public class Menu {
    private String uid;
    private List<Category> categories;

    public Menu() {
    }

    public Menu(String uid, List<Category> categories) {
        this.uid = uid;
        this.categories = categories;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
