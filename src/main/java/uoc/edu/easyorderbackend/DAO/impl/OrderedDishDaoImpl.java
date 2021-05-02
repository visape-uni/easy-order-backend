package uoc.edu.easyorderbackend.DAO.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import uoc.edu.easyorderbackend.constants.DbEasyOrderConstants;
import uoc.edu.easyorderbackend.firebase.FirebaseInitialize;
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
            orderedDishes.add(document.toObject(OrderedDish.class));
        }
        orderedDishes.sort(Comparator.comparing(OrderedDish::getUid));
        logger.info("OrderedDishDao: All orderedDishes successfully obtained");

        return orderedDishes;
    }

    public String save(OrderedDish orderedDish) throws Exception {
        return null;
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
}
