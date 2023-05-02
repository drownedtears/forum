package ru.doketov.forum.service.user;

import ru.doketov.forum.model.dto.FindUser;
import ru.doketov.forum.model.entity.User;

import java.util.List;

public interface UserService {

    User saveUser(User user) throws Exception;

    List<User> getAllUsers();

    User getUserById(Long id);

    void banOrUnbanUser(User user);

    User getUserByUsername(String username);

    FindUser getFindUser();
}
