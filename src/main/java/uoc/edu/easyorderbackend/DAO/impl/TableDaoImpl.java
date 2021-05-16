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
import uoc.edu.easyorderbackend.model.Table;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Repository
public class TableDaoImpl {
    private final static Logger logger = LoggerFactory.getLogger(TableDaoImpl.class);

    private CollectionReference tablesColRef;

    private final static String STATE_KEY = "state";


    public Optional<Table> get(String restaurantId, String tableId) throws ExecutionException, InterruptedException {
        logger.info("TableDao: Getting table");
        tablesColRef = getCollection(restaurantId);
        DocumentReference tableDocRef = tablesColRef.document(tableId);
        ApiFuture<DocumentSnapshot> tableSnapshot = tableDocRef.get();
        Table table = tableSnapshot.get().toObject(Table.class);

        logger.info("TableDao: Table successfully obtained");

        return Optional.ofNullable(table);
    }

    public List<Table> getAllFromRestaurant(String restaurantId) throws ExecutionException, InterruptedException {
        logger.info("TableDao: Getting all tables");
        tablesColRef = getCollection(restaurantId);
        List<Table> tables = new ArrayList<>();

        // Async retrieve all documents
        ApiFuture<QuerySnapshot> futureQuery = tablesColRef.get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents = futureQuery.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            tables.add(document.toObject(Table.class));
        }
        Collections.sort(tables, Comparator.comparingInt(t -> Integer.parseInt(t.getUid())));
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

    public Table changeState(String restaurantId, String tableId, String newState) throws ExecutionException, InterruptedException {

        tablesColRef = getCollection(restaurantId);

        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put(STATE_KEY, newState);

        ApiFuture<WriteResult> future = tablesColRef.document(tableId).update(updateMap);
        logger.info("TableDao: State change result: " + future.get());

        Optional<Table> optTable = this.get(restaurantId, tableId);
        if (optTable.isPresent()) {
            return optTable.get();
        } else {
            throw new EasyOrderBackendException(HttpStatus.NOT_FOUND, "Table does not exist");
        }
    }


    public Table update(String restaurantId, String tableId, Map<String, Object> updateMap) throws ExecutionException, InterruptedException {
        tablesColRef = getCollection(restaurantId);

        ApiFuture<WriteResult> future = tablesColRef.document(tableId).update(updateMap);
        logger.info("TableDao: Table update: " + future.get());

        Optional<Table> optTable = this.get(restaurantId, tableId);
        if (optTable.isPresent()) {
            return optTable.get();
        } else {
            throw new EasyOrderBackendException(HttpStatus.NOT_FOUND, "Table does not exist");
        }
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
