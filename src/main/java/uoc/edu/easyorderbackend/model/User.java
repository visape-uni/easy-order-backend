package uoc.edu.easyorderbackend.model;

public class User {
    private String uid;
    private String username;
    private String email;
    private Boolean isEmailVerified;
    private Boolean isClient;

    public User() {
    }

    public User(String uid, String username, String email, Boolean isEmailVerified, Boolean isClient) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.isEmailVerified = isEmailVerified;
        this.isClient = isClient;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsEmailVerified() {
        return isEmailVerified;
    }

    public void setIsEmailVerified(Boolean emailVerified) {
        isEmailVerified = emailVerified;
    }

    public Boolean getIsClient() {
        return isClient;
    }

    public void setIsClient(Boolean client) {
        isClient = client;
    }
}
