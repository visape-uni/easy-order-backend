package uoc.edu.easyorderbackend.DAO;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import uoc.edu.easyorderbackend.constants.DbEasyOrderConstants;
import uoc.edu.easyorderbackend.exceptions.EasyOrderException;
import uoc.edu.easyorderbackend.firebase.FirebaseInitialize;
import uoc.edu.easyorderbackend.model.User;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Repository
public class UserDaoImpl implements Dao<User>{
    private final static Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    private CollectionReference usersColRef;

    @Override
    public Optional<User> get(String id) throws ExecutionException, InterruptedException {
        logger.info("UserDao: getting user");
        usersColRef = getCollection();
        DocumentReference userDocRef = usersColRef.document(id);
        ApiFuture<DocumentSnapshot> userSnapshot = userDocRef.get();
        User user = userSnapshot.get().toObject(User.class);
        logger.info("UserDao: user successfully obtained");
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public void save(User user) throws EasyOrderException {
        logger.info("UserDao: Saving user");
        usersColRef = getCollection();
        if (StringUtils.isNotBlank(user.getUid())) {
            DocumentReference userDocRef = usersColRef.document(user.getUid());
            //Write
            userDocRef.set(user);
            logger.info("UserDao: user saved");
        } else {
            logger.error("UserDao: user not saved");
            throw new EasyOrderException("UserDao: user Id is not valid");
        }
    }

    @Override
    public void update(User user, String[] params) {

    }

    @Override
    public void delete(User user) {

    }

    private CollectionReference getCollection() {
        return FirebaseInitialize.getFirestoreDb().collection(DbEasyOrderConstants.usersCollection);
    }
}
