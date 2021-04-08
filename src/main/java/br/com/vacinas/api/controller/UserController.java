package br.com.vacinas.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.vacinas.api.exception.ResourceNotFoundException;
import br.com.vacinas.api.model.User;
import br.com.vacinas.api.repository.UserRepostory;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserRepostory userRepostory;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepostory.findAll();
    }
    
    @PostMapping("/users")
    @ResponseStatus(code = HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        return userRepostory.save(user);
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable(value = "id") Long userId) {
        return userRepostory.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable(value = "id") Long userId, @RequestBody User userDetails) {
        User user = userRepostory.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        user.setName(userDetails.getName());
        user.setCpf(userDetails.getCpf());
        user.setMail(userDetails.getMail());
        user.setBirthDate(userDetails.getBirthDate());

        User updatedUser = userRepostory.save(user);
        return updatedUser;
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long userId) {
        User user = userRepostory.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        userRepostory.delete(user);

        return ResponseEntity.ok().build();
    }
}
