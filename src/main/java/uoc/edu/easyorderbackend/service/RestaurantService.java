package uoc.edu.easyorderbackend.service;

import uoc.edu.easyorderbackend.model.Restaurant;
import uoc.edu.easyorderbackend.model.Worker;

public interface RestaurantService {
    Restaurant createRestaurant(Restaurant restaurant);
    Restaurant getRestaurant(String uid);
    Worker addWorker(String restaurantId, String workerId);
    Boolean removeWorker(String restaurantId, String workerId);
}
