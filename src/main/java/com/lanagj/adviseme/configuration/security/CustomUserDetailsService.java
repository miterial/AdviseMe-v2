package com.lanagj.adviseme.configuration.security;

import com.lanagj.adviseme.entity.user.Role;
import com.lanagj.adviseme.entity.user.User;
import com.lanagj.adviseme.entity.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByLogin(username);

        List<GrantedAuthority> authorities;
        if (user.getLogin().contains("admin"))
            authorities = Collections.singletonList(new SimpleGrantedAuthority(Role.ROLE_ADMIN.name()));
        else
            authorities = Collections.singletonList(new SimpleGrantedAuthority(Role.ROLE_USER.name()));

        UserPrincipal userPrincipal = new UserPrincipal(user.getId(), user.getLogin(), "{noop}" + user.getPassword() , authorities);

        return userPrincipal;
    }
}
