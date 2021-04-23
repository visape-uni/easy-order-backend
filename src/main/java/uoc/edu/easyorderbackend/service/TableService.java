package uoc.edu.easyorderbackend.service;

import uoc.edu.easyorderbackend.model.Table;

public interface TableService {
    Table createTable(String restaurantId, Table table);
}
