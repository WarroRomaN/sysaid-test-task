package com.sysaid.assignment.repository;

import com.sysaid.assignment.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Repository
public class UserRepositoryImpl implements IUserRepository {

    private final Random random = new Random();
    private final static List<User> users = new ArrayList<>();

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }

}
