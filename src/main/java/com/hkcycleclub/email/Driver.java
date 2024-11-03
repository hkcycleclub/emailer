package com.hkcycleclub.email;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.google.common.collect.ImmutableMap;

public class Driver {

  private static final String fromEmail = "info@hkcycleclub.com";
  private static final String fromName = "H&K Cycle Club";

  public static void main(String[] args) {
    try {
      //Session session = createSessionTest();

       Session session = createSessionProduction();

      sendEmail(session, "Update to London to Bournemouth, H&KCC National Ride 2023");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static Session createSessionTest() {
    Properties props = new Properties();
    props.put("mail.smtp.host", "smtp.mailtrap.io");
    props.put("mail.smtp.port", "587");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.ssl.protocols", "TLSv1.2");

    return Session.getDefaultInstance(
        props,
        new Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("d00dc66bbb395f", "7b155fa506426c");
          }
        });
  }

  private static void sendEmail(Session session, String subject) throws Exception {
    List<Recipient> recipients = RecipientParser.parseCsv("/list-me.csv");
    System.out.println("Send emails to " + recipients.size() + " brothers");

    List<Recipient> sent = new ArrayList<>();
    List<Recipient> failed = new ArrayList<>();

    for (Recipient recipient : recipients) {
      Message msg =
          generateEmailMessage(
              recipient.getEmailAddress(), recipient.getFullName(), session, subject);
      try {
        Transport.send(msg);
        System.out.println("Sent to " + recipient.getFullName());
      } catch (MessagingException e) {
        failed.add(recipient);
      }
      sent.add(recipient);
    }

    failed.forEach(
        recipient ->
            System.out.println(
                "failed to sent to: "
                    + recipient.getFullName()
                    + ", "
                    + recipient.getEmailAddress()));
  }

  private static Message generateEmailMessage(
      String toEmail, String toName, Session session, String subject) throws Exception {
    Message msg = new MimeMessage(session);
    msg.setFrom(new InternetAddress(fromEmail, fromName));

    msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail, toName));
    msg.setSubject(subject);

    MimeMultipart multipart = new MimeMultipart("related");

    BodyPart messageBodyPart = new MimeBodyPart();
    String htmlText = generateBody(toName);
    messageBodyPart.setContent(htmlText, "text/html");

    multipart.addBodyPart(messageBodyPart);

    msg.setContent(multipart);
    return msg;
  }

  static String generateBody(String name) throws Exception {
    EmailTemplate emailTemplate = new EmailTemplate("index.html");
    Map<String, String> variable = ImmutableMap.of("name", name);
    return emailTemplate.generate(variable);
  }

  private static Session createSessionProduction() {
    Properties props = new Properties();
    props.put("mail.smtp.host", "outlook.office365.com");
    props.put("mail.smtp.port", "587");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.ssl.protocols", "TLSv1.2");

    return Session.getDefaultInstance(
        props,
        new Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("info@hkcycleclub.com", "H&Kcc1443");
          }
        });
  }
}
