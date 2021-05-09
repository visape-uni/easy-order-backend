package uoc.edu.easyorderbackend.DAO.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uoc.edu.easyorderbackend.constants.DbEasyOrderConstants;
import uoc.edu.easyorderbackend.firebase.FirebaseInitialize;
import uoc.edu.easyorderbackend.model.Category;
import uoc.edu.easyorderbackend.model.Dish;
import uoc.edu.easyorderbackend.model.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class MenuDaoImpl {
    private final static Logger logger = LoggerFactory.getLogger(MenuDaoImpl.class);

    private CollectionReference menuColRef;
    private CategoryDaoImp categoryDao;

    public Menu getMenuFromRestaurant(String restaurantId) throws ExecutionException, InterruptedException {
        logger.info("MenuDao: Getting menu from restaurant");
        menuColRef = getCollection(restaurantId);
        Menu menu = new Menu();

        ApiFuture<QuerySnapshot> futureQuery = menuColRef.get();
        List<QueryDocumentSnapshot> documents = futureQuery.get().getDocuments();
        if (!documents.isEmpty()) {

            menu = documents.get(0).toObject(Menu.class);

            List<QueryDocumentSnapshot> categoriesDoc = documents.get(0).getReference().collection(DbEasyOrderConstants.categoriesCollection)
                    .get().get().getDocuments();
            List<Category> categories = new ArrayList<>();

            for (QueryDocumentSnapshot categoryDoc : categoriesDoc) {
                Category category = categoryDoc.toObject(Category.class);


                List<QueryDocumentSnapshot> dishesDoc = categoryDoc.getReference().collection(DbEasyOrderConstants.dishesCollection)
                        .get().get().getDocuments();

                List<Dish> dishes = new ArrayList<>();

                for (QueryDocumentSnapshot dishDoc : dishesDoc) {
                    Dish dish = dishDoc.toObject(Dish.class);
                    dishes.add(dish);
                }
                category.setDishes(dishes);
                categories.add(category);
            }
            menu.setCategories(categories);
            //menu.setCategories(categoryDao.getAllCategoriesFromMenu(restaurantId, menu.getUid()));
        }

        logger.info("MenuDao: Menu successfully obtained");
        return menu;
    }

    public Menu createMenu(String restaurantId, Menu menu) {
        logger.info("MenuDao: Saving Menu");
        menuColRef = getCollection(restaurantId);

        DocumentReference menuDocRef = menuColRef.document();

        menu.setUid(menuDocRef.getId());

        menuDocRef.set(menu.toMap());
        logger.info("MenuDao: Menu saved");
        return menu;
    }

    private CollectionReference getCollection(String restaurantId) {
        return FirebaseInitialize.getFirestoreDb()
                .collection(DbEasyOrderConstants.restaurantsCollection)
                .document(restaurantId)
                .collection(DbEasyOrderConstants.menuCollection);
    }

    @Autowired
    private void setCategoryDao(CategoryDaoImp categoryDao) {
        this.categoryDao = categoryDao;
    }
}
