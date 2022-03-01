package com.getir.readingisgood.service.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserLoaderService dummyUserService;

    public CustomUserDetailsService(UserLoaderService dummyUserService) {
        this.dummyUserService = dummyUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return dummyUserService.getUserByUsername(username);
    }
}