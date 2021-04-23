package uoc.edu.easyorderbackend.service;

import uoc.edu.easyorderbackend.model.Restaurant;
import uoc.edu.easyorderbackend.model.Table;

public interface RestaurantService {
    Restaurant createRestaurant(Restaurant restaurant);
    Restaurant getRestaurant(String uid);
}
