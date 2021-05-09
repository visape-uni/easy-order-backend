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
import uoc.edu.easyorderbackend.model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class CategoryDaoImp {
    private final static Logger logger = LoggerFactory.getLogger(CategoryDaoImp.class);

    private CollectionReference categoryColRef;
    private DishDaoImpl dishDao;

    public List<Category> getAllCategoriesFromMenu(String restaurantId, String menuId) throws ExecutionException, InterruptedException {
        logger.info("CategoryDao: Getting all categories");
        categoryColRef = getCollection(restaurantId, menuId);
        List<Category> categories = new ArrayList<>();

        ApiFuture<QuerySnapshot> futureQuery = categoryColRef.get();
        List<QueryDocumentSnapshot> documents = futureQuery.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            Category category = document.toObject(Category.class);
            category.setDishes(dishDao.getAllDishesFromCategory(restaurantId, menuId, category.getUid()));
            categories.add(category);
        }

        logger.info("CategoryDao: All categories successfully obtained");
        return categories;
    }

    public String createCategory(String restaurantId, String menuId, Category category) {
        logger.info("CategoryDAO: Saving category");
        categoryColRef = getCollection(restaurantId, menuId);

        if (StringUtils.isNotBlank(restaurantId) && StringUtils.isNotBlank(menuId) && StringUtils.isNotBlank(category.getUid())) {
            DocumentReference categoryDocRef = categoryColRef.document(category.getUid());
            categoryDocRef.set(category.toMap());
            logger.info("CategoryDAO: category from restaurant "+ restaurantId +" saved with ID: "+ category.getUid());
            return category.getUid();
        } else {
            throw new EasyOrderBackendException(HttpStatus.BAD_REQUEST, "RestaurantId or MenuId or CategoryId is null");
        }
    }

    private CollectionReference getCollection(String restaurantId, String menuId) {
        return FirebaseInitialize.getFirestoreDb()
                .collection(DbEasyOrderConstants.restaurantsCollection)
                .document(restaurantId)
                .collection(DbEasyOrderConstants.menuCollection)
                .document(menuId)
                .collection(DbEasyOrderConstants.categoriesCollection);
    }

    @Autowired
    private void setDishesDao(DishDaoImpl dishDao) {
        this.dishDao = dishDao;
    }
}
