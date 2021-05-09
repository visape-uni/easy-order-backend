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
import uoc.edu.easyorderbackend.model.Category;
import uoc.edu.easyorderbackend.model.Dish;
import uoc.edu.easyorderbackend.model.Menu;
import uoc.edu.easyorderbackend.service.MenuService;

@RestController
@RequestMapping(UrlEasyOrderConstants.menuUrl)
public class MenuController {
    private final static Logger logger = LoggerFactory.getLogger(MenuController.class);

    private MenuService menuService;

    @GetMapping(UrlEasyOrderConstants.getFromRestaurant)
    public ResponseEntity<Menu> getMenuFromRestaurant(@PathVariable String restaurantId) {
        logger.info("MenuController: Get Menu from restraunt");
        ResponseEntity<Menu> response;
        if (StringUtils.isNotBlank(restaurantId)) {
            Menu menu = menuService.getMenuFromRestaurant(restaurantId);
            response = new ResponseEntity<>(menu, HttpStatus.OK);
        } else {
            throw new EasyOrderBackendException(HttpStatus.BAD_REQUEST, "Invalid ID");
        }

        logger.info("MenuController: Giving response");
        return response;
    }

    @PostMapping(UrlEasyOrderConstants.createCategory)
    public ResponseEntity<Category> createCategory(@PathVariable String restaurantId, @RequestBody Category category) {
        logger.info("MenuController: create category");
        ResponseEntity<Category> response;
        if (StringUtils.isNotBlank(restaurantId)) {
            Category newCategory = menuService.createCategory(restaurantId, category);
            response = new ResponseEntity<>(newCategory, HttpStatus.OK);
        } else {
            throw new EasyOrderBackendException(HttpStatus.BAD_REQUEST, "Invalid ID");
        }

        logger.info("MenuController: Giving response");
        return response;
    }

    @PostMapping(UrlEasyOrderConstants.createDish)
    public ResponseEntity<Dish> createDish(@PathVariable String restaurantId, @PathVariable String categoryId, @RequestBody Dish dish) {
        logger.info("MenuController: create dish");
        ResponseEntity<Dish> response;
        if (StringUtils.isNotBlank(restaurantId) && StringUtils.isNotBlank(categoryId)) {
            Dish newDish = menuService.createDish(restaurantId, categoryId, dish);
            response = new ResponseEntity<>(newDish, HttpStatus.OK);
        } else {
            throw new EasyOrderBackendException(HttpStatus.BAD_REQUEST, "Invalid ID");
        }

        logger.info("MenuController: Giving response");
        return response;
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteDish(@PathVariable String restaurantId, @PathVariable String categoryId, @RequestBody String dishId) {
        logger.info("MenuController: delete dish");
        ResponseEntity<Boolean> response;
        if (StringUtils.isNotBlank(restaurantId) && StringUtils.isNotBlank(categoryId) && StringUtils.isNotBlank(dishId)) {
            boolean deleted = menuService.deleteDish(restaurantId, categoryId, dishId);
            response = new ResponseEntity<Boolean>(deleted, HttpStatus.OK);
        } else {
            throw new EasyOrderBackendException(HttpStatus.BAD_REQUEST, "Invalid ID");
        }

        logger.info("MenuController: Giving response");
        return response;
    }

        @Autowired
    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }
}
