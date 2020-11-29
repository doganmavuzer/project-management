package com.project.management.authentication.configuration;

import com.project.management.authentication.jwt.JwtTokenAuthenticationFilter;
import com.project.management.authentication.provider.AuthManager;
import com.project.management.authentication.provider.PassEncoder;
import com.project.management.authentication.service.SecurityUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SecurityUserDetailService userDetailService;


    /**
     * Keeping whitelist provides us to use these apis directly
     * without authentication or authorization
     */
    private final String[] whiteList = {
            "/auth/login",
            "/auth/signup",
            "/swagger-resources",
            "/swagger-resources/**"
    };

    public SpringSecurityConfig(SecurityUserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //Disable CSRF (cross site request forgery)
        http.csrf().disable();

        http
                .authorizeRequests()
                .antMatchers(whiteList)
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterBefore(new JwtTokenAuthenticationFilter(userDetailService), UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs/**");
        web.ignoring().antMatchers("/swagger.json");
        web.ignoring().antMatchers("/swagger-ui.html");
        web.ignoring().antMatchers("/swagger-resources/**");
        web.ignoring().antMatchers("/webjars/**");
    }

    @Bean
    @AuthManager
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }



    @Bean
    @PassEncoder
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
