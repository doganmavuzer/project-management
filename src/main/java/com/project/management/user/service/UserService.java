package com.project.management.user.service;

import com.project.management.user.domain.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User findByUserName(String userName);

    User save(User user);

    List<User> findAll();

    User findById(UUID id);
    
    void delete(UUID id);

}
