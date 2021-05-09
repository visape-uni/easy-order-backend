package uoc.edu.easyorderbackend.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uoc.edu.easyorderbackend.DAO.impl.CategoryDaoImp;
import uoc.edu.easyorderbackend.DAO.impl.DishDaoImpl;
import uoc.edu.easyorderbackend.DAO.impl.MenuDaoImpl;
import uoc.edu.easyorderbackend.exceptions.EasyOrderBackendException;
import uoc.edu.easyorderbackend.model.Category;
import uoc.edu.easyorderbackend.model.Dish;
import uoc.edu.easyorderbackend.model.Menu;
import uoc.edu.easyorderbackend.service.MenuService;

import java.util.concurrent.ExecutionException;

@Service
public class MenuServiceImpl implements MenuService {

    private final static Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

    private MenuDaoImpl menuDao;
    private CategoryDaoImp categoryDao;
    private DishDaoImpl dishDao;

    @Override
    public Menu getMenuFromRestaurant(String restaurantId) {
        logger.info("MenuService: Getting menu from restaurant");
        try {
            return menuDao.getMenuFromRestaurant(restaurantId);
        }catch (ExecutionException e) {
            throw new EasyOrderBackendException(HttpStatus.INTERNAL_SERVER_ERROR, "Backend server error: Process aborted");
        } catch (InterruptedException e) {
            throw new EasyOrderBackendException(HttpStatus.INTERNAL_SERVER_ERROR, "Backend server error: Process interrupted");
        }
    }

    @Override
    public Category createCategory(String restaurantId, Category category) {
        logger.info("MenuService: Creating category");
        try {
            Menu menu =  menuDao.getMenuFromRestaurant(restaurantId);

            if (StringUtils.isBlank(menu.getUid())) {
                menu = menuDao.createMenu(restaurantId, menu);
            }

            String categoryId = categoryDao.createCategory(restaurantId, menu.getUid(), category);

            category.setUid(categoryId);
        }catch (ExecutionException e) {
            throw new EasyOrderBackendException(HttpStatus.INTERNAL_SERVER_ERROR, "Backend server error: Process aborted");
        } catch (InterruptedException e) {
            throw new EasyOrderBackendException(HttpStatus.INTERNAL_SERVER_ERROR, "Backend server error: Process interrupted");
        }

        return category;
    }

    @Override
    public Dish createDish(String restaurantId, String categoryId, Dish dish) {
        logger.info("MenuService: Creating dish");
        try {
            Menu menu =  menuDao.getMenuFromRestaurant(restaurantId);

            if (StringUtils.isBlank(menu.getUid())) {
                menu = menuDao.createMenu(restaurantId, menu);
            }

            String dishId = dishDao.createDish(restaurantId, menu.getUid(), categoryId, dish);

            dish.setUid(dishId);
        }catch (ExecutionException e) {
            throw new EasyOrderBackendException(HttpStatus.INTERNAL_SERVER_ERROR, "Backend server error: Process aborted");
        } catch (InterruptedException e) {
            throw new EasyOrderBackendException(HttpStatus.INTERNAL_SERVER_ERROR, "Backend server error: Process interrupted");
        }

        return dish;
    }

    @Override
    public boolean deleteDish(String restaurantId, String categoryId, String dishId) {
        logger.info("MenuService: deleting dish");
        try {
            Menu menu =  menuDao.getMenuFromRestaurant(restaurantId);

            if (StringUtils.isBlank(menu.getUid())) {
                return false;
            }
            dishDao.deleteDish(restaurantId, menu.getUid(), categoryId, dishId);
        }catch (ExecutionException e) {
            throw new EasyOrderBackendException(HttpStatus.INTERNAL_SERVER_ERROR, "Backend server error: Process aborted");
        } catch (InterruptedException e) {
            throw new EasyOrderBackendException(HttpStatus.INTERNAL_SERVER_ERROR, "Backend server error: Process interrupted");
        }
        return true;
    }

    @Autowired
    private void setCategoryDao(CategoryDaoImp categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Autowired
    private void setMenuDao(MenuDaoImpl menuDao) {
        this.menuDao = menuDao;
    }

    @Autowired
    private void setDishDao(DishDaoImpl dishDao) {
        this.dishDao = dishDao;
    }
}
