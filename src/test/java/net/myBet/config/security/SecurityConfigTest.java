package net.myBet.config.security;

import net.myBet.model.User;
import net.myBet.model.enums.UserStatus;
import net.myBet.model.enums.UserType;
import net.myBet.repository.UserRepository;
import net.myBet.testConfig.SecurityTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@RunWith(SpringRunner.class)
@WebMvcTest
@ContextConfiguration(classes = SecurityTestConfig.class)
public class SecurityConfigTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testInvalidEmail() throws Exception {
        String email = "email";
        when(userRepository.findByEmail(email))
                .thenReturn(Optional.empty());
        mvc
                .perform(formLogin("/signIn")
                        .user(email,"email"))
                .andDo(print())
                .andExpect(redirectedUrl("/signIn?data-error"));
    }

    @Test
    public void testInvalidPassword() throws Exception {
        String email = "email";
        String password = "password";
        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(User
                        .builder()
                        .email(email)
                        .password(passwordEncoder.encode("pass"))
                        .userType(UserType.USER)
                        .userStatus(UserStatus.ACTIVE)
                        .build()));
        mvc
                .perform(formLogin("/signIn")
                        .user(email,"email")
                        .password(password,"password"))
                .andDo(print())
                .andExpect(redirectedUrl("/signIn?data-error"));
    }

    @Test
    public void testInactiveUser() throws Exception {
        String email = "email";
        String password = "password";
        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(User
                        .builder()
                        .email(email)
                        .password(passwordEncoder.encode(password))
                        .userType(UserType.USER)
                        .userStatus(UserStatus.INACTIVE)
                        .build()));
        mvc
                .perform(formLogin("/signIn")
                        .user(email,"email")
                        .password(password,"password"))
                .andDo(print())
                .andExpect(redirectedUrl("/signIn?email-inactive"));
    }

    @Test
    public void testLoginUser() throws Exception {
        String email = "email";
        String password = "password";
        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(User
                        .builder()
                        .email(email)
                        .password(passwordEncoder.encode(password))
                        .userType(UserType.USER)
                        .userStatus(UserStatus.ACTIVE)
                        .build()));
        mvc
                .perform(formLogin("/signIn")
                        .user(email,"email")
                        .password(password,"password"))
                .andDo(print())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void testLoginAdmin() throws Exception {
        String email = "email";
        String password = "password";
        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(User
                        .builder()
                        .email(email)
                        .password(passwordEncoder.encode(password))
                        .userType(UserType.ADMIN)
                        .userStatus(UserStatus.ACTIVE)
                        .build()));
        mvc
                .perform(formLogin("/signIn")
                        .user(email,"email")
                        .password(password,"password"))
                .andDo(print())
                .andExpect(redirectedUrl("/admin"));
    }
}