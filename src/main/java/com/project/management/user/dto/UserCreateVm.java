package com.project.management.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCreateVm {

    private String userName;

    private String fullName;

    private String email;

    private String password;
}
