package com.project.management.audit;

import com.project.management.authentication.provider.UserPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class JpaAuditConfiguration {

    @Bean
    public AuditorAware<String> principalAuditor() {

        return () -> {
            String userName = null;

            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                userName = SecurityContextHolder.getContext().getAuthentication().getName();
            }

            return Optional.of(userName);
        };


    }

}
