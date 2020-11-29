package com.project.management.authentication.jwt;

import com.project.management.authentication.service.SecurityUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
@Slf4j
public class JwtTokenAuthenticationFilter extends GenericFilterBean {

    private static final String BEARER = "Bearer";
    private static final String AUTHORIZATION ="Authorization";

    private final SecurityUserDetailService userDetailService;

    public JwtTokenAuthenticationFilter(SecurityUserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    /**
     * Determine if there is a JWT as part of the HTTP Request Header.
     * If it is valid then set the current context with the Authentication(user and roles) found in the token
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("Process request to check for a JSON Web Token");

        String headerValue = ((HttpServletRequest) servletRequest).getHeader(AUTHORIZATION);

        getBearerToken(headerValue).ifPresent(token -> {
            userDetailService.loadUserByJwtToken(token).ifPresent(userDetails -> {
                SecurityContextHolder.getContext().setAuthentication(new PreAuthenticatedAuthenticationToken(userDetails,"", Collections.emptyList()));
            });
        });
        filterChain.doFilter(servletRequest,servletResponse);
    }


    private Optional<String> getBearerToken(String headerValue){
        if (headerValue != null && headerValue.startsWith(BEARER)){
            return Optional.of(headerValue.replace(BEARER,"").trim());
        }

        return Optional.empty();
    }
}
