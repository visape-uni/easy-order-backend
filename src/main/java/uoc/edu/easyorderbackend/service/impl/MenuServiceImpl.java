package uoc.edu.easyorderbackend.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uoc.edu.easyorderbackend.DAO.impl.MenuDaoImpl;
import uoc.edu.easyorderbackend.exceptions.EasyOrderBackendException;
import uoc.edu.easyorderbackend.model.Menu;
import uoc.edu.easyorderbackend.service.MenuService;

import java.util.concurrent.ExecutionException;

@Service
public class MenuServiceImpl implements MenuService {

    private final static Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

    private MenuDaoImpl menuDao;

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

    @Autowired
    private void setMenuDao(MenuDaoImpl menuDao) {
        this.menuDao = menuDao;
    }
}