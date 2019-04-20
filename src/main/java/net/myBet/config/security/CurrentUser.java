package net.myBet.config.security;

import lombok.Getter;
import net.myBet.model.User;
import org.springframework.security.core.authority.AuthorityUtils;

public class CurrentUser extends org.springframework.security.core.userdetails.User{

    @Getter
    private User user;

    public CurrentUser(User user,boolean isActive) {
        super(user.getEmail(),user.getPassword(),true,
                true,true,isActive, AuthorityUtils.createAuthorityList(user.getUserType().name()));
        this.user = user;
    }
}