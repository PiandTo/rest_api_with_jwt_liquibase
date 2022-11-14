package com.example.restful_test.service;

import com.example.restful_test.exception.UserNotFoundException;
import com.example.restful_test.exception.UserNotSavedException;
import com.example.restful_test.model.User;
import com.example.restful_test.repository.UserRepository;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        if (user == null) {
            throw new UserNotSavedException("It is no provided user");
        }
        try {
            return userRepository.save(user);
        } catch (HttpMessageNotReadableException ex) {
            throw new UserNotSavedException(ex.getMessage());
        }
    }

    public Optional<User> findUserById(Long id) {
        if (id == null) {
            throw new UserNotSavedException("It is no provided user");
        }
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user;
        } else {
            throw new UserNotFoundException("No user with id " + id);
        }
    }

    public Iterable<User> findAllUsers() {
        int size = 0;
        Iterable<User> users = userRepository.findAll();
        for (User u: users) {
            size++;
        }
        if (size == 0) {
            throw new UserNotFoundException("No users");
        } else {
            return users;
        }
    }

    public User updateUser(Long id, User user) throws Exception {
        if (user == null || id == null) {
            throw new UserNotSavedException("It is no provided user");
        }
        User newUser = findUserById(id).orElseThrow(Exception::new);
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setRole(user.getRole());
        newUser.setLogin(user.getLogin());
        newUser.setPassword(user.getPassword());
        return saveUser(newUser);
    }

    public void deleteUser(Long id) {
        if (id == null) {
            throw new UserNotSavedException("It is no provided user");
        }
        Optional<User> user = findUserById(id);
        userRepository.deleteById(id);
    }
}