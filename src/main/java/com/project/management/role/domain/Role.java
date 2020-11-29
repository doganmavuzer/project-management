package com.project.management.role.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.management.app.entity.BaseEntity;
import com.project.management.user.domain.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PUBLIC)
public class Role extends BaseEntity implements GrantedAuthority {

    @NotNull(message = "Role name may not be null")
    @NotBlank
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<User> users;

    @Override
    public String getAuthority() {
        return this.roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        if (!super.equals(o)) return false;

        Role role = (Role) o;

        return getRoleName().equals(role.getRoleName());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getRoleName().hashCode();
        return result;
    }
}
