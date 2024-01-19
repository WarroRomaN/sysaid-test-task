package com.sysaid.assignment.service;

import com.sysaid.assignment.domain.User;

import java.util.List;

/**
 * This interface represents a user service.
 * It provides methods to add users, get a list of users, and get a random user.
 */
public interface IUserService {

    void addUser(User user);

    List<User> getUsers();

}
