package com.example.assignment_buddy_user_service.controller;


import com.example.assignment_buddy_user_service.config.JwtUtil;
import com.example.assignment_buddy_user_service.dto.LoginResponse;
import com.example.assignment_buddy_user_service.dto.UserResponse;
import com.example.assignment_buddy_user_service.model.User;
import com.example.assignment_buddy_user_service.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
        public AuthController(UserService userService, JwtUtil jwtUtil) {
            this.userService = userService;
            this.jwtUtil = jwtUtil;
        }

        // ✅ Signup
        @PostMapping("/signup")
        public ResponseEntity<UserResponse> signup(@RequestBody User user) {

            User savedUser = userService.register(user);

            return ResponseEntity.ok(
                    new UserResponse(
                            savedUser.getId(),
                            savedUser.getName(),
                            savedUser.getEmail()
                    )
            );
        }

        // ✅ Login
        @PostMapping("/login")
        public ResponseEntity<LoginResponse> login(@RequestBody User user) {

            User loggedInUser =
                    userService.login(user.getEmail(), user.getPassword());

            String token = jwtUtil.generateToken(
                    loggedInUser.getId(),
                    loggedInUser.getEmail()
            );

            return ResponseEntity.ok(
                    new LoginResponse(
                            token,
                            loggedInUser.getId(),
                            loggedInUser.getName(),
                            loggedInUser.getEmail()
                    )
            );
        }


}
