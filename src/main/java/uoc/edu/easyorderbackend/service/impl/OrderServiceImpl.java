package uoc.edu.easyorderbackend.service.impl;

import com.google.cloud.firestore.DocumentReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uoc.edu.easyorderbackend.DAO.impl.DishDaoImpl;
import uoc.edu.easyorderbackend.DAO.impl.MenuDaoImpl;
import uoc.edu.easyorderbackend.DAO.impl.OrderDaoImpl;
import uoc.edu.easyorderbackend.exceptions.EasyOrderBackendException;
import uoc.edu.easyorderbackend.model.Menu;
import uoc.edu.easyorderbackend.model.Order;
import uoc.edu.easyorderbackend.service.OrderService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class OrderServiceImpl implements OrderService {

    private final static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private OrderDaoImpl orderDao;
    private DishDaoImpl dishDao;
    private MenuDaoImpl menuDao;

    @Override
    public List<Order> getOrdersFromTable(String restaurantId, String tableId) {
        logger.info("OrderService: getting all Orders from table");

        try {
            return orderDao.getAllFromTable(restaurantId, tableId);
        }catch (ExecutionException e) {
            throw new EasyOrderBackendException(HttpStatus.INTERNAL_SERVER_ERROR, "Backend server error: Process aborted");
        } catch (InterruptedException e) {
            throw new EasyOrderBackendException(HttpStatus.INTERNAL_SERVER_ERROR, "Backend server error: Process interrupted");
        }
    }

    @Override
    public Order getLastOrderFromTable(String restaurantId, String tableId) {
        logger.info("OrderService: getting last Order from table");
        try {
            return orderDao.getLastOrderFromTable(restaurantId, tableId);
        }catch (ExecutionException e) {
            throw new EasyOrderBackendException(HttpStatus.INTERNAL_SERVER_ERROR, "Backend server error: Process aborted");
        } catch (InterruptedException e) {
            throw new EasyOrderBackendException(HttpStatus.INTERNAL_SERVER_ERROR, "Backend server error: Process interrupted");
        }
    }

    @Override
    public Order changeState(String restaurantId, String tableId, String orderId, String newState) {
        logger.info("OrderService: Changing order state");

        try {
            return orderDao.changeState(restaurantId, tableId, orderId, newState);
        } catch (ExecutionException e) {
            throw new EasyOrderBackendException(HttpStatus.INTERNAL_SERVER_ERROR, "Backend server error: Process aborted");
        } catch (InterruptedException e) {
            throw new EasyOrderBackendException(HttpStatus.INTERNAL_SERVER_ERROR, "Backend server error: Process interrupted");
        }
    }

    @Override
    public Order saveOrder(String restaurantId, String tableId, Order order) {
        logger.info("OrderService: Saving order");

        Menu menu = null;
        try {
            menu = menuDao.getMenuFromRestaurant(restaurantId, false);
        } catch (ExecutionException e) {
            throw new EasyOrderBackendException(HttpStatus.INTERNAL_SERVER_ERROR, "Backend server error: Process aborted");
        } catch (InterruptedException e) {
            throw new EasyOrderBackendException(HttpStatus.INTERNAL_SERVER_ERROR, "Backend server error: Process interrupted");
        }

        String menuId = menu.getUid();
        order.getOrderedDishes().forEach(orderedDish -> {
            if (orderedDish != null && orderedDish.getDish() != null) {
                DocumentReference dishRef = dishDao.getReference(restaurantId, menuId, orderedDish.getCategoryId(), orderedDish.getDish().getUid());
                orderedDish.setDishRef(dishRef);
            }
        });

        String orderId = orderDao.saveToTable(restaurantId, tableId, order);
        order.setUid(orderId);
        return order;
    }

    @Autowired
    public void setMenuDao(MenuDaoImpl menuDao) {
        this.menuDao = menuDao;
    }
    @Autowired
    public void setDishDao(DishDaoImpl dishDao) {
        this.dishDao = dishDao;
    }
    @Autowired
    public void setOrderDao(OrderDaoImpl orderDao) {
        this.orderDao = orderDao;
    }
}
