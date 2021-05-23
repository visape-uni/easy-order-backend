package uoc.edu.easyorderbackend.DAO.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
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
import uoc.edu.easyorderbackend.model.Restaurant;
import uoc.edu.easyorderbackend.model.Table;
import uoc.edu.easyorderbackend.model.Worker;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Repository
public class RestaurantDaoImpl implements Dao<Restaurant> {
    private final static Logger logger = LoggerFactory.getLogger(RestaurantDaoImpl.class);

    private TableDaoImpl tableDao;
    private UserDaoImpl userDao;

    private CollectionReference restaurantsColRef;

    public DocumentReference getReference(String id) {
        logger.info("RestaurantDao: getting reference");

        restaurantsColRef = getCollection();

        DocumentReference restaurantRef = restaurantsColRef.document(id);
        try {
            if (restaurantRef.get().get().exists()) {
                return restaurantRef;
            } else {
                throw new EasyOrderBackendException(HttpStatus.NOT_FOUND, "Owner not found");
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new EasyOrderBackendException(HttpStatus.NOT_FOUND, "Owner not found");
        }
    }

    public Optional<Restaurant> getFromRef(DocumentReference restaurantRef) throws ExecutionException, InterruptedException {
        logger.info("RestaurantDao: getting restaurant from Reference");
        Restaurant restaurant = null;
        if (restaurantRef != null) {
            ApiFuture<DocumentSnapshot> restaurantSnapshot = restaurantRef.get();
            restaurant = restaurantSnapshot.get().toObject(Restaurant.class);

            //Get tables from restaurant
            if (restaurant.getUid() != null) {
                List<Table> tables = tableDao.getAllFromRestaurant(restaurant.getUid());
                restaurant.setTables(tables);
            }
        }
        logger.info("RestaurantDao: restaurant successfully obtained");
        return Optional.ofNullable(restaurant);
    }

    @Override
    public Optional<Restaurant> get(String id) throws ExecutionException, InterruptedException {
        logger.info("RestaurantDao: getting restaurant");
        restaurantsColRef = getCollection();
        DocumentReference restaurantsDocRef = restaurantsColRef.document(id);
        ApiFuture<DocumentSnapshot> restaurantsSnapshot = restaurantsDocRef.get();
        Restaurant restaurant = restaurantsSnapshot.get().toObject(Restaurant.class);

        //Get tables from restaurant
        if (restaurant.getUid() != null) {
            List<Table> tables = tableDao.getAllFromRestaurant(restaurant.getUid());
            restaurant.setTables(tables);
        }

        restaurant.setOwner((Worker) userDao.getFromRef(restaurant.getOwnerRef()));

        logger.info("RestaurantDao: restaurant successfully obtained");
        return Optional.ofNullable(restaurant);
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
        String newRestaurantUid = null;

        if (StringUtils.isNotBlank(restaurant.getUid())) {
            DocumentReference restaurantDocRef = restaurantsColRef.document(restaurant.getUid());

            restaurantDocRef.set(restaurant.toMap());

            newRestaurantUid = restaurant.getUid();
            logger.info("RestaurantDao: restaurant saved with ID: " + newRestaurantUid);
        } else {
            DocumentReference restaurantDocRef = restaurantsColRef.document();

            restaurant.setUid(restaurantDocRef.getId());

            restaurantDocRef.set(restaurant.toMap());

            newRestaurantUid = restaurant.getUid();
            logger.info("RestaurantDao: restaurant saved with ID: " + newRestaurantUid);
        }

        return newRestaurantUid;
    }

    @Override
    public void update(Restaurant restaurant, Map<String, Object> updateMap) throws ExecutionException, InterruptedException {
        restaurantsColRef = getCollection();

        ApiFuture<WriteResult> future = restaurantsColRef.document(restaurant.getUid()).update(updateMap);
        logger.info("RestaurantDao: Restaurnat update: " + future.get());

    }

    @Override
    public void delete(Restaurant restaurant) {

    }

    private CollectionReference getCollection() {
        return FirebaseInitialize.getFirestoreDb()
                .collection(DbEasyOrderConstants.restaurantsCollection);
    }

    @Autowired
    public void setTableDao(TableDaoImpl tableDao) {
        this.tableDao = tableDao;
    }

    @Autowired
    public void setUserDao(UserDaoImpl userDao) {
        this.userDao = userDao;
    }
}
