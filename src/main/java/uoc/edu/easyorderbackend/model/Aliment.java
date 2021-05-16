package uoc.edu.easyorderbackend.model;

@Deprecated
public class Aliment {
    private String uid;
    private String name;

    public Aliment() {
    }

    public Aliment(String uid, String name) {
        this.uid = uid;
        this.name = name;
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
}
