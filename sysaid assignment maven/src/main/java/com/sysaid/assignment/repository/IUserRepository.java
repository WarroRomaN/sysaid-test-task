package com.sysaid.assignment.repository;

import com.sysaid.assignment.domain.User;

import java.util.List;


public interface IUserRepository {
    List<User> getUsers();

    void addUser(User user);
}
