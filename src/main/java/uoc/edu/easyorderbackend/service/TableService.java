package uoc.edu.easyorderbackend.service;

import uoc.edu.easyorderbackend.model.Table;

import java.util.List;

public interface TableService {
    Table createTable(String restaurantId, Table table);
    List<Table> getTablesFromRestaurant(String restaurantId);
}
