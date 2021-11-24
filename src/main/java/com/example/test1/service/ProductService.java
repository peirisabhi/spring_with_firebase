package com.example.test1.service;

import com.example.test1.entity.Product;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

/**
 * Created by Intellij.
 * Author: abhis
 * Date: 24/11/2021
 * Time: 9:37 pm
 */
@Service
public class ProductService {

    public String saveProduct(Product product) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();

        ApiFuture<WriteResult> product1 = firestore.collection("product")
                .document()
                .set(product);

        return product1.get().getUpdateTime().toString();
    }
}
