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
import uoc.edu.easyorderbackend.model.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Repository
public class TableDaoImpl {
    private final static Logger logger = LoggerFactory.getLogger(TableDaoImpl.class);

    private CollectionReference tablesColRef;


    public Optional<Table> get(String id) throws ExecutionException, InterruptedException {
        return Optional.empty();
    }

    public List<Table> getAllFromRestaurant(String restaurantId) throws ExecutionException, InterruptedException {
        logger.info("TableDao: Getting all tables");
        tablesColRef = getCollection(restaurantId);
        List<Table> tables = new ArrayList<>();

        // Async retrieve all documents
        ApiFuture<QuerySnapshot> future = tablesColRef.get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            tables.add(document.toObject(Table.class));
        }
        logger.info("TableDao: All tables successfully obtained");

        return tables;
    }

    public String saveToRestaurant(String restaurantId, Table table) {
        logger.info("TableDao: Saving table");
        tablesColRef = getCollection(restaurantId);

        if (StringUtils.isNotBlank(restaurantId) && StringUtils.isNotBlank(table.getUid())) {
            DocumentReference tableDocRef = tablesColRef.document(table.getUid());

            tableDocRef.set(table.toMap());
            logger.info("TableDao: table from restaurant "+ restaurantId +" saved with ID: "+ table.getUid());
            return table.getUid();
        } else {
            throw new EasyOrderBackendException(HttpStatus.BAD_REQUEST, "RestaurantId or TableId is null");
        }
    }



    public void update(Table table, String[] params) {

    }


    public void delete(Table table) {

    }

    private CollectionReference getCollection(String restaurantId) {
        return FirebaseInitialize.getFirestoreDb()
                .collection(DbEasyOrderConstants.restaurantsCollection)
                .document(restaurantId)
                .collection(DbEasyOrderConstants.tablesCollection);
    }
}
