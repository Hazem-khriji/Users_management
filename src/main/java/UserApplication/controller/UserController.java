package UserApplication.controller;


import UserApplication.exception.UserNotFoundException;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import UserApplication.model.User;
import UserApplication.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    //private UserRepository userRepository;

    public UserController() {}

    @GetMapping
    public List<User> getAllUsers() {
        return this.userService.getAllUsers();
    }

    @GetMapping({"/{value:.+}"})
    @ApiOperation(
            value = "User  Id or email",
            notes = "Provide user Id or email",
            response= ResponseEntity.class
    )
    public ResponseEntity<User> getUser(@PathVariable String value) {
        try{
            if (value.matches("\\d+")) {
                Long id = Long.parseLong(value);
                Optional<User> user = this.userService.getUserById(id);
                if (user.isPresent()) {
                    return ResponseEntity.ok(user.get());
                } else {
                    throw (new UserNotFoundException("User with such ID was not found"));
                }
            } else {
                Optional<User> user = this.userService.getUserByEmail(value);
                if (user.isPresent()) {
                    return ResponseEntity.ok(user.get());
                } else {
                    throw (new UserNotFoundException("User with such email was not found"));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<User> updateUser(@PathVariable Long id,@RequestBody User user) {
        Optional<User> existingUser = this.userService.getUserById(user.getId());
        if (existingUser.isEmpty()) {
            throw(new UserNotFoundException("User with such Id was not found"));
        }
        else{
            user.setId(id);
            User updatedUser = this.userService.addUser(user);
            return ResponseEntity.ok(updatedUser);
        }
    }
    @PutMapping({"/{id}/{email}"})
    public ResponseEntity<User> updateUser(@PathVariable Long id, @PathVariable String email) {
        Optional<User> existingUser = this.userService.getUserById(id);
        if (existingUser.isEmpty()) {
            throw(new UserNotFoundException("User with such Id was not found"));
        }
        else{
            ((User) existingUser.get()).setEmail(email);
            User updatedUser = this.userService.addUser(existingUser.get());
            return ResponseEntity.ok(updatedUser);
        }
    }
    @DeleteMapping("/{value:.+}")
    public ResponseEntity<Void> deleteUser(@PathVariable String value) {
        if (value.matches("\\d+")) {
            Long id = Long.parseLong(value);
            Optional<User> user = this.userService.getUserById(id);
            if (user.isPresent()) {
                this.userService.deleteById(id);
                return ResponseEntity.noContent().build();
            }
            else{
                throw(new UserNotFoundException("User with such ID was not found"));
            }
        }
        else{
            Optional<User> user = this.userService.getUserByEmail(value);
            if (user.isPresent()) {
                this.userService.deleteByEmail(value);
                return ResponseEntity.noContent().build();
            }
            else{
                throw(new UserNotFoundException("User with such email was not found"));
            }
        }
    }
}
