package com;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailSender {
	String filePath = "C:/Users/wenhuan.HYRON-JS/Desktop/1.gif";
    String smtpHost = "mail.hyron-js.com";
    String to ="wenhuan@hyron-js.com";
    String from = "wenhuan@hyron-js.com";
    String name = "wenhuan";
    String password = "JasonWen@1234";
    String subject = "test img";
    String content = "<img src='cid:IMG1' width='600' height='450'></img><b>testtest</b><img src='cid:IMG2' width='600' height='450'></img>";

    public static void main(String[] args) throws MessagingException{
    	EmailSender es = new EmailSender();
    	es.sendEmail();
    }
    
    public void sendEmail() throws MessagingException{
    	Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(props, null);
        
        session.setDebug(false);
        
        MimeMessage message;
        InternetAddress[] address = {new InternetAddress(to)};
        message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, address);
        message.setSubject(subject);
        message.setSentDate(new Date());
        
        MimeMultipart mm = new MimeMultipart("related");
        MimeBodyPart mdp = new MimeBodyPart();
        
        mdp.setContent(content, "text/html;charset=UTF-8");
        
        MimeBodyPart attachmentBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(filePath);
        attachmentBodyPart.setDataHandler(new DataHandler(source));
        attachmentBodyPart.setFileName("1.gif");
        attachmentBodyPart.setHeader("Content-ID", "IMG1");
        
        MimeBodyPart attachmentBodyPart1 = new MimeBodyPart();
        DataSource source1 = new FileDataSource("C:/Users/wenhuan.HYRON-JS/Desktop/paging.png");
        attachmentBodyPart1.setDataHandler(new DataHandler(source1));
        attachmentBodyPart1.setFileName("paging.png");
        attachmentBodyPart1.setHeader("Content-ID", "IMG2");
        
        
        mm.addBodyPart(mdp);
        mm.addBodyPart(attachmentBodyPart);
        mm.addBodyPart(attachmentBodyPart1);
        
        message.setContent(mm);
        
        message.saveChanges(); 
        javax.mail.Transport transport = session.getTransport("smtp");
        transport.connect(smtpHost, name, password);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}
