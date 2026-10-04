package com.smart.vendor_management.Controller;

import com.smart.vendor_management.dto.LoginRequest;
import com.smart.vendor_management.dto.LoginResponse;
import com.smart.vendor_management.dto.RegisterRequest;
import com.smart.vendor_management.dto.UserResponse;
import com.smart.vendor_management.entity.User;
import com.smart.vendor_management.service.JwtService;
import com.smart.vendor_management.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(UserService userService,
                          AuthenticationManager authenticationManager,
                          JwtService jwtService) {

        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody RegisterRequest request) {

        User user = new User();

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRole("USER");

        User savedUser = userService.registerUser(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getRole()
        );
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String token =
                jwtService.generateToken(request.getUsername());

        User user = userService
                .getUserByUsername(request.getUsername());

        return new LoginResponse(
                token,
                user.getUsername(),
                user.getRole()
        );
    }
}