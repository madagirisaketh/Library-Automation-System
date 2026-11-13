package com.library.service;

import com.library.dao.UserDAO;
import com.library.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User login(String username, String password, String role) {
        return userDAO.login(username, password, role);
    }

    public List<User> all() {
        return userDAO.all();
    }
}