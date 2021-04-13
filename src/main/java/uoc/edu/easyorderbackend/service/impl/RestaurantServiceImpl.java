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
import uoc.edu.easyorderbackend.service.RestaurantService;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final static Logger logger = LoggerFactory.getLogger(RestaurantServiceImpl.class);

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
            // Save restaurant in DB
            String id = restaurantDao.save(restaurant);

            //TODO: SAVE RESTAURANT REF TO USER

            logger.info("RestaurantService: Restaurant created");
            return restaurant;

        } else {
            throw new EasyOrderBackendException(HttpStatus.NOT_FOUND, "Owner not found");
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
