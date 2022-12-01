package ru.doketov.forum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.doketov.forum.dao.UserRepository;
import ru.doketov.forum.model.FindUser;
import ru.doketov.forum.model.Role;
import ru.doketov.forum.model.User;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private final FindUser findUser;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder,
                           UserRepository userRepository, FindUser findUser) {
        this.findUser = findUser;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    @Override
    public User saveUser(User user) throws Exception {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            throw new Exception("Username already exists");
        }

        if (user.getUsername().length() < 3) {
            throw new Exception("Must be longer than 2 symbols");
        }

        String timePattern = "dd:MM:YYYY";
        ZonedDateTime curTime = ZonedDateTime.now(ZoneId.of( "Europe/Moscow"));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(timePattern);

        user.setBanned(false);
        user.setRegDate(curTime.format(dateTimeFormatter));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        userRepository.save(user);

        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll().stream().filter(user ->
                user.getRoles().size() < 2)
                .collect(Collectors.toList());
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    @Override
    public void banOrUnbanUser(User user) {
        User userFromDb = userRepository.getUserById(user.getId());
        userFromDb.setBanned(!userFromDb.getBanned());

        userRepository.save(userFromDb);
    }

    @Override
    public User getUserByUsername(String username) { return userRepository.getUserByUsername(username); }

    @Override
    public FindUser getFindUser() {
        return findUser;
    }
}
