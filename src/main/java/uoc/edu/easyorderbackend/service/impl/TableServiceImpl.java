package uoc.edu.easyorderbackend.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uoc.edu.easyorderbackend.DAO.impl.OrderDaoImpl;
import uoc.edu.easyorderbackend.DAO.impl.TableDaoImpl;
import uoc.edu.easyorderbackend.constants.EasyOrderConstants;
import uoc.edu.easyorderbackend.exceptions.EasyOrderBackendException;
import uoc.edu.easyorderbackend.model.Order;
import uoc.edu.easyorderbackend.model.Table;
import uoc.edu.easyorderbackend.service.TableService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class TableServiceImpl implements TableService {

    private final static Logger logger = LoggerFactory.getLogger(RestaurantServiceImpl.class);

    private TableDaoImpl tableDao;
    private OrderDaoImpl orderDao;

    @Override
    public Table createTable(String restaurantId, Table table) {
        logger.info("RestaurantService: creating table");

        tableDao.saveToRestaurant(restaurantId, table);

        return table;
    }

    @Override
    public List<Table> getTablesFromRestaurant(String restaurantId) {
        logger.info("RestaurantService: getting all Tables from restaurant");

        try {
            return tableDao.getAllFromRestaurant(restaurantId);
        }  catch (ExecutionException e) {
            throw new EasyOrderBackendException(HttpStatus.INTERNAL_SERVER_ERROR, "Backend server error: Process aborted");
        } catch (InterruptedException e) {
            throw new EasyOrderBackendException(HttpStatus.INTERNAL_SERVER_ERROR, "Backend server error: Process interrupted");
        }
    }

    @Override
    public Table changeState(String restaurantId, String tableId, String newState) {
        logger.info("RestaurantService: Changing table state");
        try {
            Table table =  tableDao.changeState(restaurantId, tableId, newState);

            if (newState.equals(EasyOrderConstants.occupiedTableState)) {
                String uid = String.valueOf(Calendar.getInstance().getTimeInMillis());
                Order order = new Order(uid, 0, EasyOrderConstants.notPaidOrderState, new ArrayList<>());
                orderDao.saveToTable(restaurantId, tableId, order);

                List<Order> orderList = table.getOrderList() != null ? table.getOrderList() : new ArrayList<>();
                orderList.add(order);
                table.setOrderList(orderList);
            }

            return table;
        } catch (ExecutionException e) {
            throw new EasyOrderBackendException(HttpStatus.INTERNAL_SERVER_ERROR, "Backend server error: Process aborted");
        } catch (InterruptedException e) {
            throw new EasyOrderBackendException(HttpStatus.INTERNAL_SERVER_ERROR, "Backend server error: Process interrupted");
        }
    }

    @Override
    public Table getTable(String restaurantId, String tableId) {
        logger.info("RestaurantService: getting table from restaurant");

        try {
            Optional<Table> optionalTable = tableDao.get(restaurantId, tableId);
            if (optionalTable.isPresent()) {
                return optionalTable.get();
            } else {
                throw new EasyOrderBackendException(HttpStatus.NOT_FOUND, "Table not found");
            }
         }  catch (ExecutionException e) {
            throw new EasyOrderBackendException(HttpStatus.INTERNAL_SERVER_ERROR, "Backend server error: Process aborted");
        } catch (InterruptedException e) {
            throw new EasyOrderBackendException(HttpStatus.INTERNAL_SERVER_ERROR, "Backend server error: Process interrupted");
        }
    }

    @Autowired
    public void setTableDao(TableDaoImpl tableDao) {
        this.tableDao = tableDao;
    }

    @Autowired
    public void setOrderDao(OrderDaoImpl orderDao) {
        this.orderDao = orderDao;
    }
}
