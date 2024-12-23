package com.LMSAssginment.Code.BusinessLayers.Services;

import org.springframework.context.annotation.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("om983554@gmail.com");
        mailSender.setPassword("nswm bvxz fovh odsf");

        mailSender.getJavaMailProperties().put("mail.smtp.auth", "true");
        mailSender.getJavaMailProperties().put("mail.smtp.starttls.enable", "true");
        mailSender.getJavaMailProperties().put("mail.smtp.starttls.required", "true");
        mailSender.getJavaMailProperties().put("mail.smtp.ssl.trust", "smtp.gmail.com");
        return mailSender;

    }
}
