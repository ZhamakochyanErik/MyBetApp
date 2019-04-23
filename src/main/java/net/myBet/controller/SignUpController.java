package net.myBet.controller;

import net.myBet.form.MailMessageForm;
import net.myBet.form.UserForm;
import net.myBet.model.User;
import net.myBet.model.enums.UserStatus;
import net.myBet.model.enums.UserType;
import net.myBet.service.UserService;
import net.myBet.service.impl.MailService;
import net.myBet.util.ViewName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping("/signUp")
public class SignUpController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Value("${email.subject}")
    private String emailSubject;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public String signUp(@Valid UserForm userForm, BindingResult result,
                         Model model){
        if(result.hasErrors()){
            return ViewName.SIGN_IN;
        }
        int checkEmailAndRepeatPasswordCount = 0;
        if(userService.existsByEmail(userForm.getEmail())){
            model.addAttribute("emailExistsError",true);
        }else {
            checkEmailAndRepeatPasswordCount++;
        }
        if(!userForm.getPassword().equals(userForm.getRePassword())){
            model.addAttribute("repeatPasswordError",true);
        }else {
            checkEmailAndRepeatPasswordCount++;
        }
        if(checkEmailAndRepeatPasswordCount != 2){
            return ViewName.SIGN_IN;
        }
        String token = UUID.randomUUID().toString();
        String text = "http://localhost:8080/email/confirm/" + userForm.getEmail() + "/" + token;
        MailMessageForm messageForm = MailMessageForm
                .builder()
                .to(userForm.getEmail())
                .subject(emailSubject)
                .text(text)
                .build();
        User user = User
                .builder()
                .name(userForm.getName())
                .surname(userForm.getSurname())
                .email(userForm.getEmail())
                .passwordCode(userForm.getPasswordCode())
                .password(passwordEncoder.encode(userForm.getPassword()))
                .userType(UserType.USER)
                .token(token)
                .userStatus(UserStatus.INACTIVE)
                .build();
        userService.add(user);
        mailService.sendMessage(messageForm);
        LOGGER.info("{} user signed up successfully");
        return "redirect:/signIn?signUp-new";
    }
}