package uoc.edu.easyorderbackend.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uoc.edu.easyorderbackend.constants.EasyOrderConstants;
import uoc.edu.easyorderbackend.constants.UrlEasyOrderConstants;
import uoc.edu.easyorderbackend.exceptions.EasyOrderBackendException;
import uoc.edu.easyorderbackend.model.Order;
import uoc.edu.easyorderbackend.service.OrderService;

import java.util.List;

@RestController
@RequestMapping(UrlEasyOrderConstants.orderUrl)
public class OrderController {
    private final static Logger logger = LoggerFactory.getLogger(OrderController.class);

    private OrderService orderService;

    @GetMapping(UrlEasyOrderConstants.getAllOrders)
    public ResponseEntity<List<Order>> getAllOrders(@PathVariable String restaurantId, @PathVariable String tableId) {
        logger.info("OrderController: get all orders from table");
        ResponseEntity<List<Order>> response;

        if (StringUtils.isNotBlank(restaurantId)
        && StringUtils.isNotBlank(tableId)) {
            List<Order> orderList = orderService.getOrdersFromTable(restaurantId, tableId);
            response = new ResponseEntity<>(orderList, HttpStatus.OK);
        } else {
            throw new EasyOrderBackendException(HttpStatus.BAD_REQUEST, "Invalid ID");
        }

        logger.info("OrderController: Giving response");
        return response;
    }

    @GetMapping(UrlEasyOrderConstants.getLastOrder)
    public ResponseEntity<Order> getLastOrder(@PathVariable String restaurantId, @PathVariable String tableId) {
        logger.info("OrderController: Get last order from table");
        ResponseEntity<Order> response;

        if (StringUtils.isNotBlank(restaurantId)
                && StringUtils.isNotBlank(tableId)) {
            Order order = orderService.getLastOrderFromTable(restaurantId, tableId);
            response = new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            throw new EasyOrderBackendException(HttpStatus.BAD_REQUEST, "Invalid ID");
        }

        logger.info("OrderController: Giving response");
        return response;

    }

    @PostMapping(UrlEasyOrderConstants.saveOrder)
    public ResponseEntity<Order> saveOrder(@PathVariable String restaurantId, @PathVariable String tableId, @RequestBody Order order) {
        logger.info("OrderController: Save order");
        ResponseEntity<Order> response;

        if (StringUtils.isNotBlank(restaurantId)
                && StringUtils.isNotBlank(tableId)) {
            Order newOrder = orderService.saveOrder(restaurantId, tableId, order);
            response = new ResponseEntity<>(newOrder, HttpStatus.OK);
        } else {
            throw new EasyOrderBackendException(HttpStatus.BAD_REQUEST, "Invalid ID");
        }

        logger.info("OrderController: Giving response");
        return response;
    }

    @PutMapping(UrlEasyOrderConstants.changeOrderState)
    public ResponseEntity<Order> changeOrderState(@PathVariable String restaurantId, @PathVariable String tableId, @PathVariable String orderId, @RequestBody Order order) {
        logger.info("OrderController: Change order state");
        ResponseEntity<Order> response;

        String newState = order.getState();

        if (StringUtils.isNotBlank(newState) && correctState(newState)) {
            Order newOrder = orderService.changeState(restaurantId, tableId, orderId, newState);
            response = new ResponseEntity<>(newOrder, HttpStatus.OK);
        } else {
            throw new EasyOrderBackendException(HttpStatus.BAD_REQUEST, "Invalid State");
        }

        logger.info("OrderController: Giving response");
        return response;
    }

    private boolean correctState(String newState) {
        return (newState.equals(EasyOrderConstants.canceledOrderState)
                || newState.equals(EasyOrderConstants.paidOrderState)
                || newState.equals(EasyOrderConstants.notPaidOrderState));
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

}
