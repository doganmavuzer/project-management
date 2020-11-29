package com.project.management.user.service;

import com.project.management.user.domain.User;
import com.project.management.user.domain.UserSpecification;
import com.project.management.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public Optional<User> findByUserName(String userName) {
        return userRepository.findOne(UserSpecification.hasUserName(userName));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }


}
