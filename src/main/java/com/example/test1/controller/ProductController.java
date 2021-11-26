package com.example.test1.controller;

import com.example.test1.entity.Product;
import com.example.test1.handlers.EmailHandler;
import com.example.test1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * Created by Intellij.
 * Author: abhis
 * Date: 24/11/2021
 * Time: 9:41 pm
 */
@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    EmailHandler emailHandler;

    @Autowired
    private ApplicationContext applicationContext;

    @PostMapping("product")
    public String saveProduct() throws ExecutionException, InterruptedException {

        String a = productService.saveProduct(new Product(2, "b"));

        return a;
    }

    @GetMapping
    public String test(){

//        EmailHandler classeTaskRunn = applicationContext.getBean(EmailHandler.class);
        emailHandler.start();

        return "Ok";
    }

}
