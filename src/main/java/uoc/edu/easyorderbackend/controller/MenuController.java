package uoc.edu.easyorderbackend.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uoc.edu.easyorderbackend.constants.UrlEasyOrderConstants;
import uoc.edu.easyorderbackend.exceptions.EasyOrderBackendException;
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

    @Autowired
    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }
}
