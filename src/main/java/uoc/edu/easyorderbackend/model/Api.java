package uoc.edu.easyorderbackend.model;


public class Api {

    private String id;
    private String title;

    public Api(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
