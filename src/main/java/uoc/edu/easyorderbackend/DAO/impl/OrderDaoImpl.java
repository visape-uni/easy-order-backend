package uoc.edu.easyorderbackend.DAO.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import uoc.edu.easyorderbackend.constants.DbEasyOrderConstants;
import uoc.edu.easyorderbackend.constants.EasyOrderConstants;
import uoc.edu.easyorderbackend.exceptions.EasyOrderBackendException;
import uoc.edu.easyorderbackend.firebase.FirebaseInitialize;
import uoc.edu.easyorderbackend.model.Order;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Repository
public class OrderDaoImpl {
    private final static Logger logger = LoggerFactory.getLogger(OrderDaoImpl.class);

    private CollectionReference orderColRef;
    private OrderedDishDaoImpl orderedDishDao;

    private final static String STATE_KEY = "state";
    private final static String ENDTIME_KEY = "endTime";

    public Optional<Order> get(String restaurantId, String tableId, String orderId) throws ExecutionException, InterruptedException {
        logger.info("OrderDao: Getting order");
        orderColRef = getCollection(restaurantId, tableId);
        DocumentReference orderDocRef = orderColRef.document(orderId);
        ApiFuture<DocumentSnapshot> orderSnapshot = orderDocRef.get();
        Order order = orderSnapshot.get().toObject(Order.class);

        logger.info("OrderDao: Order successfully obtained");

        return Optional.ofNullable(order);
    }

    public Order getLastOrderFromTable(String restaurantId, String tableId) throws ExecutionException, InterruptedException {
        logger.info("OrderDao: Getting last order");
        orderColRef = getCollection(restaurantId, tableId);

        Order order = new Order();

        ApiFuture<QuerySnapshot> futureQuery = orderColRef.orderBy("uid", Query.Direction.DESCENDING).limit(1).get();

        List<QueryDocumentSnapshot> documents = futureQuery.get().getDocuments();
        if (!documents.isEmpty()) {
            order = documents.get(0).toObject(Order.class);
            order.setOrderedDishes(orderedDishDao.getAllFromOrder(restaurantId, tableId, order.getUid()));
        }
        logger.info("OrderDao: Order successfully obatained");

        return order;
    }

    public List<Order> getAllFromTable(String restaurantId, String tableId) throws ExecutionException, InterruptedException {
        logger.info("OrderDao: Getting all orders");
        orderColRef = getCollection(restaurantId, tableId);

        List<Order> orders = new ArrayList<>();

        ApiFuture<QuerySnapshot> futureQuery = orderColRef.get();

        List<QueryDocumentSnapshot> documents = futureQuery.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            Order order = document.toObject(Order.class);
            order.setOrderedDishes(orderedDishDao.getAllFromOrder(restaurantId, tableId, order.getUid()));
            orders.add(document.toObject(Order.class));
        }
        orders.sort(Comparator.comparing(Order::getUid));
        logger.info("OrderDao: All orders successfully obatained");

        return orders;
    }

    public String saveToTable(String restaurantId, String tableId, Order order) {
        logger.info("OrderDao: Saving Order");
        orderColRef = getCollection(restaurantId, tableId);

        if (StringUtils.isNotBlank(restaurantId) && StringUtils.isNotBlank(tableId)
        && StringUtils.isNotBlank(order.getUid())) {
            DocumentReference orderDocRef = orderColRef.document(order.getUid());

            orderDocRef.set(order.toMap());
            logger.info("OrderDao: order from restaurant "+ restaurantId +" and table "+ tableId +" saved with ID: " + order.getUid());
            return order.getUid();
        } else {
            throw new EasyOrderBackendException(HttpStatus.BAD_REQUEST, "RestaurantId or TableId or OrderId is null");
        }
    }

    public Order changeState(String restaurantId, String tableId, String orderId, String newState) throws ExecutionException, InterruptedException {
        orderColRef = getCollection(restaurantId, tableId);
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put(STATE_KEY, newState);

        if (newState.equals(EasyOrderConstants.canceledOrderState)
                || newState.equals(EasyOrderConstants.paidOrderState)) {
            updateMap.put(ENDTIME_KEY, Calendar.getInstance().getTimeInMillis());
        }

        ApiFuture<WriteResult> future = orderColRef.document(orderId).update(updateMap);
        logger.info("OrderDao: State change result: " + future.get());

        Optional<Order> optOrder = this.get(restaurantId, tableId, orderId);
        if (optOrder.isPresent()) {
            return optOrder.get();
        } else {
            throw new EasyOrderBackendException(HttpStatus.NOT_FOUND, "Order does not exist");

        }

    }

    public void update(Order order, String[] params) {

    }

    public void delete(Order order) {

    }

    private CollectionReference getCollection(String restaurantId, String tableId) {
        return FirebaseInitialize.getFirestoreDb()
                .collection(DbEasyOrderConstants.restaurantsCollection)
                .document(restaurantId)
                .collection(DbEasyOrderConstants.tablesCollection)
                .document(tableId)
                .collection(DbEasyOrderConstants.ordersCollection);
    }

    @Autowired
    private void setOrderedDishDao(OrderedDishDaoImpl orderedDishDao) {
        this.orderedDishDao = orderedDishDao;
    }

}
