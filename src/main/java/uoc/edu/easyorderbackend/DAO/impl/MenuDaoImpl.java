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
import uoc.edu.easyorderbackend.model.Menu;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class MenuDaoImpl {
    private final static Logger logger = LoggerFactory.getLogger(MenuDaoImpl.class);

    private CollectionReference menuColRef;
    private CategoryDaoImp categoryDao;

    public Menu getMenuFromRestaurant(String restaurantId, boolean getDishes) throws ExecutionException, InterruptedException {
        logger.info("MenuDao: Getting menu from restaurant");
        menuColRef = getCollection(restaurantId);
        Menu menu = new Menu();

        ApiFuture<QuerySnapshot> futureQuery = menuColRef.get();
        List<QueryDocumentSnapshot> documents = futureQuery.get().getDocuments();
        if (!documents.isEmpty()) {

            menu = documents.get(0).toObject(Menu.class);
            menu.setCategories(categoryDao.getAllCategoriesFromMenu(restaurantId, menu.getUid()));
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
