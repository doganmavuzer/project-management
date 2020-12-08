package com.project.management.user.controller;

import com.project.management.authentication.provider.PassEncoder;
import com.project.management.role.domain.Role;
import com.project.management.user.domain.User;
import com.project.management.user.dto.UserCreateVm;
import com.project.management.user.dto.UserVm;
import com.project.management.user.exception.UserNotFoundException;
import com.project.management.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @PassEncoder
    private final PasswordEncoder passwordEncoder;


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserVm createUser(@Valid @RequestBody UserCreateVm user) {

        User createdUser = User.builder().userName(user.getUserName())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .password(passwordEncoder.encode(user.getPassword())).role(Role.builder().roleName("ROLE_USER").build()).build();

        return new UserVm(userService.save(createdUser));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserVm>> findAllUser() {

        List<User> users = userService.findAll();

        if (users.isEmpty()) {
            throw new UserNotFoundException("Any user could not found");
        }
        return ResponseEntity.ok(users.stream().map(UserVm::new).collect(Collectors.toList()));

    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserVm findById(@PathVariable(value = "id") String id) {
        return new UserVm(userService.findById(UUID.fromString(id)));
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteUser(@PathVariable(value = "id") String id) {
        userService.delete(UUID.fromString(id));
    }

}
