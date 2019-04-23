package net.myBet.service.impl;

import net.myBet.model.User;
import net.myBet.repository.UserRepository;
import net.myBet.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void add(User user) {
        userRepository.save(user);
        LOGGER.info("{} user saved successfully",user);
    }
}
