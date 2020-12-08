package com.project.management.role.dto;

import com.project.management.role.domain.Role;
import lombok.Data;


@Data
public class RoleVm {

    private String roleName;

    public RoleVm(Role role) {
        this.roleName = role.getRoleName();
    }
}
