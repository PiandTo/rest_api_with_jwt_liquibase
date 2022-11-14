package com.example.restful_test.controller;

import com.example.restful_test.model.User;
import com.example.restful_test.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(user);
    }

    @PostMapping("users")
    public User addNewUser (@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        log.info("Post method - addNewUser function save " + savedUser);
        return savedUser;
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
