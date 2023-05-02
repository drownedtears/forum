package ru.doketov.forum.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.doketov.forum.model.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User getUserById(Long id);

    User getUserByUsername(String username);

}
