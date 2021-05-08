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
import uoc.edu.easyorderbackend.model.Dish;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class DishDaoImpl {
    private final static Logger logger = LoggerFactory.getLogger(DishDaoImpl.class);

    private CollectionReference dishColRef;

    public List<Dish> getAllDishesFromCategory(String restaurantId, String menuId, String categoryId) throws ExecutionException, InterruptedException {
        logger.info("DishDao: Get all dishes from category");
        dishColRef = getCollection(restaurantId, menuId, categoryId);

        List<Dish> dishList = new ArrayList<>();
        ApiFuture<QuerySnapshot> futureQuery = dishColRef.get();

        List<QueryDocumentSnapshot> documents = futureQuery.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            dishList.add(document.toObject(Dish.class));
        }

        logger.info("DishDao: Dishes obtained successfully");
        return dishList;
    }

    private CollectionReference getCollection(String restaurantId, String menuId, String categoryId) {
        return FirebaseInitialize.getFirestoreDb()
                .collection(DbEasyOrderConstants.restaurantsCollection)
                .document(restaurantId)
                .collection(DbEasyOrderConstants.menuCollection)
                .document(menuId)
                .collection(DbEasyOrderConstants.categoriesCollection)
                .document(categoryId)
                .collection(DbEasyOrderConstants.dishesCollection);
    }
}
