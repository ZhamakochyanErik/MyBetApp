package net.myBet.controller;

import net.myBet.form.MailMessageForm;
import net.myBet.model.User;
import net.myBet.model.enums.UserStatus;
import net.myBet.model.enums.UserType;
import net.myBet.service.UserService;
import net.myBet.service.impl.MailService;
import net.myBet.testConfig.SecurityTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = SignUpController.class)
@ContextConfiguration(classes = SecurityTestConfig.class)
public class SignUpControllerTest {

    private static final String URI = "/signUp";

    @Value("${email.subject}")
    private String emailSubject;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private MailService mailService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void invalidDataTest()throws Exception{
        String[] errorFieldNameArray = {"name","surname","email","passwordCode","password","rePassword"};
        mvc.perform(post(URI)
                .param("name","")
                .param("surname","")
                .param("email","")
                .param("passwordCode","")
                .param("password","")
                .param("rePassword",""))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("userForm",errorFieldNameArray));
    }

    @Test
    public void emailExistsTest()throws Exception{
        String email = "tomas@gmail.com";
        when(userService.existsByEmail(email)).thenReturn(true);
        mvc.perform(post(URI)
                .param("name","Tomas")
                .param("surname","Walter")
                .param("email",email)
                .param("passwordCode","AF2828282828282")
                .param("password","tomas")
                .param("rePassword","tomas"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("emailExistsError"));
    }

    @Test
    public void repeatPasswordsErrorTest()throws Exception{
        String email = "tomas@gmail.com";
        when(userService.existsByEmail(email)).thenReturn(false);
        mvc.perform(post(URI)
                .param("name","Tomas")
                .param("surname","Walter")
                .param("email",email)
                .param("passwordCode","AF2828282828282")
                .param("password","tomas1")
                .param("rePassword","tomas"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("repeatPasswordError"));
    }

    @Test
    public void everythingIsOkTest()throws Exception{
        String encodePassword = "111111";
        User expectUser = User
                .builder()
                .name("Tomas")
                .surname("Walter")
                .email("tomas@gmail.com")
                .password("tomas")
                .passwordCode("AF2828282828282")
                .userStatus(UserStatus.INACTIVE)
                .userType(UserType.USER)
                .build();
        when(passwordEncoder.encode(expectUser.getPassword())).thenReturn(encodePassword);
        when(userService.existsByEmail(expectUser.getEmail())).thenReturn(false);
        mvc.perform(post(URI)
                .param("name",expectUser.getName())
                .param("surname",expectUser.getSurname())
                .param("email",expectUser.getEmail())
                .param("passwordCode",expectUser.getPasswordCode())
                .param("password",expectUser.getPassword())
                .param("rePassword",expectUser.getPassword()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/signIn?signUp-new"));
        expectUser.setPassword(encodePassword);
        MailMessageForm messageForm = MailMessageForm
                .builder()
                .subject(emailSubject)
                .to(expectUser.getEmail())
                .build();
        verify(userService).add(expectUser);
        verify(mailService).sendMessage(messageForm);
    }
}