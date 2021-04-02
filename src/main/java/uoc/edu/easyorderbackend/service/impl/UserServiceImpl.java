package uoc.edu.easyorderbackend.service.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uoc.edu.easyorderbackend.DAO.UserDaoImpl;
import uoc.edu.easyorderbackend.exceptions.EasyOrderException;
import uoc.edu.easyorderbackend.firebase.FirebaseInitialize;
import uoc.edu.easyorderbackend.model.User;
import uoc.edu.easyorderbackend.model.UserAuth;
import uoc.edu.easyorderbackend.service.UserService;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserDaoImpl userDao;

    @Override
    public UserAuth createUserWithEmailAndPassword(UserAuth userAuth) throws EasyOrderException, FirebaseAuthException {
        logger.info("FirebaseService: creating User with email and password");

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

        //Return new userAuth
        UserAuth newUserAuth = new UserAuth(userRecord);
        newUserAuth.setUsername(userAuth.getUsername());
        newUserAuth.setEmail(userAuth.getEmail());
        newUserAuth.setIsClient(userAuth.getIsClient());
        newUserAuth.setEmailVerified(false);

        //Create user in DB
        User newUser = new User(userRecord.getUid(), userAuth.getUsername(), userAuth.getEmail(), false, userAuth.getIsClient());
        userDao.save(newUser);

        logger.info("FirebaseService: User created");
        return newUserAuth;
    }

    @Autowired
    public void setUserDao(UserDaoImpl userDao) {
        this.userDao = userDao;
    }
}
