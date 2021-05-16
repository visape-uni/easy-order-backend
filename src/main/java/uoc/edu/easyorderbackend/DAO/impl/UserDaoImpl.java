package uoc.edu.easyorderbackend.DAO.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Repository
public class UserDaoImpl implements Dao<User> {
    private final static Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    private RestaurantDaoImpl restaurantDao;

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
                && userSnapshot.get().get("isClient") != null
                && (Boolean) userSnapshot.get().get("isClient")) {
            // isClient
            user = userSnapshot.get().toObject(Client.class);
        } else {
            // isWorker
            user = userSnapshot.get().toObject(Worker.class);
            DocumentReference restaurantRef = (DocumentReference) userSnapshot.get().get("restaurantRef");
            Optional<Restaurant> optionalRestaurant = restaurantDao.getFromRef(restaurantRef);
            if (optionalRestaurant.isPresent()) {
                ((Worker) user).setRestaurant(optionalRestaurant.get());
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

        // Set restaurant to null to avoid store in BD
        if (user instanceof Worker) {
            ((Worker) user).setRestaurant(null);
        }

        if (StringUtils.isNotBlank(user.getUid())) {
            DocumentReference userDocRef = usersColRef.document(user.getUid());
            //Write
            userDocRef.set(user.toMap());
            logger.info("UserDao: user saved");
            return user.getUid();
        } else {

            DocumentReference userDocRef = usersColRef.document();

            user.setUid(userDocRef.getId());

            userDocRef.set(user.toMap());

            logger.info("RestaurantDao: restaurant saved with ID: " + user.getUid());
            return user.getUid();
        }
    }

    @Override
    public void update(User user, Map<String, Object> updateMap) throws ExecutionException, InterruptedException {
        usersColRef = getCollection();

        ApiFuture<WriteResult> future = usersColRef.document(user.getUid()).update(updateMap);
        logger.info("UserDao: Updating user: " + future.get());

    }

    @Override
    public void delete(User user) {

    }

    private CollectionReference getCollection() {
        return FirebaseInitialize.getFirestoreDb()
                .collection(DbEasyOrderConstants.usersCollection);
    }

    @Autowired
    public void setRestaurantDao(RestaurantDaoImpl restaurantDao) {
        this.restaurantDao = restaurantDao;
    }
}
