package ru.doketov.forum.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.doketov.forum.model.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
