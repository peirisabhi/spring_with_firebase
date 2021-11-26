package com.example.test1.test;

import javax.mail.Session;
import java.util.Properties;

import static com.example.test1.Config.HOST;

public class Test {
    public static void main(String[] args) {
        //create properties field
        Properties properties = new Properties();

        properties.put("mail.pop3.host", HOST);
        properties.put("mail.pop3.port", "995");
        properties.put("mail.pop3.starttls.enable", "true");
        Session emailSession = Session.getDefaultInstance(properties);
    }
}
