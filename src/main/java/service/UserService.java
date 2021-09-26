package service;

import dto.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserService {

    List<User> users = new ArrayList<>();

    public List<User> getAll() {
        return users;
    }

    public boolean addUser(User user) {
        return users.add(user);
    }

    public Optional<User> login(String name, String password) {
        return users.stream()
                .filter(u -> u.getUserName().equals(name))
                .filter(u -> u.getPassword().equals(password))
                .findFirst();
    }
}
