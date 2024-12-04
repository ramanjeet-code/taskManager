package com.taskManager.controller;
import com.taskManager.exception.UserException;
import com.taskManager.modules.User;
import com.taskManager.service.UserService;
import com.taskManager.utility.JwtRequest;
import com.taskManager.utility.JwtResponse;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthController {
    ;
    @Autowired
    private UserService userService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // User Signup
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid  User user) throws UserException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.addUser(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    // User Login
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) throws UserException {
        JwtResponse response=  userService. login(jwtRequest);
            return new ResponseEntity<JwtResponse>(response, HttpStatus.OK);

    }

}
