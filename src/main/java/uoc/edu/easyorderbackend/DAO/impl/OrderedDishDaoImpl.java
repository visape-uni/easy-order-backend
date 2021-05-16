package uoc.edu.easyorderbackend.DAO.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import uoc.edu.easyorderbackend.constants.DbEasyOrderConstants;
import uoc.edu.easyorderbackend.exceptions.EasyOrderBackendException;
import uoc.edu.easyorderbackend.firebase.FirebaseInitialize;
import uoc.edu.easyorderbackend.model.Dish;
import uoc.edu.easyorderbackend.model.OrderedDish;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Repository
public class OrderedDishDaoImpl {
    private final static Logger logger = LoggerFactory.getLogger(OrderedDishDaoImpl.class);

    private CollectionReference orderedDishColRef;
    private DishDaoImpl dishDao;

    public Optional<OrderedDish> get(String id) throws ExecutionException, InterruptedException {
        return Optional.empty();
    }

    public List<OrderedDish> getAllFromOrder(String restaurantId, String tableId, String orderId) throws ExecutionException, InterruptedException {
        logger.info("OrderedDishDao: Getting all orderedDishes");
        orderedDishColRef = getCollection(restaurantId, tableId, orderId);

        List<OrderedDish> orderedDishes = new ArrayList<>();

        ApiFuture<QuerySnapshot> futureQuery = orderedDishColRef.get();

        List<QueryDocumentSnapshot> documents = futureQuery.get().getDocuments();
        for(QueryDocumentSnapshot document : documents) {
            //TODO: GET DISH FROM REF
            OrderedDish orderedDish = document.toObject(OrderedDish.class);

            Optional<Dish> optionalDish = dishDao.getFromRef(orderedDish.getDishRef());
            if (optionalDish.isPresent()) {
                orderedDish.setDish(optionalDish.get());
            }
            orderedDishes.add(orderedDish);
        }
        orderedDishes.sort(Comparator.comparing(OrderedDish::getUid));
        logger.info("OrderedDishDao: All orderedDishes successfully obtained");

        return orderedDishes;
    }

    public String save(String restaurantId, String tableId, String orderId, OrderedDish orderedDish) throws Exception {
        logger.info("OrderedDishDao: Saving orderedDish");

        orderedDishColRef = getCollection(restaurantId, tableId, orderId);

        if (StringUtils.isNotBlank(restaurantId) && StringUtils.isNotBlank(tableId)
            && StringUtils.isNotBlank(orderId)) {
            DocumentReference orderedDishRef = orderedDishColRef.document(orderedDish.getUid());
            orderedDishRef.set(orderedDish.toMap());

            logger.info("OrderedDishDao: orderedDish saved");
            return orderedDish.getUid();
        } else {
            throw new EasyOrderBackendException(HttpStatus.BAD_REQUEST, "RestaurantId or TableId or OrderId is null");
        }
    }

    public void update(OrderedDish orderedDish, String[] params) {

    }

    public void delete(OrderedDish orderedDish) {

    }

    private CollectionReference getCollection(String restaurantId, String tableId, String orderId) {
        return FirebaseInitialize.getFirestoreDb()
                .collection(DbEasyOrderConstants.restaurantsCollection)
                .document(restaurantId)
                .collection(DbEasyOrderConstants.tablesCollection)
                .document(tableId)
                .collection(DbEasyOrderConstants.ordersCollection)
                .document(orderId)
                .collection(DbEasyOrderConstants.orderedDishesCollection);
    }

    @Autowired
    public void setDishDao(DishDaoImpl dishDao) {
        this.dishDao = dishDao;
    }
}
