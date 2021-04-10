package uoc.edu.easyorderbackend.service;

import com.google.firebase.auth.FirebaseAuthException;
import uoc.edu.easyorderbackend.exceptions.EasyOrderBackendException;
import uoc.edu.easyorderbackend.model.User;
import uoc.edu.easyorderbackend.model.UserAuth;


public interface UserService {
    User createUserWithEmailAndPassword(UserAuth userAuth);
}
