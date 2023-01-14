package com.example.restful_test.service;

import com.example.restful_test.exception.UserNotFoundException;
import com.example.restful_test.exception.UserNotSavedException;
import com.example.restful_test.model.User;
import com.example.restful_test.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.data.domain.PageRequest.*;

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

    public Iterable<User> findAllUsers(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<User> users = userRepository.findAll(paging);
        if (users.hasContent()) {
            return users.getContent();
        } else {
            throw new UserNotFoundException("No users");
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

    public User findByLogin(String login) {
        User user = userRepository.findByLogin(login);
        return user;
    }
}
