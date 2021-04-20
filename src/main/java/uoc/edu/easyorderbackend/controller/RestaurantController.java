package uoc.edu.easyorderbackend.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uoc.edu.easyorderbackend.constants.UrlEasyOrderConstants;
import uoc.edu.easyorderbackend.exceptions.EasyOrderBackendException;
import uoc.edu.easyorderbackend.model.Restaurant;
import uoc.edu.easyorderbackend.service.RestaurantService;

@RestController
@RequestMapping(UrlEasyOrderConstants.restaurantUrl)
public class RestaurantController {

    private final static Logger logger = LoggerFactory.getLogger(RestaurantController.class);

    private RestaurantService restaurantService;

    /**
     *
     * @param restaurant
     * @throws EasyOrderBackendException If the owner of the restaurant
     * doesn't exists
     * @return A responseEntity with the restaurant created
     */

    @PostMapping(UrlEasyOrderConstants.createUrl)
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        logger.info("RestaurantController: Create Restaurant");
        ResponseEntity<Restaurant> response;

        if (restaurant != null && StringUtils.isNotBlank(restaurant.getName())
            && StringUtils.isNotBlank(restaurant.getCity()) && StringUtils.isNotBlank(restaurant.getCountry())
                && StringUtils.isNotBlank(restaurant.getZipCode()) && restaurant.getOwner() != null) {

            Restaurant newRestaurant = restaurantService.createRestaurant(restaurant);
            response = new ResponseEntity<>(newRestaurant, HttpStatus.OK);
        } else {
            throw new EasyOrderBackendException(HttpStatus.BAD_REQUEST, "Any mandatory camp of the Restaurant is empty");
        }
        logger.info("RestaurantController: Giving response");
        return response;
    }

    @GetMapping(UrlEasyOrderConstants.getUrl)
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable String uid) {
        logger.info("RestaurantController: GetRestaurantProfile");
        ResponseEntity<Restaurant> response;
        if (uid != null) {
            Restaurant restaurantProfile = restaurantService.getRestaurant(uid);
            response = new ResponseEntity<>(restaurantProfile, HttpStatus.OK);
        } else {
            throw new EasyOrderBackendException(HttpStatus.BAD_REQUEST, "RestaurantId is empty");
        }
        logger.info("RestaurantController: Giving response");
        logger.info("RestaurantController: logger");
        logger.info("RestaurantController: " + response.getBody());
        return response;
    }

    @Autowired
    public void setRestaurantService(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

}
