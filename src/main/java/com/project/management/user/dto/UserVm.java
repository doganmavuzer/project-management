package com.project.management.user.dto;

import com.project.management.role.dto.RoleVm;
import com.project.management.user.domain.User;
import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PUBLIC)
public class UserVm {

    private String userName;
    private String fullName;
    private String email;
    private Set<RoleVm> roles;

    public UserVm(User user) {
        this.userName = user.getUserName();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.roles = user.getRoles().stream().map(RoleVm::new).collect(Collectors.toSet());
    }
}
