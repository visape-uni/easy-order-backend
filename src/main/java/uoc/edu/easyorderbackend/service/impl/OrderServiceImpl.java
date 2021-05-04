package uoc.edu.easyorderbackend.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uoc.edu.easyorderbackend.DAO.impl.OrderDaoImpl;
import uoc.edu.easyorderbackend.exceptions.EasyOrderBackendException;
import uoc.edu.easyorderbackend.model.Order;
import uoc.edu.easyorderbackend.service.OrderService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class OrderServiceImpl implements OrderService {

    private final static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private OrderDaoImpl orderDao;

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

    @Autowired
    public void setOrderDao(OrderDaoImpl orderDao) {
        this.orderDao = orderDao;
    }
}
