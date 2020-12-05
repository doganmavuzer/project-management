package com.project.management.user.service;

import com.project.management.user.domain.User;
import com.project.management.user.domain.UserSpecification;
import com.project.management.user.exception.UserNotFoundException;
import com.project.management.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findByUserName(String userName) {

        Optional<User> user = userRepository.findOne(UserSpecification.hasUserName(userName));

        return user.orElseThrow(() -> new UserNotFoundException("User could not found"));
    }

    @Override
    public User findById(UUID id) {
        Optional<User> user = userRepository.findById(id);

        return user.orElseThrow(() -> new UsernameNotFoundException("User could not found"));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void delete(UUID id) {
        userRepository.delete(this.findById(id));
    }

}
