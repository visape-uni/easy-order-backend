package uoc.edu.easyorderbackend.DAO;

import com.google.cloud.firestore.CollectionReference;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Repository
public interface Dao<T> {

    Optional<T> get(String id) throws ExecutionException, InterruptedException;

    List<T> getAll();

    void save(T t) throws Exception;

    void update(T t, String[] params);

    void delete(T t);

}