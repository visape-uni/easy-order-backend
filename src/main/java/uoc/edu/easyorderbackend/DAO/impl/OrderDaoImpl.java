package uoc.edu.easyorderbackend.DAO.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import uoc.edu.easyorderbackend.constants.DbEasyOrderConstants;
import uoc.edu.easyorderbackend.exceptions.EasyOrderBackendException;
import uoc.edu.easyorderbackend.firebase.FirebaseInitialize;
import uoc.edu.easyorderbackend.model.Order;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Repository
public class OrderDaoImpl {
    private final static Logger logger = LoggerFactory.getLogger(OrderDaoImpl.class);

    private CollectionReference orderColRef;
    private OrderedDishDaoImpl orderedDishDao;

    public Optional<Order> get(String id) throws ExecutionException, InterruptedException {
        return Optional.empty();
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

    private void setOrderedDishDao(OrderedDishDaoImpl orderedDishDao) {
        this.orderedDishDao = orderedDishDao;
    }
}
