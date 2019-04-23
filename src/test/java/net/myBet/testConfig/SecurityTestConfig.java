package net.myBet.testConfig;

import net.myBet.config.security.UserDetailsServiceImpl;
import net.myBet.repository.UserRepository;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;

@TestConfiguration
public class SecurityTestConfig {

    @Bean
    @Qualifier("UDS")
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl();
    }

    @Bean
    public UserRepository userRepository(){
        return Mockito.mock(UserRepository.class);
    }
}