package com.example.test1.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Intellij.
 * Author: abhis
 * Date: 24/11/2021
 * Time: 9:32 pm
 */
@Service
public class FirebaseInitialization {

    @PostConstruct
    public void initialization(){

        FileInputStream serviceAccount =
                null;
        try {
            serviceAccount = new FileInputStream("./serviceAccountKey.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
