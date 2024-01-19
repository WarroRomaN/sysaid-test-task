package com.sysaid.assignment.service;

import com.sysaid.assignment.domain.User;
import com.sysaid.assignment.repository.IUserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class is an implementation of the IUserService interface.
 * It provides methods to add users, get a list of users, and get a random user.
 */
@Service
@Log4j2
public class UserServiceImpl implements IUserService {
    /**
     * The userRepository variable is an instance of the IUserRepository interface.
     * It is used in the UserServiceImpl class to perform CRUD operations on users.
     * The userRepository variable is declared as private and final, which means that it cannot be reassigned or accessed from outside the class.
     * The repository provides methods to add users, get a list of users, and get a random user.
     */
    private final IUserRepository userRepository;

    /**
     * UserServiceImpl is an implementation of IUserService interface.
     * It provides methods to add users, get a list of users, and get a random user.
     */
    public UserServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Adds a user to the user repository.
     * This method takes a User object as a parameter and logs the information about the user being added.
     * The User object is then passed to the addUser method of the userRepository instance to perform the addition operation.
     *
     * @param user The user to be added to the repository.
     */
    @Override
    @CacheEvict("users")
    public void addUser(User user) {
        log.info("Adding user: " + user);
        userRepository.addUser(user);
    }

    /**
     * Retrieves a list of users from the user repository.
     *
     * @return The list of users retrieved from the repository.
     */
    @Override
    @Cacheable("users")
    public List<User> getUsers() {
        List<User> users = userRepository.getUsers();
        log.info("Getting " + users.size() + " users from repository");
        return users;
    }

}
