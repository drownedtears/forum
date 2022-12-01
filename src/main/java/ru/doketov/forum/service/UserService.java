package ru.doketov.forum.service;

import ru.doketov.forum.model.FindUser;
import ru.doketov.forum.model.User;

import java.util.List;

public interface UserService {

    User saveUser(User user) throws Exception;

    List<User> getAllUsers();

    User getUserById(Long id);

    void banOrUnbanUser(User user);

    User getUserByUsername(String username);

    FindUser getFindUser();
}
