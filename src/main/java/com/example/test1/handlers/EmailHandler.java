package com.example.test1.handlers;


import com.example.test1.entity.EmailDetails;
import com.example.test1.service.EmailService;
import com.example.test1.session.EmailSession;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.MimeMultipart;

import static com.example.test1.Config.*;

@Component
@Scope("application")
public class EmailHandler extends Thread{
    EmailService emailService = new EmailService();

    public void run() {
        System.out.println("Started");
        long startTime = System.currentTimeMillis();
        int i = 0;

        //create properties field
        Properties properties = new Properties();

        properties.put("mail.pop3.host", HOST);
        properties.put("mail.pop3.port", "995");
        properties.put("mail.pop3.starttls.enable", "true");
        Session emailSession = Session.getDefaultInstance(properties);

        EmailHandler emailHandler = new EmailHandler();

        while (true) {
//            System.out.println(this.getName() + ": New Thread is running..." + i++);


            emailHandler.checkNewMail(emailSession);

            try {
                //Wait for one sec so it doesn't print too fast
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void checkNewMail(Session emailSession) {
        try {



            //create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("pop3s");

            store.connect(HOST, USER, PASSWORD);

            //create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();
            System.out.println("messages.length---" + messages.length);

            for (int i = 0, n = messages.length; i < n; i++) {
                Message message = messages[i];
                System.out.println("---------------------------------");
                System.out.println("Email Number " + (i + 1));
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("Text: " + message.getContent().toString());
                System.out.println("Content: " + getTextFromMessage(message));

                EmailDetails emailDetails = new EmailDetails();
                emailDetails.setFrom(message.getSubject());
                emailDetails.setContent(message.getContent().toString());
//                emailDetails.setText(message.get);


                saveFireBase(emailDetails);
            }

            //close the store and folder objects
            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getTextFromMessage(Message message) throws MessagingException, IOException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    private String getTextFromMimeMultipart(
            MimeMultipart mimeMultipart) throws MessagingException, IOException {
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
                break; // without break same text appears twice in my tests
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
//                result = result + "\n" + Jsoup.parse(html).text();
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
            }
        }
        return result;
    }


    private void saveFireBase(EmailDetails emailDetails) throws ExecutionException, InterruptedException {
        emailService.saveEmailDetails(emailDetails);
    }

}
