package com.project.management.user.dto;

import com.project.management.role.domain.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder(access = AccessLevel.PUBLIC)
public class UserVm {

    private String userName;
    private String fullName;
    private String email;
    private Set<Role> roles;
}
