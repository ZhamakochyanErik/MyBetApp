package net.myBet.service.impl;

import net.myBet.form.MailMessageForm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private MailSender mailSender;

    @Async
    public void sendMessage(MailMessageForm messageForm){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject(messageForm.getSubject());
        mailMessage.setText(messageForm.getText());
        mailMessage.setTo(messageForm.getTo());
        mailSender.send(mailMessage);
        LOGGER.info("{} message sent successfully",messageForm);
    }
}