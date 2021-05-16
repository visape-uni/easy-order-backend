package uoc.edu.easyorderbackend.service;

import uoc.edu.easyorderbackend.model.Table;

import java.util.List;

public interface TableService {
    Table createTable(String restaurantId, Table table);
    List<Table> getTablesFromRestaurant(String restaurantId);
    Table changeState(String restaurantId, String tableId, Table table);
    Table getTable(String restaurantId, String tableId);
}
