package com.project.management.authentication.service;

import com.project.management.authentication.jwt.JwtTokenProvider;
import com.project.management.authentication.provider.UserPrincipal;
import com.project.management.user.domain.User;
import com.project.management.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityUserDetailService implements UserDetailsService {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = userService.findByUserName(userName);

        return new UserPrincipal(user);

    }

    /**
     * Taking token as parameter and parse it. And validate that user exist.
     * Note: We could pass username and roles directly to UserPrincipal
     * Note: It's more efficient way to implement
     *
     * @param token
     * @return
     */
    public Optional<UserDetails> loadUserByJwtToken(String token) {

        if (jwtTokenProvider.validateToken(token)) {

            return Optional.of(loadUserByUsername(jwtTokenProvider.getUserName(token)));
        }
        return Optional.empty();
    }


}
