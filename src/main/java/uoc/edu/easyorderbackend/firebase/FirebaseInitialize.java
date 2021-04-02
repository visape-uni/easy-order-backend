package uoc.edu.easyorderbackend.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.FirestoreClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uoc.edu.easyorderbackend.EasyOrderBackendApplication;
import uoc.edu.easyorderbackend.constants.UrlEasyOrderConstants;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseInitialize {

    private final static Logger logger = LoggerFactory.getLogger(FirebaseInitialize.class);

    @PostConstruct
    private void initFirebase() {
        try {
            InputStream serviceAccount = EasyOrderBackendApplication.class.getClassLoader().getResourceAsStream("firebase_config.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(UrlEasyOrderConstants.databaseUrl)
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }

            logger.info("FirebaseInitialize: {} iniatilzed!", FirebaseApp.getInstance().getName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FirebaseAuth getFirebaseAuth() {
        logger.info("FirebaseInitialize: Getting firebaseAuth");
        return FirebaseAuth.getInstance();
    }

    public static Firestore getFirestoreDb() {
        logger.info("FirebaseInitialize: Getting firestore");
        return FirestoreClient.getFirestore();
    }
}
