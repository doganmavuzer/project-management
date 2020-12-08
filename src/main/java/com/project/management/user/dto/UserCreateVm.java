package com.project.management.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UserCreateVm {

    @NotEmpty(message = "User name is mandatory.")
    @Size(min = 5, max = 10, message = "User name should have min 5 max 10 characters.")
    private String userName;

    @NotEmpty(message = "Full name is mandatory.")
    @Size(min = 5, max = 20, message = "User name should have min 5 max 20 characters.")
    private String fullName;

    @NotEmpty(message = "Email is mandatory.")
    private String email;

    @NotEmpty(message = "Email is mandatory.")
    private String password;
}
