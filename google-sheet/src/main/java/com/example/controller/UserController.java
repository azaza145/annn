package com.example.controller;

import com.example.model.User;
import com.example.service.MailService;
import com.example.service.RandomStringGenerator;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    private final MailService mailService;
    @Autowired
    private SpringTemplateEngine templateEngine;

    public UserController(MailService mailService) {
        this.mailService = mailService;
    }


    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }




    @PostMapping
    public User createUser(@RequestBody User user) {
        String password = RandomStringGenerator.generateRandomString(8);
        user.setPassword(password);
        user.setRole(User.Role.USER);
        String subject = "Registration Successful";
        Context context = new Context();
        context.setVariable("name", user.getLastName());
        context.setVariable("email", user.getEmail());
        context.setVariable("password", password);

        String body = templateEngine.process("registration_successful", context);

        mailService.sendMail(user.getEmail(), subject, body, true);

        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return userService.getUserById(id)
                .map(user -> {
                    user.setFirstName(userDetails.getFirstName());
                    user.setLastName(userDetails.getLastName());
                    user.setDivision(userDetails.getDivision());
                    user.setEmail(userDetails.getEmail());
                    user.setPassword(userDetails.getPassword());
                    user.setRole(userDetails.getRole());
                    User updatedUser = userService.updateUser(user);
                    return ResponseEntity.ok(updatedUser);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody Map<String, String> loginDetails) {
        String email = loginDetails.get("email");
        String password = loginDetails.get("password");
        User user = userService.login(email, password);
        return ResponseEntity.ok(user);
    }


    @GetMapping("/profile")
    public ResponseEntity<User> getAuthenticatedUser() {
        // Get the currently authenticated user's email (username in this case)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        // Fetch user details from the database
        User user = userService.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Return the user info (excluding sensitive info like password)
        return ResponseEntity.ok(user);
    }

}