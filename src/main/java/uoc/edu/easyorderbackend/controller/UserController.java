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
import uoc.edu.easyorderbackend.exceptions.EasyOrderException;
import uoc.edu.easyorderbackend.model.Message;
import uoc.edu.easyorderbackend.model.UserAuth;
import uoc.edu.easyorderbackend.service.UserService;

@RestController
@RequestMapping(UrlEasyOrderConstants.userUrl)
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @PostMapping(UrlEasyOrderConstants.createUserUrl)
    public ResponseEntity createUser(@RequestBody UserAuth userAuth) {
        logger.info("UserController: Create user");

        ResponseEntity response;
        try {
            if (userAuth != null && StringUtils.isNotBlank(userAuth.getEmail()) && StringUtils.isNotBlank(userAuth.getPassword())
                    && StringUtils.isNotBlank(userAuth.getUsername())) {
                UserAuth newUserAuth = userService.createUserWithEmailAndPassword(userAuth);
                response = new ResponseEntity(newUserAuth, HttpStatus.OK);
            } else {
                response = new ResponseEntity(new Message("Username, Email or Password is empty"), HttpStatus.BAD_REQUEST);
            }
        } catch (FirebaseAuthException e) {
            logger.error("UserController: {}", e.getMessage());
            response = new ResponseEntity(new Message("Firebase error: " + e.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (EasyOrderException e) {
            logger.error("UserController: {}", e.getMessage());
            response = new ResponseEntity(new Message("Internal error: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("UserController: Giving response");
        return response;
    }

    @Autowired
    public void setFirebaseService(UserService userService) {
        this.userService = userService;
    }
}
