package net.myBet.testConfig;

import net.myBet.config.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;

@TestConfiguration
public class SecurityTestConfig {

    @Bean
    @Qualifier("UDS")
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl();
    }
}