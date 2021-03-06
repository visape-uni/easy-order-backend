package uoc.edu.easyorderbackend.DAO.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import uoc.edu.easyorderbackend.constants.DbEasyOrderConstants;
import uoc.edu.easyorderbackend.exceptions.EasyOrderBackendException;
import uoc.edu.easyorderbackend.firebase.FirebaseInitialize;
import uoc.edu.easyorderbackend.model.Dish;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Repository
public class DishDaoImpl {
    private final static Logger logger = LoggerFactory.getLogger(DishDaoImpl.class);

    private CollectionReference dishColRef;

    public DocumentReference getReference(String restaurantId, String menuId, String categoryId, String dishId) {
        logger.info("RestaurantDao: getting reference");

        dishColRef = getCollection(restaurantId, menuId, categoryId);

        DocumentReference dishRef = dishColRef.document(dishId);
        try {
            if (dishRef.get().get().exists()) {
                return dishRef;
            } else {
                throw new EasyOrderBackendException(HttpStatus.NOT_FOUND, "Dish not found");
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new EasyOrderBackendException(HttpStatus.NOT_FOUND, "Dish not found");
        }
    }

    public Optional<Dish> getFromRef(DocumentReference dishRef) throws ExecutionException, InterruptedException {
        logger.info("DishDao: getting dish from Reference");
        Dish dish = null;
        if (dishRef != null) {
            ApiFuture<DocumentSnapshot> dishSnapshot = dishRef.get();
            dish = dishSnapshot.get().toObject(Dish.class);
        }
        logger.info("DishDao: dish successfully obtained");
        return Optional.ofNullable(dish);
    }

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

    public String createDish(String restaurantId, String menuId, String categoryId, Dish dish) {
        logger.info("DishDAO: Saving dish");
        dishColRef = getCollection(restaurantId, menuId, categoryId);

        if (StringUtils.isNotBlank(restaurantId) && StringUtils.isNotBlank(menuId) && StringUtils.isNotBlank(categoryId)
            && StringUtils.isNotBlank(dish.getUid())) {
            DocumentReference dishDocRef = dishColRef.document(dish.getUid());
            dish.setUid(dishDocRef.getId());
            dishDocRef.set(dish.toMap());
            logger.info("DishDAO: dish from restaurant "+ restaurantId +" saved with ID: "+ dish.getUid());
            return dish.getUid();
        } else {
            throw new EasyOrderBackendException(HttpStatus.BAD_REQUEST, "RestaurantId or MenuId or CategoryId or DishId is null");
        }
    }

    public void deleteDish(String restaurantId, String menuId, String categoryId, String dishId) throws ExecutionException, InterruptedException {
        logger.info("DishDao: Deleting dish");
        dishColRef = getCollection(restaurantId, menuId, categoryId);
        if (StringUtils.isNotBlank(restaurantId) && StringUtils.isNotBlank(menuId) && StringUtils.isNotBlank(categoryId)) {
            DocumentReference dishDocRef = dishColRef.document(dishId);
            ApiFuture<WriteResult> writeResult = dishDocRef.delete();
            logger.info("DishDAO: dish from restaurant "+ restaurantId +" deleted "+ writeResult.get().getUpdateTime());
        } else {
            throw new EasyOrderBackendException(HttpStatus.BAD_REQUEST, "RestaurantId or MenuId or CategoryId is null");
        }
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
