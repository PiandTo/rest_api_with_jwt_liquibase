package com.example.restful_test.controller;

import com.example.restful_test.exception.UserNotFoundException;
import com.example.restful_test.model.User;
import com.example.restful_test.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/")
@Tag(name = "User Controller", description = "REST controller provide services to manages user")
public class UserControler {
    private final UserService userService;

    public UserControler(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Endpoint provide all available users")
    @GetMapping("users")
    public ResponseEntity<?> getAllUsers(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                         @RequestParam(name = "pageSize", defaultValue = "2") Integer pageSize,
                                         @RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {
        Iterable<User> user =  userService.findAllUsers(pageNo, pageSize, sortBy);
        log.info("Get method - GetAllUser function");
        return ResponseEntity.status(HttpStatus.OK)
                .body(user);
    }

    @GetMapping("users/{id}")
    @Operation(summary = "Endpoint provide details about User specified by ID")
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
    @Operation(summary = "Endpoint add new User")
    public ResponseEntity<?> addNewUser (@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        log.info("Post method - addNewUser function save " + savedUser);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(user);
    }

    @PutMapping("users/{user-id}")
    @Operation(summary = "Endpoint update information about User")
    public User updateUser(@RequestBody User user,
                           @PathVariable("user-id") Long id) throws Exception {
        User savedUser =  userService.updateUser(id, user);
        log.info("Put method - updateUser function update " + savedUser);
        return savedUser;
    }

    @DeleteMapping("users/{user-id}")
    @Operation(summary = "Endpoint delete User specified by ID")
    public void deleteUser(@PathVariable("user-id") Long id) {
        log.info("Delete method - deleteUser function delete user with id " + id);
        userService.deleteUser(id);
    }


}
