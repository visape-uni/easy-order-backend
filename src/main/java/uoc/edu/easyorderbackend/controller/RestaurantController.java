package uoc.edu.easyorderbackend.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uoc.edu.easyorderbackend.constants.UrlEasyOrderConstants;
import uoc.edu.easyorderbackend.exceptions.EasyOrderBackendException;
import uoc.edu.easyorderbackend.model.Restaurant;
import uoc.edu.easyorderbackend.service.RestaurantService;

@RestController
@RequestMapping(UrlEasyOrderConstants.restaurantUrl)
public class RestaurantController {

    private final static Logger logger = LoggerFactory.getLogger(RestaurantController.class);

    private RestaurantService restaurantService;

    @PostMapping(UrlEasyOrderConstants.createUrl)
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        logger.info("RestaurantController: Create Restaurant");
        ResponseEntity<Restaurant> response;

        if (restaurant != null && StringUtils.isNotBlank(restaurant.getName())
            && StringUtils.isNotBlank(restaurant.getCity()) && StringUtils.isNotBlank(restaurant.getCountry())
                && StringUtils.isNotBlank(restaurant.getZipCode())) {

            Restaurant newRestaurant = restaurantService.createRestaurant(restaurant);
            response = new ResponseEntity<>(newRestaurant, HttpStatus.OK);
        } else {
            throw new EasyOrderBackendException(HttpStatus.BAD_REQUEST, "Any mandatory camp of the Restaurant is empty");
        }
        logger.info("RestaurantController: Giving response");
        return response;
    }

    @Autowired
    public void setRestaurantService(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

}