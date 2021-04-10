package uoc.edu.easyorderbackend.service.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uoc.edu.easyorderbackend.DAO.impl.UserDaoImpl;
import uoc.edu.easyorderbackend.exceptions.EasyOrderBackendException;
import uoc.edu.easyorderbackend.firebase.FirebaseInitialize;
import uoc.edu.easyorderbackend.model.User;
import uoc.edu.easyorderbackend.model.UserAuth;
import uoc.edu.easyorderbackend.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class UserServiceImpl implements UserService {
    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserDaoImpl userDao;

    @Override
    public User createUserWithEmailAndPassword(UserAuth userAuth) {
        try {
            logger.info("UserService: creating User with email and password");

            //Create request
            UserRecord.CreateRequest createRequest = new UserRecord.CreateRequest();
            createRequest.setEmail(userAuth.getEmail())
                    .setPassword(userAuth.getPassword())
                    .setDisplayName(userAuth.getUsername());

            //Get firebaseAuth
            FirebaseAuth firebaseAuth = FirebaseInitialize.getFirebaseAuth();

            //Create user in Auth
            UserRecord userRecord = firebaseAuth.createUser(createRequest);

            //Claim isClient
            Map<String, Object> claims = new HashMap<>();
            claims.put("isClient", userAuth.getIsClient() != null ? userAuth.getIsClient() : true);
            firebaseAuth.setCustomUserClaims(userRecord.getUid(), claims);

            //Create user in DB
            User newUser = new User(userRecord.getUid(), userAuth.getUsername(), userAuth.getEmail(), false, userAuth.getIsClient());
            userDao.save(newUser);

            logger.info("UserService: User created");
            return newUser;
        } catch (FirebaseAuthException e) {
            logger.error("UserService: {}", e.getMessage());
            throw new EasyOrderBackendException(HttpStatus.UNAUTHORIZED , "Firebase error: " + e.getMessage());
        }
    }

    @Override
    public User getUserProfile(String uid) {
        try {
            Optional<User> optionalUser = userDao.get(uid);
            if (optionalUser.isPresent()) {
                return optionalUser.get();
            } else {
                throw new EasyOrderBackendException(HttpStatus.NOT_FOUND, "User not found");
            }
        } catch (ExecutionException e) {
            throw new EasyOrderBackendException(HttpStatus.INTERNAL_SERVER_ERROR, "Backend server error: Process aborted");
        } catch (InterruptedException e) {
            throw new EasyOrderBackendException(HttpStatus.INTERNAL_SERVER_ERROR, "Backend server error: Process interrupted");
        }
    }

    @Autowired
    public void setUserDao(UserDaoImpl userDao) {
        this.userDao = userDao;
    }
}
