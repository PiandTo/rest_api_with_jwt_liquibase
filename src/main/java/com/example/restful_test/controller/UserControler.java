package com.example.restful_test.controller;

import com.example.restful_test.exception.UserNotFoundException;
import com.example.restful_test.model.User;
import com.example.restful_test.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/")
public class UserControler {
    private final UserService userService;

    public UserControler(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("users")
    public ResponseEntity<?> getAllUsers() {
        Iterable<User> user =  userService.findAllUsers();
        log.info("Get method - GetAllUser function");
        return ResponseEntity.status(HttpStatus.OK)
                .body(user);
    }

    @GetMapping("users/{id}")
    public ResponseEntity<?> findUserByid (@PathVariable(name = "id") Long id) {
        Optional<User> findUser = userService.findUserById(id);
        if (findUser.isPresent()) {
            log.info("Get method - findUserByid function");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(findUser.get());
        }
        throw new UserNotFoundException("No such user");
    }

    @PostMapping("users")
    public ResponseEntity<?> addNewUser (@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        log.info("Post method - addNewUser function save " + savedUser);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(user);
    }

    @PutMapping("users/{user-id}")
    public User updateUser(@RequestBody User user,
                           @PathVariable("user-id") Long id) throws Exception {
        User savedUser =  userService.updateUser(id, user);
        log.info("Put method - updateUser function update " + savedUser);
        return savedUser;
    }

    @DeleteMapping("users/{user-id}")
    public void deleteUser(@PathVariable("user-id") Long id) {
        log.info("Delete method - deleteUser function delete user with id " + id);
        userService.deleteUser(id);
    }


}
