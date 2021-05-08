package uoc.edu.easyorderbackend.service;

import uoc.edu.easyorderbackend.model.Menu;

public interface MenuService {
    Menu getMenuFromRestaurant(String restaurantId);
}
