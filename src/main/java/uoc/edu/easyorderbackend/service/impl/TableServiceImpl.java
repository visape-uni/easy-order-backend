package uoc.edu.easyorderbackend.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uoc.edu.easyorderbackend.DAO.impl.TableDaoImpl;
import uoc.edu.easyorderbackend.exceptions.EasyOrderBackendException;
import uoc.edu.easyorderbackend.model.Table;
import uoc.edu.easyorderbackend.service.TableService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class TableServiceImpl implements TableService {

    private final static Logger logger = LoggerFactory.getLogger(RestaurantServiceImpl.class);

    private TableDaoImpl tableDao;

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

    @Autowired
    public void setTableDao(TableDaoImpl tableDao) {
        this.tableDao = tableDao;
    }
}
