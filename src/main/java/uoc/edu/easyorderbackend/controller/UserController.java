package uoc.edu.easyorderbackend.controller;

import com.google.firebase.auth.FirebaseAuthException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uoc.edu.easyorderbackend.constants.UrlEasyOrderConstants;
import uoc.edu.easyorderbackend.exceptions.EasyOrderBackendException;
import uoc.edu.easyorderbackend.model.User;
import uoc.edu.easyorderbackend.model.UserAuth;
import uoc.edu.easyorderbackend.service.UserService;

@RestController
@RequestMapping(UrlEasyOrderConstants.userUrl)
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @PostMapping(UrlEasyOrderConstants.createUrl)
    public ResponseEntity<User> createUser(@RequestBody UserAuth userAuth)  {
        logger.info("UserController: Create user");

        ResponseEntity<User> response;
            if (userAuth != null && StringUtils.isNotBlank(userAuth.getEmail()) && StringUtils.isNotBlank(userAuth.getPassword())
                    && StringUtils.isNotBlank(userAuth.getUsername())) {
                User newUser = userService.createUserWithEmailAndPassword(userAuth);
                response = new ResponseEntity<>(newUser, HttpStatus.OK);
            } else {
                throw new EasyOrderBackendException(HttpStatus.BAD_REQUEST, "Username, Email or Password is empty");
            }

        logger.info("UserController: Giving response");
        return response;
    }

    @GetMapping(UrlEasyOrderConstants.getUrl)
    public ResponseEntity<User> getUserProfile(@PathVariable String uid) {
        logger.info("UserController: GetUserProfile");
        ResponseEntity<User> response;
        if (uid != null) {
            User userProfile = userService.getUserProfile(uid);
            response = new ResponseEntity<>(userProfile, HttpStatus.OK);
        } else {
            throw new EasyOrderBackendException(HttpStatus.BAD_REQUEST, "UserId is empty");
        }

        logger.info("UserController: Giving response");
        return response;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
