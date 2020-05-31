package com.lanagj.adviseme.configuration.security;

import com.lanagj.adviseme.entity.user.Role;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/users*").hasAnyAuthority(Role.ROLE_SIMPLE.toString(), Role.ROLE_ADMIN.toString())
                .antMatchers("/admin*").hasAuthority(Role.ROLE_ADMIN.toString())
                .antMatchers("/","/movies*","/login*").permitAll()
                .and().csrf().disable();

        http
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error")
                .usernameParameter("username").passwordParameter("password");

        http
                .logout().permitAll().logoutSuccessUrl("/login?logout");
    }
}