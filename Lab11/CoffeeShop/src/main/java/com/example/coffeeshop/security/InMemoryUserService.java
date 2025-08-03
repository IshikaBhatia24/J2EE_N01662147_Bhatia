package com.example.coffeeshop.security;

import com.example.coffeeshop.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InMemoryUserService implements UserDetailsService {

    private final Map<String, User> users = new HashMap<>();

    public void saveUser(User user) {
        users.put(user.getUsername(), user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = users.get(username);
        if (user != null) {
            return user;
        }
        throw new UsernameNotFoundException("User '" + username + "' not found");
    }
}