package uoc.edu.easyorderbackend.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

}
