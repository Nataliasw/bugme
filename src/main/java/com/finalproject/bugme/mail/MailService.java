package com.finalproject.bugme.mail;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

private final JavaMailSender javaMailSender;

public void sendMail(Mail mail){
    try{
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);

        mimeMessageHelper.setTo(mail.getRecipient());
        mimeMessageHelper.setSubject(mail.getSubject());
        mimeMessageHelper.setText(mail.getContent());
        javaMailSender.send(mimeMessage);
    } catch(Exception e){
        System.out.println("Wysyłanie maila nie powiodło się");
    }
}
}
