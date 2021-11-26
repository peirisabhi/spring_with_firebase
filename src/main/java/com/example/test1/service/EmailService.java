package com.example.test1.service;

import com.example.test1.entity.EmailDetails;
import com.example.test1.entity.Product;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class EmailService {

    public String saveEmailDetails(EmailDetails emailDetails) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();

        ApiFuture<WriteResult> product1 = firestore.collection("emails")
                .document()
                .set(emailDetails);

        return product1.get().getUpdateTime().toString();
    }

}
