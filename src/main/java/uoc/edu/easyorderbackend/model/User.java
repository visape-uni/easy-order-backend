package uoc.edu.easyorderbackend.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return uid.equals(user.uid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid);
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", isEmailVerified=" + isEmailVerified +
                ", isClient=" + isClient +
                '}';
    }

    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<String, Object>();

        if (getUid() != null) map.put("uid", getUid());
        if (getUsername() != null) map.put("username", getUsername());
        if (getEmail() != null) map.put("email", getEmail());
        if (getIsClient() != null) map.put("isClient", getIsClient());
        if (getIsEmailVerified() != null) map.put("isEmailVerified", getIsEmailVerified());

        return map;
    }
}
