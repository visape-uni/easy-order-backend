package uoc.edu.easyorderbackend.DAO.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import uoc.edu.easyorderbackend.DAO.Dao;
import uoc.edu.easyorderbackend.constants.DbEasyOrderConstants;
import uoc.edu.easyorderbackend.exceptions.EasyOrderBackendException;
import uoc.edu.easyorderbackend.firebase.FirebaseInitialize;
import uoc.edu.easyorderbackend.model.Client;
import uoc.edu.easyorderbackend.model.Restaurant;
import uoc.edu.easyorderbackend.model.User;
import uoc.edu.easyorderbackend.model.Worker;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Repository
public class UserDaoImpl implements Dao<User> {
    private final static Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    private CollectionReference usersColRef;

    public DocumentReference getReference(String id) {
        logger.info("UserDao: getting reference");

        usersColRef = getCollection();

        DocumentReference userRef = usersColRef.document(id);
        try {
            if (userRef.get().get().exists()) {
                return userRef;
            } else {
                throw new EasyOrderBackendException(HttpStatus.NOT_FOUND, "Owner not found");
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new EasyOrderBackendException(HttpStatus.NOT_FOUND, "Owner not found");
        }
    }

    @Override
    public Optional<User> get(String id) throws ExecutionException, InterruptedException {
        logger.info("UserDao: getting user");
        usersColRef = getCollection();
        DocumentReference userDocRef = usersColRef.document(id);



        ApiFuture<DocumentSnapshot> userSnapshot = userDocRef.get();
        User user = null;
        if (userSnapshot.get() != null
                && userSnapshot.get().get("isClient") != null) {
            if (userSnapshot.get() != null
                    && userSnapshot.get().get("isClient") != null
                    && (Boolean) userSnapshot.get().get("isClient")) {
                // isClient
                user = userSnapshot.get().toObject(Client.class);
            } else {
                // isWorker
                user = userSnapshot.get().toObject(Worker.class);
                DocumentReference restaurantRef = (DocumentReference) userSnapshot.get().get("restaurantRef");
                if (restaurantRef != null) {
                    ApiFuture<DocumentSnapshot> restaurantSnapshot = restaurantRef.get();
                    Restaurant restaurant = restaurantSnapshot.get().toObject(Restaurant.class);
                    ((Worker) user).setRestaurant(restaurant);
                }
            }
        }

        logger.info("UserDao: user successfully obtained");
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public String save(User user) {
        logger.info("UserDao: Saving user");
        usersColRef = getCollection();
        if (StringUtils.isNotBlank(user.getUid())) {
            DocumentReference userDocRef = usersColRef.document(user.getUid());
            //Write
            userDocRef.set(user);
            logger.info("UserDao: user saved");
            return user.getUid();
        } else {

            DocumentReference userDocRef = usersColRef.document();

            user.setUid(userDocRef.getId());

            userDocRef.set(user);

            logger.info("RestaurantDao: restaurant saved with ID: " + user.getUid());
            return user.getUid();
        }
    }

    @Override
    public void update(User user, String[] params) {


    }

    @Override
    public void delete(User user) {

    }

    private CollectionReference getCollection() {
        return FirebaseInitialize.getFirestoreDb()
                .collection(DbEasyOrderConstants.usersCollection);
    }
}
