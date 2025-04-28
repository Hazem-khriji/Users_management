package UserApplication.controller;


import UserApplication.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;


import UserApplication.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import UserApplication.model.User;
import UserApplication.service.UserService;

@RestController
@RequestMapping("/users")
@Tag(
        name = "User Controller",
        description = "Controller for managing users: create, retrieve, update, and delete users."
)
public class UserController {
    @Autowired
    private UserService userService;

    private UserRepository userRepository;

    public UserController() {}

    @GetMapping("/welcome/welcome")
    @Operation(
            description = "Page nsallmoo fiha aala ness."
    )
    public ResponseEntity<String> welcome() {
        return new ResponseEntity<>("Ahlan Ahlan sharraftoonaa", HttpStatus.OK);
    }


    @GetMapping
    @Operation(
            description = "The full list of all users."
    )
    public List<User> getAllUsers() {
        return this.userService.getAllUsers();
    }
    @GetMapping({"/{value:.+}"})
    @Operation(
            summary = "User Id or email",
            description = "Provide user Id or email to get user details."
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
    @Operation(
            summary = "Add a new user",
            description = "Admin-only endpoint to create a new user."
    )
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @Operation(
            summary = "Update user",
            description = "Admin-only endpoint to update user."
    )
    @PutMapping({"/{id}"})
    @PreAuthorize("hasRole('ADMIN')")
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

    @Operation(
            summary = "Update user",
            description = "Admin-only endpoint to update user."
    )
    @PutMapping({"/{id}/{email}"})
    @PreAuthorize("hasRole('ADMIN')")
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

    @Operation(
            summary = "Delete user",
            description = "Admin-only endpoint to delete user."
    )
    @DeleteMapping("/{value:.+}")
    @PreAuthorize("hasRole('ADMIN')")
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
