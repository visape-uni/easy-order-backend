package uoc.edu.easyorderbackend.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uoc.edu.easyorderbackend.DAO.impl.TableDaoImpl;
import uoc.edu.easyorderbackend.model.Table;
import uoc.edu.easyorderbackend.service.TableService;

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

    @Autowired
    public void setTableDao(TableDaoImpl tableDao) {
        this.tableDao = tableDao;
    }
}
