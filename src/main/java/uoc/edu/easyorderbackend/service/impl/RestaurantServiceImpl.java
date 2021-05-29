package uoc.edu.easyorderbackend.service.impl;

import com.google.cloud.firestore.DocumentReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uoc.edu.easyorderbackend.DAO.impl.RestaurantDaoImpl;
import uoc.edu.easyorderbackend.DAO.impl.UserDaoImpl;
import uoc.edu.easyorderbackend.exceptions.EasyOrderBackendException;
import uoc.edu.easyorderbackend.model.Restaurant;
import uoc.edu.easyorderbackend.model.User;
import uoc.edu.easyorderbackend.model.Worker;
import uoc.edu.easyorderbackend.service.RestaurantService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final static Logger logger = LoggerFactory.getLogger(RestaurantServiceImpl.class);

    private final static String WORKERS_STATE_KEY = "workers";
    private final static String RESTAURANT_REF_STATE_KEY = "restaurantRef";

    private RestaurantDaoImpl restaurantDao;
    private UserDaoImpl userDao;


    @Override
    public Restaurant createRestaurant(Restaurant restaurant) {
        logger.info("RestaurantService: creating restaurant");

        // Get owner reference
        DocumentReference ownerRefence = userDao.getReference(restaurant.getOwner().getUid());

        if (ownerRefence != null) {
            // Set Owner ref before save in BD
            restaurant.setOwnerRef(ownerRefence);
            // Save owner id
            String ownerUid = restaurant.getOwner().getUid();
            // Save restaurant in DB (Owner is deleted)
            String id = restaurantDao.save(restaurant);

            // Save restaurantRef in UserEntity in BD
            try {
                Optional<User> userOptional = userDao.get(ownerUid);
                if (userOptional.isPresent()) {

                    // Set Owner to true
                    Worker worker = (Worker) userOptional.get();
                    worker.setIsOwner(true);

                    DocumentReference restaurantRef = restaurantDao.getReference(id);
                    worker.setRestaurantRef(restaurantRef);

                    userDao.save(worker);

                    restaurant.getOwner().setIsOwner(true);

                } else {
                    throw new EasyOrderBackendException(HttpStatus.NOT_FOUND, "Owner not found");
                }
            } catch (ExecutionException e) {
                throw new EasyOrderBackendException(HttpStatus.INTERNAL_SERVER_ERROR, "Backend server error: Process aborted");
            } catch (InterruptedException e) {
                throw new EasyOrderBackendException(HttpStatus.INTERNAL_SERVER_ERROR, "Backend server error: Process interrupted");
            }


            logger.info("RestaurantService: Restaurant created");
            return restaurant;

        } else {
            throw new EasyOrderBackendException(HttpStatus.NOT_FOUND, "Owner not found");
        }
    }

    @Override
    public Restaurant getRestaurant(String uid) {
        try {
            Optional<Restaurant> optionalRestaurant = restaurantDao.get(uid);
            if (optionalRestaurant.isPresent()) {
                return optionalRestaurant.get();
            } else {
                throw new EasyOrderBackendException(HttpStatus.NOT_FOUND, "Restaurant not found");
            }
        }  catch (ExecutionException e) {
            throw new EasyOrderBackendException(HttpStatus.INTERNAL_SERVER_ERROR, "Backend server error: Process aborted");
        } catch (InterruptedException e) {
            throw new EasyOrderBackendException(HttpStatus.INTERNAL_SERVER_ERROR, "Backend server error: Process interrupted");
        }
    }

    @Override
    public Worker addWorker(String restaurantId, String workerId) {
        try {
            Optional<User> userOptional = userDao.get(workerId);
            if (userOptional.isPresent()) {
                Optional<Restaurant> optionalRestaurant = restaurantDao.get(restaurantId);
                if (optionalRestaurant.isPresent()) {
                    Restaurant restaurant = optionalRestaurant.get();
                    Worker worker = (Worker) userOptional.get();

                    restaurant.addWorker(worker);

                    Map<String, Object> updateRestaurantMap = new HashMap<>();
                    updateRestaurantMap.put(WORKERS_STATE_KEY, restaurant.getWorkers());
                    restaurantDao.update(restaurant, updateRestaurantMap);

                    worker.setRestaurantRef(restaurantDao.getReference(restaurantId));

                    Map<String, Object> updateWorkerMap = new HashMap<>();
                    updateWorkerMap.put(RESTAURANT_REF_STATE_KEY , worker.getRestaurantRef());

                    userDao.update(worker, updateWorkerMap);

                    return worker;
                } else {
                    throw new EasyOrderBackendException(HttpStatus.NOT_FOUND, "Restaurant not found");
                }
            } else {
                throw new EasyOrderBackendException(HttpStatus.NOT_FOUND, "Worker doesn't exist");
            }
        }  catch (ExecutionException e) {
            throw new EasyOrderBackendException(HttpStatus.INTERNAL_SERVER_ERROR, "Backend server error: Process aborted");
        } catch (InterruptedException e) {
            throw new EasyOrderBackendException(HttpStatus.INTERNAL_SERVER_ERROR, "Backend server error: Process interrupted");
        }
    }

    @Override
    public Boolean removeWorker(String restaurantId, String workerId) {
        try {
            Optional<User> userOptional = userDao.get(workerId);
            if (userOptional.isPresent()) {
                Optional<Restaurant> optionalRestaurant = restaurantDao.get(restaurantId);
                if (optionalRestaurant.isPresent()) {
                    Restaurant restaurant = optionalRestaurant.get();
                    Worker worker = (Worker) userOptional.get();

                    restaurant.removeWorker(worker);

                    Map<String, Object> updateRestaurantMap = new HashMap<>();
                    updateRestaurantMap.put(WORKERS_STATE_KEY, restaurant.getWorkers());
                    restaurantDao.update(restaurant, updateRestaurantMap);

                    worker.setRestaurantRef(null);

                    Map<String, Object> updateWorkerMap = new HashMap<>();
                    updateWorkerMap.put(RESTAURANT_REF_STATE_KEY , worker.getRestaurantRef());

                    userDao.update(worker, updateWorkerMap);

                    return true;
                } else {
                    throw new EasyOrderBackendException(HttpStatus.NOT_FOUND, "Restaurant not found");
                }
            } else {
                throw new EasyOrderBackendException(HttpStatus.NOT_FOUND, "Worker doesn't exist");
            }
        }  catch (ExecutionException e) {
            throw new EasyOrderBackendException(HttpStatus.INTERNAL_SERVER_ERROR, "Backend server error: Process aborted");
        } catch (InterruptedException e) {
            throw new EasyOrderBackendException(HttpStatus.INTERNAL_SERVER_ERROR, "Backend server error: Process interrupted");
        }
    }

    @Autowired
    public void setRestaurantDao(RestaurantDaoImpl restaurantDao) {
        this.restaurantDao = restaurantDao;
    }

    @Autowired
    public void setUserDao(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

}
