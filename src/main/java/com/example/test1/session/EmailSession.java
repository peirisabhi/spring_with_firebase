package com.example.test1.session;

import org.springframework.stereotype.Service;

import javax.mail.Session;
import java.util.Properties;

import static com.example.test1.Config.HOST;

public class EmailSession {

    Session emailSession;

    EmailSession(){
        //create properties field
        Properties properties = new Properties();

        properties.put("mail.pop3.host", HOST);
        properties.put("mail.pop3.port", "995");
        properties.put("mail.pop3.starttls.enable", "true");
        emailSession = Session.getDefaultInstance(properties);
    }

}
