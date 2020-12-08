package com.project.management.user.domain;

import com.project.management.app.entity.BaseEntity;
import com.project.management.role.domain.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PUBLIC)
@Table(name = "USERS")
public class User extends BaseEntity {

    @NotEmpty(message = "User name is mandatory.")
    @Size(min = 5, max = 10, message = "User name should have min 5 max 10 characters.")
    private String userName;

    @NotEmpty(message = "Full name is mandatory.")
    @Size(min = 5, max = 20, message = "Full name should have min 5 max 20 characters.")
    private String fullName;

    @NotEmpty(message = "Password name is mandatory.")
    private String password;

    @NotEmpty(message = "Email name is mandatory.")
    private String email;

    @Singular
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_users",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private Set<Role> roles;

    @Builder.Default
    private Boolean accountNonExpired = true;

    @Builder.Default
    private Boolean accountNonLocked = true;

    @Builder.Default
    private Boolean credentialsNonExpired = true;

    @Builder.Default
    private Boolean enabled = true;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!getId().equals(user.getId())) return false;
        return getUserName().equals(user.getUserName());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getUserName().hashCode();
        return result;
    }
}
