package com.project.management.user.domain;

import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor
public class UserSpecification {

    public static Specification<User> hasUserName(String userName) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("userName"), userName);
    }

}
