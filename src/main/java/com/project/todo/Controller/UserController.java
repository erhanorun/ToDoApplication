package com.project.todo.Controller;

import com.project.todo.Entities.ToDoUser;
import com.project.todo.Jwt.Utils;
import com.project.todo.Repository.UsersRepository;
import com.project.todo.Request.SignInRequest;
import com.project.todo.Request.LoginRequest;
import com.project.todo.Response.ResponseMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationManager authenticate;
    private final UsersRepository userRepository;
    private final PasswordEncoder encoder;
    private final Utils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignInRequest signInRequest) {


        if (userRepository.existsByUsername(signInRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Error: Username is already taken!"));
        }

        // Create new user's account
        ToDoUser user = new ToDoUser(signInRequest.getUsername(),
                encoder.encode(signInRequest.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(new ResponseMessage("User registered successfully!"));
    }

    @PostMapping("/login")
    public String generateToken(@Valid @RequestBody LoginRequest authRequest) throws Exception {
        try {
            authenticate.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                    authRequest.getPassword()));
        } catch (Exception ex) {
            throw new Exception("invalid username/password");
        }
        return jwtUtils.generateToken(authRequest.getUsername());
    }
}
