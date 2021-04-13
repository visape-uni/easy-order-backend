package uoc.edu.easyorderbackend.DAO.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import uoc.edu.easyorderbackend.DAO.Dao;
import uoc.edu.easyorderbackend.constants.DbEasyOrderConstants;
import uoc.edu.easyorderbackend.exceptions.EasyOrderBackendException;
import uoc.edu.easyorderbackend.firebase.FirebaseInitialize;
import uoc.edu.easyorderbackend.model.Restaurant;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Repository
public class RestaurantDaoImpl implements Dao<Restaurant> {
    private final static Logger logger = LoggerFactory.getLogger(RestaurantDaoImpl.class);

    private CollectionReference restaurantsColRef;

    @Override
    public Optional<Restaurant> get(String id) throws ExecutionException, InterruptedException {
        logger.info("RestaurantDao: getting restaurant");
        restaurantsColRef = getCollection();
        DocumentReference restaurantsDocRef = restaurantsColRef.document(id);
        ApiFuture<DocumentSnapshot> restaurantsSnapshot = restaurantsDocRef.get();
        Restaurant restaurants = restaurantsSnapshot.get().toObject(Restaurant.class);
        logger.info("RestaurantDao: restaurant successfully obtained");
        return Optional.ofNullable(restaurants);
    }

    @Override
    public List<Restaurant> getAll() {
        return null;
    }

    @Override
    public String save(Restaurant restaurant) {
        logger.info("RestaurantDao: Saving restaurant");
        restaurantsColRef = getCollection();

        // Set Owner null to avoid store to DB
        restaurant.setOwner(null);

        if (StringUtils.isNotBlank(restaurant.getUid())) {
            DocumentReference restaurantDocRef = restaurantsColRef.document(restaurant.getUid());

            restaurantDocRef.set(restaurant);

            logger.info("RestaurantDao: restaurant saved with ID: " + restaurant.getUid());
            return restaurant.getUid();
        } else {
            DocumentReference restaurantDocRef = restaurantsColRef.document();

            restaurant.setUid(restaurantDocRef.getId());

            restaurantDocRef.set(restaurant.toMap());

            logger.info("RestaurantDao: restaurant saved with ID: " + restaurant.getUid());
            return restaurant.getUid();
        }
    }

    @Override
    public void update(Restaurant restaurant, String[] params) {

    }

    @Override
    public void delete(Restaurant restaurant) {

    }

    private CollectionReference getCollection() {
        return FirebaseInitialize.getFirestoreDb()
                .collection(DbEasyOrderConstants.restaurantsCollection);
    }
}
