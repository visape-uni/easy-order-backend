package uoc.edu.easyorderbackend.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uoc.edu.easyorderbackend.DAO.impl.OrderDaoImpl;
import uoc.edu.easyorderbackend.DAO.impl.TableDaoImpl;
import uoc.edu.easyorderbackend.DAO.impl.UserDaoImpl;
import uoc.edu.easyorderbackend.constants.EasyOrderConstants;
import uoc.edu.easyorderbackend.exceptions.EasyOrderBackendException;
import uoc.edu.easyorderbackend.model.Order;
import uoc.edu.easyorderbackend.model.Table;
import uoc.edu.easyorderbackend.model.User;
import uoc.edu.easyorderbackend.service.TableService;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class TableServiceImpl implements TableService {

    private final static Logger logger = LoggerFactory.getLogger(RestaurantServiceImpl.class);

    private final static String TABLE_STATE_KEY = "state";
    private final static String TABLE_USERID_KEY = "userId";
    private final static String USER_TABLEID_KEY = "tableId";

    private TableDaoImpl tableDao;
    private OrderDaoImpl orderDao;
    private UserDaoImpl userDao;

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

    /* If Empty table and order is not paid -> Order state to Canceled
       Set tableId from UserEntity
     */
    @Override
    public Table changeState(String restaurantId, String tableId, Table table) {
        logger.info("RestaurantService: Changing table state");
        try {
            Optional<Table> optTable = tableDao.get(restaurantId, tableId);
            if (optTable.isPresent()) {
                Table newTable;
                Map<String, Object> updateMap = new HashMap<>();

                updateMap.put(TABLE_STATE_KEY, table.getState());
                updateMap.put(TABLE_USERID_KEY, table.getUserId());

                newTable = tableDao.update(restaurantId, tableId, updateMap);

                String newState = table.getState();

                if (newState.equals(EasyOrderConstants.occupiedTableState)) {
                    // Editar tableId del user
                    Map<String, Object> userUpdateMap = new HashMap<>();
                    userUpdateMap.put(USER_TABLEID_KEY, tableId);

                    User user = new User();
                    user.setUid(table.getUserId());
                    userDao.update(user, userUpdateMap);


                    String uid = String.valueOf(Calendar.getInstance().getTimeInMillis());
                    Order order = new Order(uid, 0.0, EasyOrderConstants.notPaidOrderState, new ArrayList<>());
                    orderDao.saveToTable(restaurantId, tableId, order);

                    List<Order> orderList = table.getOrderList() != null ? table.getOrderList() : new ArrayList<>();
                    orderList.add(order);
                    table.setOrderList(orderList);
                } else if (newState.equals(EasyOrderConstants.emptyTableState)) {
                    // Editar tableId del user
                    Map<String, Object> userUpdateMap = new HashMap<>();
                    userUpdateMap.put(USER_TABLEID_KEY, tableId);

                    String userId = optTable.get().getUserId();
                    User user = new User();
                    user.setUid(userId);
                    userDao.update(user, userUpdateMap);


                    Order order = orderDao.getLastOrderFromTable(restaurantId, tableId);
                    if (order != null && order.getState() != null
                            && order.getState().equals(EasyOrderConstants.notPaidOrderState)) {
                        orderDao.changeState(restaurantId, tableId, order.getUid(), EasyOrderConstants.canceledOrderState);
                    }

                } else {
                    throw new EasyOrderBackendException(HttpStatus.BAD_REQUEST, "Incorrect table state");
                }

                return newTable;
            } else {
                throw new EasyOrderBackendException(HttpStatus.NOT_FOUND, "Table not found");
            }
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
    public void setUserDao(UserDaoImpl userDao) {
        this.userDao = userDao;
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
