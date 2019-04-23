package net.myBet.service;

import net.myBet.model.User;

public interface UserService {

    boolean existsByEmail(String email);

    void add(User user);
}
