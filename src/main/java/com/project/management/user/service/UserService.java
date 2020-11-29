package com.project.management.user.service;

import com.project.management.user.domain.User;
import com.project.management.user.dto.UserVm;

import java.util.Optional;

public interface UserService {

    Optional<User> findByUserName(String userName);

    User save(User user);

}
