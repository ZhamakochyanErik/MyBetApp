package net.myBet.config.security;

import net.myBet.model.enums.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("UDS")
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/signIn","/signUp","/user/email/*/exists")
                .anonymous()
                .antMatchers("/")
                .hasAnyAuthority(UserType.USER.name(),"ROLE_ANONYMOUS")
                .antMatchers("/admin","/admin/**")
                .hasAnyAuthority(UserType.ADMIN.name())
        .and()
                .formLogin()
                .loginPage("/signIn")
                .loginProcessingUrl("/signIn")
                .usernameParameter("email")
                .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();
                    String redirectUrl;
                    if(currentUser.getUser().getUserType() == UserType.ADMIN){
                        redirectUrl = "/admin";
                    }else {
                        redirectUrl = "/";
                    }
                    httpServletResponse.sendRedirect(redirectUrl);
                })
                .failureHandler((httpServletRequest, httpServletResponse, e) -> {
                    if(e instanceof LockedException){
                        httpServletResponse.sendRedirect("/signIn?email-inactive");
                    }else {
                        httpServletResponse.sendRedirect("/signIn?data-error");
                    }
                })
        .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("RM")
        .and()
                .rememberMe()
                .rememberMeParameter("remember-me")
                .rememberMeCookieName("RM")
        .and()
                .csrf()
                .disable();
    }
}