package com.drcall.client.test;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

public class EmailAttachmentExample {

    public static void main(String[] args) {

        EmailAttachmentExample example = new EmailAttachmentExample();
        example.sendEmail();

    }

    public void sendEmail() {

        // Strings that contain from, to, subject, body and file path to the attachment
        String from = "sender@javacodegeeks.com";
        String to = "sclin0323@gmail.com";
        String subject = "Test mail";
        String body = "Test body";
        String filename = "C:\\csb.log";

        // Set smtp properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.javacodegeeks.com");
        properties.put("mail.smtp.port", "25");
        Session session = Session.getDefaultInstance(properties, null);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setSentDate(new Date());

            // Set the email body
            MimeBodyPart messagePart = new MimeBodyPart();
            messagePart.setText(body);

            // Set the email attachment file
            MimeBodyPart attachmentPart = new MimeBodyPart();
            FileDataSource fileDataSource = new FileDataSource(filename) {
                @Override
                public String getContentType() {
                    return "application/octet-stream";
                }
            };

            attachmentPart.setDataHandler(new DataHandler(fileDataSource));
            attachmentPart.setFileName(fileDataSource.getName());

            // Add all parts of the email to Multipart object
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messagePart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            // Send email
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
