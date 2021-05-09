package uoc.edu.easyorderbackend.service;

import uoc.edu.easyorderbackend.model.Category;
import uoc.edu.easyorderbackend.model.Menu;

public interface MenuService {
    Menu getMenuFromRestaurant(String restaurantId);
    Category createCategory(String restaurantId, Category category);
}
