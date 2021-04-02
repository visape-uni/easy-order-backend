package uoc.edu.easyorderbackend.model;

import org.springframework.security.authentication.AbstractAuthenticationToken;


public class FirebaseAuthenticationToken extends AbstractAuthenticationToken {

    private final String idToken;

    public FirebaseAuthenticationToken(String idToken) {
        super(null);
        this.idToken = idToken;
    }

    public String getIdToken() {
        return idToken;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
