package uoc.edu.easyorderbackend.service;

import uoc.edu.easyorderbackend.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> getOrdersFromTable(String restaurantId, String tableId);
    Order getLastOrderFromTable(String restaurantId, String tableId);
    Order changeState(String restaurantId, String tableId, String orderId, String newState);
}
