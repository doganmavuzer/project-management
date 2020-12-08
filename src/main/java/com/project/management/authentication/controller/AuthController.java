package com.project.management.authentication.controller;

import com.project.management.authentication.dto.LoginRequest;
import com.project.management.authentication.dto.SignUpRequest;
import com.project.management.authentication.jwt.JwtTokenProvider;
import com.project.management.authentication.provider.AuthManager;
import com.project.management.authentication.provider.PassEncoder;
import com.project.management.role.domain.Role;
import com.project.management.role.dto.RoleVm;
import com.project.management.user.domain.User;
import com.project.management.user.dto.UserVm;
import com.project.management.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    @AuthManager
    private final AuthenticationManager authenticationManager;

    @PassEncoder
    private final PasswordEncoder passwordEncoder;

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;


    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Login Request")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {

        Optional<String> token;

        Optional<User> user = Optional.ofNullable(userService.findByUserName(loginRequest.getUserName()));
        if (user.isPresent()) {
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
                token = Optional.of(jwtTokenProvider.createToken(user.get()));
                Map<Object, Object> model = new HashMap<>();
                model.put("username", loginRequest.getUserName());
                model.put("userId", user.get().getId());
                model.put("token", token);
                return ResponseEntity.ok(model);
            } catch (AuthenticationException e) {
                log.error("Invalid user name or password", e);
            }

        }
        return new ResponseEntity<>("Invalid user name or password!", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity singUp(@Valid @RequestBody SignUpRequest signUpRequest) {

        Optional<User> user = Optional.ofNullable(userService.findByUserName(signUpRequest.getUserName()));

        if (user.isEmpty()) {
            try {
                User signUpUser = userService.save(User.builder().userName(signUpRequest.getUserName())
                        .fullName(signUpRequest.getFullName())
                        .password(passwordEncoder.encode(signUpRequest.getPassword()))
                        .email(signUpRequest.getEmail()).role(Role.builder().roleName("ROLE_USER").build()).build());
                return ResponseEntity.ok(UserVm.builder().userName(signUpUser.getUserName()).email(signUpUser.getEmail()).fullName(signUpUser.getFullName()).roles(signUpUser.getRoles().stream().map(RoleVm::new).collect(Collectors.toSet())).build());
            } catch (Exception e) {
                log.error("User could not save to database", e);
            }
        } else {
            return new ResponseEntity("User already exist.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("User could not save to database", HttpStatus.BAD_REQUEST);
    }
}
