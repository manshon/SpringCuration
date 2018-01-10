package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || "".equals(username)) {
            throw new UsernameNotFoundException("Username is empty");
        }

        User user = repository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return (UserDetails) user;
    }

    @Transactional
    public void registerAdmin(String username, String password, String mailAddress) {
        User user = new User(username, passwordEncoder.encode(password));
//        user.setAdmin(true);
        repository.save(user);
    }

    @Transactional
    public void registerUser(String username, String password, String mailAddress) {
        User user = new User(username, passwordEncoder.encode(password));
        repository.save(user);
    }

}
