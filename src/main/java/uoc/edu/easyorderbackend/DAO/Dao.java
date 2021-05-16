package uoc.edu.easyorderbackend.DAO;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Repository
public interface Dao<T> {

    Optional<T> get(String id) throws ExecutionException, InterruptedException;

    List<T> getAll();

    String save(T t) throws Exception;

    void update(T t, Map<String, Object> updateMap) throws ExecutionException, InterruptedException;

    void delete(T t);

}