package uoc.edu.easyorderbackend.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uoc.edu.easyorderbackend.DAO.impl.RestaurantDaoImpl;
import uoc.edu.easyorderbackend.model.Restaurant;
import uoc.edu.easyorderbackend.service.RestaurantService;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final static Logger logger = LoggerFactory.getLogger(RestaurantServiceImpl.class);

    private RestaurantDaoImpl restaurantDao;

    @Override
    public Restaurant createRestaurant(Restaurant restaurant) {
        logger.info("RestaurantService: creating restaurant");

        String id = restaurantDao.save(restaurant);

        restaurant.setId(id);

        logger.info("RestaurantService: Restaurant created");
        return restaurant;
    }

    @Autowired
    public void setRestaurantDao(RestaurantDaoImpl restaurantDao) {
        this.restaurantDao = restaurantDao;
    }
}
