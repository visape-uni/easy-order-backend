package uoc.edu.easyorderbackend.provider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import uoc.edu.easyorderbackend.model.FirebaseAuthenticationToken;
import uoc.edu.easyorderbackend.model.UserAuth;

@Component
public class FirebaseIdTokenAuthenticationProvider implements AuthenticationProvider {
    public static final Logger logger = LoggerFactory.getLogger(FirebaseIdTokenAuthenticationProvider.class);

    @Override
    public Authentication authenticate(Authentication authentication) throws BadCredentialsException {
        FirebaseAuthenticationToken token = (FirebaseAuthenticationToken) authentication;
        try {
            FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(token.getIdToken(), true);
            String uuid = firebaseToken.getUid();
            UserRecord userRecord = FirebaseAuth.getInstance().getUser(uuid);

            logger.info("FirebaseIdTokenAuthenticationProvider: User fetched, uid: {}", userRecord.getUid());
            return new UserAuth(userRecord);
        } catch (FirebaseAuthException e) {

            logger.error("FirebaseIdTokenAuthenticationProvider: {}", e.getMessage());
            if (e.getErrorCode().equals("id-token-revoked")) {
                throw new BadCredentialsException("User token has been revoked, please sign in again!");
            } else {
                throw new BadCredentialsException("Authentication failed, wrong token!");
            }
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(FirebaseAuthenticationToken.class);
    }

    public void validateToken(Authentication authentication) throws BadCredentialsException{
        FirebaseAuthenticationToken token = (FirebaseAuthenticationToken) authentication;
        try {
            logger.info("FirebaseIdTokenAuthenticationProvider: validating Token");
            FirebaseAuth.getInstance().verifyIdToken(token.getIdToken(), true);
        } catch (FirebaseAuthException e) {
            logger.error("FirebaseIdTokenAuthenticationProvider: {}", e.getMessage());
            if (e.getErrorCode().equals("id-token-revoked")) {
                throw new BadCredentialsException("User token has been revoked, please sign in again!");
            } else {
                throw new BadCredentialsException("Authentication failed, wrong token!");
            }
        }
    }
}
